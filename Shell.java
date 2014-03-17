package com.alexhulbert.jmobiledevice;

import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.python.core.PyObject;
import org.python.core.PyString;

/**
 *
 * @author Taconut
 */
public class Shell extends Wrapper {
    private final String clientID = Utils.unique();
    private final AFC client;
    private final JsonParser jp = new JsonParser();
    
    /**
     * Creates a new Shell instance
     */
    public Shell() {
        Pymobiledevice.use("pymobiledevice", "afc");
        pi.exec(id + "=afc.AFCShell()");
        pi.exec(clientID + "=" + id + ".afc");
        client = new AFC(clientID);
    }
    
    /**
     * Creates a new Shell instance on the specified device
     * @param lockdown A lockdown instance connected to you're device
     */
    public Shell(Lockdown lockdown) {
        Pymobiledevice.use("pymobiledevice", "afc");
        pi.exec(id + "=afc.AFCShell(" + lockdown.getId() + ")");
        pi.exec(clientID + "=" + id + ".afc");
        client = new AFC(clientID);
    }
    
    /**
     * Attaches a shell class to an existing AFCShell
     * @param ID The id of an already-existing AFC instance
     */
    public Shell(String ID) {
        id = ID;
        Pymobiledevice.use("pymobiledevice", "afc");
        pi.exec(clientID + "=" + id + ".afc");
        client = new AFC(clientID);
    }
    
    /**
     * Gets an AFC instance from a Shell object
     * @return An AFC Client linked to this Shell
     */
    public AFC client() {
        return client;
    }
    
    /**
     * Runs a shell script
     * @see Shell#run(java.lang.String) 
     * @param script the file to run
     * @return The output of each command
     */
    public PyObject[] run(File script) {
        try {
            return run(FileUtils.readFileToString(script));
        } catch (IOException e) {/*stderr*/}
        return null;
    }
    
    /**
     * Runs shell code on an iDevice
     * Valid commands are: pwd, ln, ls, cat, cd, mv, push, pull, info, about, mkdir, rmdir, and rm
     * You can use <, >, and >> to do file I/O
     * Put a exclamation point (!) before >>, >, or < to write to a file on the computer
     * @param code Shell code to run
     * @return The outputs of each command
     */
    public PyObject[] run(String code) {
        String[] lines = code.split("(;)|(\\n)");
        List<PyObject> rets = new ArrayList<PyObject>();
        for (String command : lines) {
            command = command.trim();
            int pipeType = AFCConstants.AFC_CMD_NONE;
            String pipe = "";
            int exclam = ((command.indexOf("!>") + 1) | (command.indexOf("!<") + 1)) - 1;
            Boolean localPipe = false;
            if (exclam != -1) { 
                command = command.substring(0, exclam) + command.substring(exclam + 1);
                localPipe = true;
            }
            if (command.contains(">>")) {
                pipeType = AFCConstants.AFC_CMD_TOTO;
                pipe = command.split(">>")[1].trim();
                command = command.split(">>")[0].trim();
            } else if (command.contains(">")) {
                pipeType = AFCConstants.AFC_CMD_TO;
                pipe = command.split(">")[1].trim();
                command = command.split(">")[0].trim();
            } else if (command.contains("<")) {
                pipeType = AFCConstants.AFC_CMD_FROM;
                pipe = command.split("<")[1].trim();
                command = command.split("<")[0].trim();
            }
            String opcode = command.split(" ")[0];
            opcode = opcode.replace("ln", "link");
            opcode = opcode.replace("hd", "hexdump");
            opcode = opcode.replace("info", "infos");
            opcode = opcode.replace("df", "infos");
            if (opcode.startsWith("'") || opcode.startsWith("\"")) {
                opcode = opcode.substring(1);
            }
            if (opcode.endsWith("'") || opcode.endsWith("\"")) {
                opcode = opcode.substring(opcode.length() - 1);
            }
            String operand = command.contains(" ") ? command.substring(command.split(" ")[0].length() + 1) : "";
            operand = operand.replace("'", "\\'"); //Escape quotes
            
            PyObject val = null;
            if (localPipe) {
                switch (pipeType) {
                    case AFCConstants.AFC_CMD_TO:
                        val = pi.eval(id + ".do_" + opcode + "('" + operand + "')");
                        try {
                            FileUtils.writeStringToFile(new File(pipe), val.toString(), false);
                        } catch (IOException e) {/*stderr*/}
                    break;
                        
                    case AFCConstants.AFC_CMD_TOTO:
                        val = pi.eval(id + ".do_" + opcode + "('" + operand + "')");
                        try {
                            FileUtils.writeStringToFile(new File(pipe), val.toString(), true);
                        } catch (IOException e) {/*stderr*/}
                    break;
                        
                    case AFCConstants.AFC_CMD_FROM:
                        try {
                            operand += FileUtils.readFileToString(new File(pipe));
                        } catch (IOException ex) {/*stderr*/}
                        String tmp = Utils.unique();
                        pi.set(tmp, new PyString(operand));
                        val = pi.eval(id + ".do_" + opcode + "(" + tmp + ")");
                        pi.set(tmp, pi.eval("None"));
                    break;
                        
                    default:
                        val = pi.eval(id + ".do_" + opcode + "('" + operand + "')");
                    break;
                }
            } else {
                switch (pipeType) {
                    case AFCConstants.AFC_CMD_TO:
                        val = pi.eval(id + ".do_" + opcode + "('" + operand + "')");
                        client.set_file_contents(pipe, val.toString());
                    break;

                    case AFCConstants.AFC_CMD_TOTO:
                        val = pi.eval(id + ".do_" + opcode + "('" + operand + "')");
                        client.set_file_contents(pipe, client.get_file_contents(pipe) + val.toString());
                    break;

                    case AFCConstants.AFC_CMD_FROM:
                        operand += client.get_file_contents(pipe);
                        String tmp = Utils.unique();
                        pi.set(tmp, new PyString(operand));
                        val = pi.eval(id + ".do_" + opcode + "(" + tmp + ")");
                        pi.set(tmp, pi.eval("None"));
                    break;

                    default: 
                        val = pi.eval(id + ".do_" + opcode + "('" + operand + "')");
                    break;
                }
            }
            rets.add(val);
        }

        return rets.toArray(new PyObject[rets.size()]);
    }
    /**
     * Gets the current directory
     * @return The current directory (relative to ~mobile/Documents
     */
    public String pwd() {
        return pi.eval(id + ".do_pwd('')").toString();
    }
    
