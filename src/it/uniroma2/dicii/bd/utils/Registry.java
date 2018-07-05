package it.uniroma2.dicii.bd.utils;

import java.util.HashMap;
import java.util.Map;

public class Registry {

    private static Registry instance = null;
    private Map<String, String> map = new HashMap<>();

    /**
     *
     */
    public static synchronized Registry getSingletonInstance() {

        if (Registry.instance == null){
            Registry.instance = new Registry();
        }

        return instance;
    }

    /**
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        map.put(key,value);
    }

    /**
     *
     * @param key
     */
    public String get(String key) {
        return map.get(key);
    }
}
