package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowDataKV implements RowData {
    private String _id;
    private JsonArray values;
    private JsonArray keys;

    private Map<String, Integer> index;

    public RowDataKV(String _id, JsonArray values, JsonArray keys) {
        this._id = _id;
        this.values = values;
        this.keys = keys;

        index = new HashMap<>();
        for (int i = 0; i < keys.size(); ++i) {
            index.put(keys.get(i).getAsString(), i);
        }
    }

    public JsonElement getValue(String name){
        if(index.containsKey(name)) {
            return values.get(index.get(name));
        }
        return null;
    }
    public JsonArray getKeys(){
        return this.keys;
    }
}
