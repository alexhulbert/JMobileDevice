package com.alexhulbert.jmobiledevice.afc;

import com.alexhulbert.jmobiledevice.Pymobiledevice;
import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import com.alexhulbert.jmobiledevice.Utils;

public class AFCFile {
    private final String id;
    
    public AFCFile(String file) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
        pi.exec(id + "=afc.AFCFile('" + file + "')");
    }
    
    public AFCFile(String file, String mode) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
        pi.exec(id + "=afc.AFCFile('" + file + "','" + mode + "')");
    }
    
    public AFCFile(String file, String mode, AFC parent) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
        pi.exec(id + "=afc.AFCFile('" + file + "','" + mode + "'," + parent.getId() + ")");
    }
    
    public AFCFile(String file, AFC parent) {
        Pymobiledevice.use("pymobiledevice", "afc");
        id = Utils.unique();
        //Should it really be 'r' by default
        pi.exec(id + "=afc.AFCFile('" + file + "','r'," + parent.getId() + ")"); //TODO: Consult PythEch on this
    }
    
    private AFCFile(String id, boolean dummy) {
        Pymobiledevice.use("pymobiledevice", "afc");
        this.id = id;
    }
    
    public static AFCFile connect(String id) {
        return new AFCFile(id, false);
    }
    
    public String getId() {
        return id;
    }
}
