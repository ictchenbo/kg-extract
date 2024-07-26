package com.golaxy.cn.extract.service.extract;

import com.golaxy.cn.extract.utils.StringUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class RandomExtractor implements ValueExtractor{

    public String generate(){
        return StringUtil.getShortUuid();
    }

    @Override
    public JsonElement extract(RowData row) {
        return new JsonPrimitive(generate());
    }
}
