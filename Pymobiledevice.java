package com.alexhulbert.jmobiledevice;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.python.google.common.io.Files;
import org.python.util.PythonInterpreter;

/**
 *
 * @author Taconut
 */
public class Pymobiledevice {
    public static boolean initiated;
    private static List<String> libraries = new ArrayList<String>();
    public static PythonInterpreter pi;
    public static File tmpFolder;
    
    static {
        init();
    }
    
    private static void autoClean(File dir) {
        dir.deleteOnExit();  
        File[] files = dir.listFiles();  
        if (files != null) {  
            for (File file : files) {  
                if (file.isDirectory()) {  
                    autoClean(file);  
                } else {  
                    file.deleteOnExit();  
                }  
            }  
        }
    }
    
    public static void init() {
        try {
            //Is it better practice to use Files.createTmpDir()?
            tmpFolder = new File("./tmp");
            File location = new File(tmpFolder, "pymobiledevice");
            if (!location.exists()) {
                Extractor.extract("/pymobiledevice", location.getAbsolutePath());
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        FileUtils.deleteDirectory(tmpFolder);
                    } catch (IOException e) {
                        //Oh well. I guess the tmp files are still 
                    }
                }
            });
            
            pi = new PythonInterpreter();
            pi.exec("import sys");
            
            pi.exec("sys.path.append('" + location.getAbsolutePath().replace("\\", "\\\\") + "')");
            initiated = true;
        } catch (IOException e) {
            initiated = new File("./tmp/pymobiledevice").exists();
        }
    }
    
    public static void cleanUp() {
        pi.cleanup();
        pi = new PythonInterpreter();
        libraries.clear();
        try {
            FileUtils.deleteDirectory(new File("./tmp"));
        } catch (Exception e) {}
        initiated = false;
    }
    
    public static void use(String location, String module) {
        String libID = location + "." + module;
        if (!libraries.contains(libID)) {
            pi.exec("from " + location + " import " + module);
            libraries.add(libID);
        }
    }
    
    public static void use(String location, String module, String alias) {
        String libID = location + "." + module + "@" + alias;
        if (!libraries.contains(libID)) {
            pi.exec("from " + location + " import " + module + "as " + alias);
            libraries.add(libID);
        }
    }
}