package com.alexhulbert.jmobiledevice.afc;

import com.alexhulbert.jmobiledevice.Utils;
import com.alexhulbert.jmobiledevice.afc.AFC;
import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import org.python.core.PyObject;

/**
 *
 * @author Taconut
 */
@Deprecated
public class FileHandle {
    private String parentID;
    public String id = Utils.unique();
    private boolean open = true;
    private final String name;
    private int mode = AFC.AFC_FOPEN_RDONLY;
    
    public FileHandle(String parentID, PyObject po, String fileName) {
        pi.set(id, po);
        name = fileName;
    }
    
    public FileHandle(String parentID, String id, String fileName) {
        this.id = id;
        this.parentID = parentID;
        name = fileName;
    }
    
    public FileHandle(String parentID, PyObject po, String fileName, int mode) {
        pi.set(id, po);
        name = fileName;
        this.mode = mode;
    }
    
    public FileHandle(String parentID, String id, String fileName, int mode) {
        this.id = id;
        this.parentID = parentID;
        this.name = fileName;
        this.mode = mode;
    }
    
    public AFC parent() {
        return AFC.connect(parentID);
    }
    
    public String read() {
        return pi.eval(parentID + ".file_read(" + id + ")").toString();
    }
    
    public void write(String data) {
        String tmp = Utils.unique();
        pi.set(tmp, data);
        pi.exec(parentID + ".file_write(" + id + "," + tmp + ")");
        pi.set(tmp, pi.eval("None"));
    }
    
    public void close() {
        pi.exec(parentID + ".file_close(" + id + ")");
        open = false;
    }
    
    public void reOpen() {
        pi.exec(id + "=" + parentID + ".file_open('" + name + "','" + mode + "')");
        open = true;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isOpen() {
        return open;
    }
    
    public String getID() {
        return id;
    }
}
