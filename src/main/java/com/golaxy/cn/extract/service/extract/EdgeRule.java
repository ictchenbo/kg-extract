package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class EdgeRule extends ExtractRule{
    protected List<ValueExtractor> fromExtrators = new ArrayList<>(4);//支持组合键
    protected List<ValueExtractor> toExtractors = new ArrayList<>(4);//支持组合键
    protected boolean directed = true;

    public EdgeRule(JsonObject rule) {
        super(rule);
        if (rule.has("fromId")) {//支持单个或数组
            addExtractor(rule.get("fromId"), fromExtrators);
        }
        if (rule.has("toId")) {//支持单个或数组
            addExtractor(rule.get("toId"), toExtractors);
        }
        if (rule.has("directed")) {
            this.directed = rule.get("directed").getAsBoolean();
        }
    }


    public JsonObject consume(RowData row) {
        if (fromExtrators.isEmpty() || toExtractors.isEmpty()) {
            return null;
        }
        JsonObject edge = super.consume(row);
        if (edge == null) {
            return null;
        }

        //from
        String from = consumeMultiFields(row, fromExtrators);
        //to
        String to = consumeMultiFields(row, toExtractors);
        if (from == null || to == null || from.trim().isEmpty() || to.trim().isEmpty()) {
            return null;
        }

        edge.addProperty("fromId", from);
        edge.addProperty("toId", to);
        edge.addProperty("directed", directed);
        return edge;
    }
}
