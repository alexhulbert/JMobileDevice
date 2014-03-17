package com.alexhulbert.jmobiledevice;

import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;

/**
 *
 * @author Taconut
 */
public class Lockdown extends Wrapper {
    public Lockdown() {
        Pymobiledevice.use("pymobiledevice", "lockdown");
        pi.exec(id + ".lockdown=" + id);
    }
    
    public Lockdown(String id) {
        Pymobiledevice.use("pymobiledevice", "lockdown");
        this.id = id;
        pi.exec(id + ".lockdown=" + id);
    }
}
