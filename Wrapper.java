package com.alexhulbert.jmobiledevice;

import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import org.python.core.PyObject;

/**
 *
 * @author Taconut
 */
public class Wrapper {
    protected String id = Utils.unique();
    protected String lockdownId = Utils.unique();
    
    public void execRaw(String python) {
        pi.exec(python);
    }
    
    public PyObject evalRaw(String python) {
        return pi.eval(python);
    }
    
    public PyObject getRaw(String var) {
        return pi.get(var);
    }
    
    public void setRaw(String var, Object value) {
        pi.set(var, value);
    }
    
    public Lockdown getLockdown() {
        pi.exec(lockdownId + "=" + id + ".lockdown");
        return new Lockdown(lockdownId);
    }
    
    public String getId() {
        return id;
    }
}
