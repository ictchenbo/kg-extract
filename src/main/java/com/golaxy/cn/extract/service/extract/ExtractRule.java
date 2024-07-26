package com.golaxy.cn.extract.service.extract;

import com.golaxy.cn.extract.utils.StringUtil;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则抽取器 分为节点抽取器和关系抽取器
 */
public abstract class ExtractRule {
    public static final String KEY_JOIN = "_";

    protected String ruleId; //规则ID 当前不用

//    protected String idPrefix = "";

    protected List<ValueExtractor> idExtractors = new ArrayList<>(4);//支持组合字段成为id

    protected List<ValueExtractor> mustFields = new ArrayList<>(4);

    protected List<ValueExtractor> typeExtractors = new ArrayList<>(4);//支持组合形成type

    protected ValueExtractor typeIdExtractor;

    protected List<PropExtractor> propsExtractors = new ArrayList<>();


    public ExtractRule(JsonObject rule) {
        //idPrefix
//        if(rule.has("idPrefix")){
//            this.idPrefix = rule.get("idPrefix").getAsString();
//        }

        //id
        if(rule.has("id")) {
            addExtractor(rule.get("id"), idExtractors);
        }

        //mustFields
        if(rule.has("mustFields")){
            JsonArray keyFieldsRule = rule.get("mustFields").getAsJsonArray();
            for(int i=0; i<keyFieldsRule.size(); ++i){
                JsonElement jsonElement = keyFieldsRule.get(i);
                ValueExtractor make = ValueExtractorFactory.make(jsonElement);
                mustFields.add(make);
            }
        }

        //type
        if(rule.has("type")) {
            JsonElement typeRule = rule.get("type");
            if (typeRule.isJsonObject()) {
                ValueExtractor typeExtractor = ValueExtractorFactory.make(typeRule, "name");
                this.typeExtractors.add(typeExtractor);
                this.typeIdExtractor = ValueExtractorFactory.make(typeRule, "id");
            } else {
                addExtractor(typeRule, typeExtractors);
            }
        }
        //mappings -> props, 以后统一为props
        if(rule.has("mappings")) {
            JsonArray props = rule.get("mappings").getAsJsonArray();
            for (int i = 0; i < props.size(); ++i) {
                JsonObject propRule = props.get(i).getAsJsonObject();
                propsExtractors.add(new PropExtractor(propRule));
            }
        }
    }

    public void addExtractor(JsonElement ruleItem, List<ValueExtractor> target) {
        if (ruleItem.isJsonPrimitive()) {
            target.add(ValueExtractorFactory.make(ruleItem));
        } else if (ruleItem.isJsonArray()) {
            JsonArray items = ruleItem.getAsJsonArray();
            for (int i = 0; i < items.size(); ++i) {
                target.add(ValueExtractorFactory.make(items.get(i)));
            }
        }
    }

    public String jsonAsString(JsonElement jsonValue) {
        if (jsonValue == null || jsonValue.isJsonNull()) {
            return null;
        }
        return jsonValue.getAsString();
    }

    /**
     * 多字段组合抽取
     * @param row
     * @param list
     * @return
     */
    public String consumeMultiFields(RowData row, List<ValueExtractor> list) {
        StringBuilder sbKey = new StringBuilder();
        for (ValueExtractor valueExtractor : list) {
            String v = valueExtractor.extract(row).getAsString();
            sbKey.append(v);
            sbKey.append(KEY_JOIN);
        }
        String key = sbKey.toString();
        if (!key.isEmpty()) {
            return key.substring(0, key.length() - KEY_JOIN.length());
        }
        return null;
    }

    /**
     * 抽取主要方法 节点和关系的具体抽取方法需要重载该方法
     * @param row
     * @return 返回抽取节点或关系JSON对象，返回null表示数据无效
     */
    public JsonObject consume(RowData row) {
        JsonObject item = new JsonObject();
        //unique ID
        item.addProperty("_id", StringUtil.getShortUuid());

        //id
        String id = consumeMultiFields(row, idExtractors);
        if(id!=null) {
            item.addProperty("id", id);
        }

        //获取mustFields中值为空的字段列表 可用于下一步策略：丢弃数据或提示用户进行处理
        JsonArray requireFields = new JsonArray();
        for(ValueExtractor valueExtractor : mustFields){
            if(valueExtractor instanceof FieldExtractor){
                String field = ((FieldExtractor)valueExtractor).getField();
                String value = jsonAsString(valueExtractor.extract(row));
                if(value == null || value.trim().isEmpty()) {
                    requireFields.add(field);
                }
            }
        }
        item.add("must", requireFields);

        //type
        String type = consumeMultiFields(row, typeExtractors);
        if(type!=null){
            item.addProperty("type", type);
        }

        //typeId
        if (typeIdExtractor != null) {
            item.add("typeId", typeIdExtractor.extract(row));
        }

        //props
        JsonArray props = new JsonArray();
        for (PropExtractor propExtractor : propsExtractors) {
            JsonObject prop = propExtractor.extract(row);
            if(prop!=null) {
                props.add(prop);
            }
        }
        item.add("props", props);

        return item;
    }
}
