package com.alexhulbert.jmobiledevice.diagnostics;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Info {
    private final JsonObject jo;
    
    public Info(String jo) {
        JsonParser jp = new JsonParser();
        this.jo = (JsonObject) jp.parse(jo);
    }
    
    public Info(JsonObject jo) {
        this.jo = jo;
    }
    
    public JsonObject raw() {
        return jo;
    }
    
    public boolean status() {
        try {
            if (jo.has("Diagnostics") && jo.get("Diagnostics").isJsonObject()) {
                JsonObject joDiag = jo.get("Diagnostics").getAsJsonObject();
                if (joDiag.has("MobileGestalt") && joDiag.get("MobileGestalt").isJsonObject()) {
                    return joDiag.get("MobileGestalt").getAsJsonObject().get("Status").getAsString().equals("Success");
                }
            }
        } catch(Exception e) {}
        return false;
    }
    
    public JsonElement get(double id) {
        List<Object> rets = new ArrayList<Object>();
        String key = Diagnostics.stringTable[(int)Math.floor(Math.log(id) / Math.log(2))];
        try {
            return jo.get("Diagnostics").getAsJsonObject().get("MobileGestalt").getAsJsonObject().get(key);
        } catch (Exception e) {
            return JsonNull.INSTANCE;
        }
    }
    
    public String getString(double id) {
        return get(id).getAsString();
    }
    
    public long getLong(double id) {
        return get(id).getAsLong();
    }
    
    public char getChar(double id) {
        return get(id).getAsCharacter();
    }
    
    public int getInt(double id) {
        return get(id).getAsInt();
    }
    
    public double getDouble(double id) {
        return get(id).getAsDouble();
    }
    
    public float getFloat(double id) {
        return get(id).getAsFloat();
    }
    
    public byte getByte(double id) {
        return get(id).getAsByte();
    }
    
    public BigInteger getBigInt(double id) {
        return get(id).getAsBigInteger();
    }
    
    public BigDecimal getBigDec(double id) {
        return get(id).getAsBigDecimal();
    }
    
    public boolean getBool(double id) {
        return get(id).getAsBoolean();
    }
    
    public short getShort(double id) {
        return get(id).getAsShort();
    }
    
    public Number getNumber(double id) {
        return get(id).getAsNumber();
    }
}
