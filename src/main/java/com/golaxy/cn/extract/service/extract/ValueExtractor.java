package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;

public interface ValueExtractor {
    JsonElement extract(RowData row);
}
