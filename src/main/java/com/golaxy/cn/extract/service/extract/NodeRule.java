package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class NodeRule extends ExtractRule {
    private ValueExtractor nameExtractor;

    protected List<ValueExtractor> keyFields = new ArrayList<>(4);//支持组合键

    public NodeRule(JsonObject rule) {
        super(rule);

        if (rule.has("name")) {
            nameExtractor = ValueExtractorFactory.make(rule.get("name"));
        }

        //keyFields
        if(rule.has("keyFields")){
            JsonArray keyFieldsRule = rule.get("keyFields").getAsJsonArray();
            for(int i=0; i<keyFieldsRule.size(); ++i){
                JsonElement jsonElement = keyFieldsRule.get(i);
                ValueExtractor make = ValueExtractorFactory.make(jsonElement);
                keyFields.add(make);
            }
        }

        // id使用策略 keyFields优先级更高
        if(!this.keyFields.isEmpty()){
            idExtractors.clear();
        }
    }

    public JsonObject consume(RowData row) {
        JsonObject item = super.consume(row);
        if(item==null){
            return null;
        }

//        item.addProperty("_type", "node");

        if (nameExtractor != null) {
            item.addProperty("name", jsonAsString(nameExtractor.extract(row)));
        }

        //business ID
        String key = consumeMultiFields(row, keyFields);
        if(key!=null) {
            item.addProperty("key", key);
            if (!item.has("id")) {
                item.addProperty("id", key);
            }
        } else if(item.has("id")){
            item.add("key", item.get("id"));
        } else {
            item.add("key", item.get("_id"));
        }

        return item;
    }
}