    /**
     * Makes a link/shortcut
     * @param target The directory that the link should point to
     * @param directory The name of the link
     */
    public void ln(String target, String directory) {
        pi.exec(id + ".do_ln('" + target + " " + directory + "')");
    }
    
    /**
     * Changes the current directory
     * @param directory The directory to change to
     */
    public void cd(String directory) {
        pi.exec(id + ".do_cd('" + directory + "')");
    }
    
    /**
     * Gets the contents of a file
     * @param fileName The file to read
     * @return The contents of "fileName"
     */
    public String cat(String fileName) {
        return pi.eval(id + ".do_cat('" + fileName + "')").toString();
    }
    
    /**
     * List
     * @param directory The folder to view the files
     * @return The files/folders in "directory"
     */
    public String ls(String directory) {
        return pi.eval(id + ".do_ls('" + directory + "')").toString();
    }
    
    /**
     * List
     * @return The files/folders in the current directory 
     */
    public String ls() {
        return ls("./");
    }
    
    /**
     * Removes a file
     * @param fileName The file to remove
     */
    public void rm(String fileName) {
         pi.exec(id + ".do_rm('" + fileName + "')");
    }

    /**
     * Pulls a file from the iDevice and 
     * copies it onto the computer
     * @param local The destination file
     * @param remote The file to copy
     */
    public void pull(String remote, File local) {
        pull(remote + " " + local.getPath().replace("\\", "\\\\"));
    }
    
    /**
     * Pulls a file from the iDevice and 
     * copies it to the current directory
     * @param remote The file to copy
     */
    public void pull(String remote) {
        pi.exec(id + ".do_pull('" + remote + "')");
    }
    
    /**
     * Takes a local file and puts it onto the iDevice
     * @param local  Where the file on the computer is located
     * @param remote Where to put the file on the iDevice
     */
    public void push(File local, String remote) {
        pi.exec(id + ".do_push('" + remote + " " + local.getPath().replace("\\", "\\\\") + "')");
    }
    
    /**
     * Dumps the hex of a file
     * @param fileName The file to view the hex of
     * @return The file in raw hex
     */
    public String hd(String fileName) {
        return pi.eval(id + ".do_hexdump('" + fileName + "')").toString();
    }
    
    /**
     * Makes a new directory
     * @param dirName The directory to create
     */
    public void mkdir(String dirName) {
        pi.exec(id + ".do_mkdir('" + dirName + "')");
    }
    
    /**
     * Gets various information about the iDevice
     * @return JSON string of data
     */
    public JsonObject df() {
        return (JsonObject)jp.parse(pi.eval(id + ".do_infos('')").toString());
    }
    
    /**
     * Recursively Removes a directory
     * @param dirName Directory to remove
     */
    public void rmdir(String dirName) {
        pi.exec(id + ".do_rmdir('" + dirName + "')");
    }
    
    /**
     * Renames or moves a file
     * @param oldName File to rename/move
     * @param newName New name/path for the file
     */
    public void mv(String oldName, String newName) {
        pi.exec(id + ".do_mv('" + oldName + " " + newName + "')");
    }

    /**
     * Gets information about a specific file
     * @param fileName The file to get information about
     * @return Info about the file
     */
    public JsonObject about(String fileName) {
        return (JsonObject)jp.parse(pi.eval(id + ".do_about('" + fileName + "')").toString());
    }
}
