package com.mlt.entity;

import java.util.HashMap;

public class MapBean {
    private HashMap<String, Account> map;
    
    public HashMap<String, Account> getMap() {
        return map;
    }
    public void setMap(HashMap<String, Account> map) {
        this.map = map;
    }
}