package com.alexhulbert.jmobiledevice;

import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import com.alexhulbert.jmobiledevice.diagnostics.Keys;
import java.util.ArrayList;
import java.util.List;
import org.python.core.PyList;
import org.python.core.PyObject;

/**
 *
 * @author Taconut
 */
public class Lockdown extends Wrapper {
    public static String[] listDevices() {
        List<String> ret = new ArrayList<String>();
        Pymobiledevice.use("pymobiledevice", "lockdown");
        PyObject[] raws = new PyList(pi.eval("lockdown.list_devices()")).getArray();
        for (PyObject i : raws) {
            ret.add(i.toString());
        }
        return ret.toArray(new String[ret.size()]);
    }
    
    public Lockdown() {
        Pymobiledevice.use("pymobiledevice", "lockdown");
        pi.exec(id + "= lockdown.LockdownClient()");
        pi.exec(id + ".lockdown=" + id);
    }
    
    public Lockdown(String UDID) {
        Pymobiledevice.use("pymobiledevice", "lockdown");
        pi.exec(id + "= lockdown.LockdownClient('" + new String(UDID) + "')");
        pi.exec(id + ".lockdown=" + id);
    }
    
    private Lockdown(String id, boolean dummy) {
        Pymobiledevice.use("pymobiledevice", "lockdown");
        this.id = id;
        pi.exec(id + ".lockdown=" + id);
    }
    
    public static Lockdown connect(String id) {
        return new Lockdown(id, false);
    }
}
