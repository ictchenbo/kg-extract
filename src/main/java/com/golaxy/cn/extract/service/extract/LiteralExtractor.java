package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class LiteralExtractor implements ValueExtractor {
    private JsonElement value;

    public LiteralExtractor(String value){
        this.value = new JsonPrimitive(value);
    }

    @Override
    public JsonElement extract(RowData row) {
        return value;
    }
}
