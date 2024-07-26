package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;

public interface RowData {
    JsonElement getValue(String name);
}
