package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PropExtractor implements ValueExtractor{
    private ValueExtractor idExtrator;

    private String name;
    private ValueExtractor nameExtractor;

    private ValueExtractor valueExtractor;

    public ValueExtractor getIdExtrator() {
        return idExtrator;
    }

    public String getName() {
        return name;
    }

    public ValueExtractor getNameExtractor() {
        return nameExtractor;
    }

    public ValueExtractor getValueExtractor() {
        return valueExtractor;
    }

    public PropExtractor(JsonObject rule) {
        this.valueExtractor = ValueExtractorFactory.make(rule.get("value"));

        if(rule.has("name")) {
            JsonElement nameEle = rule.get("name");
            if (nameEle.isJsonObject()) {
                JsonObject nameRule = rule.get("name").getAsJsonObject();

                if (nameRule.has("id")) {
                    this.idExtrator = ValueExtractorFactory.make(nameRule.get("id"));
                }

                if (nameRule.has("name")) {
                    this.name = nameRule.get("name").getAsString();
                    this.nameExtractor = ValueExtractorFactory.make(this.name);
                }
            } else {
                this.name = nameEle.getAsString();
                this.nameExtractor = ValueExtractorFactory.make(this.name);
            }
        }

        if(this.nameExtractor==null && valueExtractor instanceof FieldExtractor){
            this.name = ((FieldExtractor)valueExtractor).getField();
            this.nameExtractor = ValueExtractorFactory.make(this.name);
        }
    }

    public JsonObject extract(RowData row) {
        if (valueExtractor == null) {
            return null;
        }

        JsonObject prop = new JsonObject();

        JsonElement value = valueExtractor.extract(row);

        prop.add("value", value);

        if (nameExtractor != null) {
            prop.add("name", nameExtractor.extract(row));
        }

        if (idExtrator != null) {
            prop.add("id", idExtrator.extract(row));
        }

        return prop;
    }
}
