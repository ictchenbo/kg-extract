package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;

public class FieldExtractor implements ValueExtractor {
    private final String field;

    public String getField() {
        return field;
    }

    public FieldExtractor(String field) {
        this.field = field;
    }

    @Override
    public JsonElement extract(RowData row) {
        JsonElement value = row.getValue(field);
        return value;
    }
}
