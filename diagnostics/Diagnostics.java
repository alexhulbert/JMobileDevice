package com.alexhulbert.jmobiledevice.diagnostics;

import com.alexhulbert.jmobiledevice.Lockdown;
import com.alexhulbert.jmobiledevice.Pymobiledevice;
import static com.alexhulbert.jmobiledevice.Pymobiledevice.pi;
import com.alexhulbert.jmobiledevice.Utils;
import com.alexhulbert.jmobiledevice.Wrapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.python.core.PyArray;
import org.python.core.PyString;

/**
 *
 * @author alex_000
 */
public class Diagnostics extends Wrapper implements DiagConstants {
    public String[] convert(double src) {
        List<String> diags = new ArrayList<String>();
        BigInteger biSrc = new BigDecimal(src).toBigInteger();
        for (int i = 0; i < 80; i++) {
            if (biSrc.testBit(i)) {
                diags.add(DiagConstants.stringTable[i]);
            }
        }
        return diags.toArray(new String[diags.size()]);
    }
    
    public Diagnostics() {
        Pymobiledevice.use("pymobiledevice", "diagnostics_relay");
        pi.exec(id + "=diagnostics_relay.DIAGClient()");
    }
    
    public Diagnostics(String id) {
        Pymobiledevice.use("pymobiledevice", "diagnostics_relay");
        this.id = id;
    }
    
    public Diagnostics(Lockdown lockdown) {
        Pymobiledevice.use("pymobiledevice", "diagnostics_relay");
        pi.exec(id + "=diagnostics_relay.DIAGClient(" + lockdown.getId() + ")");
        
    }
    
    public Info query(double id) {
        String joined = "";
        String[] keys = convert(id);
        for (String i : keys) {
            joined += ",'" + i + "'";
        }
        joined = joined.substring(1);
        String jsonResponse =  pi.eval(this.id + ".query_mobilegestalt([" + joined + "])").toString();
        return new Info(jsonResponse);
    }
    
    public boolean doAction(String action) {
        return false;
    } 
}
