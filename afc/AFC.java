package com.alexhulbert.jmobiledevice.afc;

import com.alexhulbert.jmobiledevice.Lockdown;
import com.alexhulbert.jmobiledevice.Pymobiledevice;
import com.alexhulbert.jmobiledevice.Utils;
import com.alexhulbert.jmobiledevice.Wrapper;
import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import org.python.core.PyArray;
import org.python.core.PyString;

/**
 *
 * @author Taconut
 */
public class AFC extends Wrapper implements AFCConstants {
    public AFC() {
        Pymobiledevice.use("pymobiledevice", "afc");
        Pymobiledevice.use("pymobiledevice", "lockdown");
        pi.exec(id + "=afc.AFCClient()");
    }

    public AFC(Lockdown lockdown) {
        Pymobiledevice.use("pymobiledevice", "afc");
        Pymobiledevice.use("pymobiledevice", "lockdown");
        pi.exec(id + "=afc.AFCClient('" + lockdown.getId() + "')");
    }

    private AFC(String ID) {
        Pymobiledevice.use("pymobiledevice", "afc");
        Pymobiledevice.use("pymobiledevice", "lockdown");
        id = ID;
    }
    
    public static AFC connect(String id) {
        return new AFC(id);
    }
    
    public String get_device_infos() {
        return pi.eval(id + ".get_device_infos()").toString();
    }

    public String read_directory(String dirName) {
        return pi.eval(id + ".read_directory('" + dirName + "')").toString();
    }

    public void make_directory(String dirName) {
        pi.exec(id + ".make_directory('" + dirName + "')");
    }

    public void remove_directory(String dirName) {
        pi.exec(id + ".remove_directory('" + dirName + "')");
    }

    public String get_file_info(String fileName) {
        return pi.eval(id + ".get_file_info('" + fileName + "')").toString();
    }

    public void make_link(String target, String linkName) {
        pi.exec(id + ".make_link('" + target + "','" + linkName + "')");
    }

    public void make_link(String target, String linkName, int mode) {
        pi.exec(id + ".make_link('" + target + "','" + linkName + "','" + mode + "')");
    }

    public FileHandle file_open(String fileName) {
        return new FileHandle(id, pi.eval(id + ".file_open('" + fileName + "')"), fileName);
    }

    public FileHandle file_open(String fileName, int mode) {
        return new FileHandle(id, pi.eval(id + ".file_open('" + fileName + "')"), fileName);
    }

    public void file_remove(String fileName) {
        pi.exec(id + ".file_remove('" + fileName + "')");
    }

    public String get_file_contents(String fileName) {
        return pi.eval(id + ".get_file_contents('" + fileName + "')").toString();
    }

    public void set_file_contents(String fileName, String data) {
        String tmp = Utils.unique();
        pi.set(tmp, new PyString(data));
        pi.exec(id + ".set_file_contents('" + fileName + "'," + tmp + ")");
        pi.set(tmp, pi.eval("None"));
    }

    public String[] dir_walk(String directory) {
        String raw = pi.eval(id + ".dir_walk('" + directory + "')").toString();
        return raw.substring(1, raw.length() - 2).split("', '");
    }

    public String[] dir_walk(String directory, String[] file_list) {
        String tmp = Utils.unique();
        pi.set(tmp, new PyArray(String.class, file_list));
        String raw = pi.eval(id + ".dir_walk('" + directory + "'," + tmp + ")").toString();
        return raw.substring(1, raw.length() - 2).split("', '");
    }
}
