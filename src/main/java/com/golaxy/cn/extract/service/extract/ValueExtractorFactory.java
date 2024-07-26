package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ValueExtractorFactory {
    public static final ValueExtractor RANDOM_GEN = new RandomExtractor();

    public static ValueExtractor make(JsonElement value){
        if(value !=null && value.isJsonPrimitive()){
            return make(value.getAsString());
        }
        return null;
    }

    public static ValueExtractor make(JsonElement value, String key){
        if(value!=null && value.isJsonObject()){
            JsonObject rule = value.getAsJsonObject();
            if(rule.has(key)){
                return make(rule.get(key).getAsString());
            }
        }
        return null;
    }

    public static ValueExtractor make(String x){
        if(x.equals("_")){
            return RANDOM_GEN;
        }
        if(x.startsWith("@")){
            return new FieldExtractor(x.substring(1));
        }
        return new LiteralExtractor(x);
    }
}
