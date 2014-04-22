package com.alexhulbert.jmobiledevice.afc;

import com.alexhulbert.jmobiledevice.Pymobiledevice;
import com.alexhulbert.jmobiledevice.Utils;

public class AFCFile {
    private final String id;
    
    public AFCFile(String file) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
    }
    
    public AFCFile(String file, String mode) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
    }
    
    public AFCFile(String file, String mode, AFC parent) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
    }
    
    public AFCFile(String file, AFC parent) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
    }
    
    public AFCFile(char[] id) {
        Pymobiledevice.use("pymobiledevice", "afc");
        this.id = id.toString();
    }
    
    public String getId() {
        return id;
    }
}
