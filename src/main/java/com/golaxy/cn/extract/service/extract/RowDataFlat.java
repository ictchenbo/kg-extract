package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RowDataFlat implements RowData {
    private String _id;
    private JsonObject item;

    public RowDataFlat(String _id, JsonObject item) {
        this._id = _id;
        this.item = item;
    }

    public JsonElement getValue(String name){
        if(item.has(name)){
            return item.get(name);
        }
        return null;
    }
}
