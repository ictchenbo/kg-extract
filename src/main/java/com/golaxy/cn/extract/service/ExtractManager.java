package com.golaxy.cn.extract.service;

import com.golaxy.cn.extract.service.extract.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ExtractManager {
    private Map<String, TableExtractor> extractorMap = new HashMap<>();
    private MongodbService mongodbService;
    private String idPrefix = "";
    private String tableFormat;

    public ExtractManager(JsonObject ruleObj, MongodbService mongodbService, String tableFormat){
        this.mongodbService = mongodbService;
        this.tableFormat = tableFormat;

        if(ruleObj.has("idPrefix")){
            idPrefix = ruleObj.get("idPrefix").getAsString();
        }

        if(ruleObj.has("nodes")) {
            JsonArray nodes = ruleObj.get("nodes").getAsJsonArray();
            for (int i = 0; i < nodes.size(); ++i) {
                JsonObject rule = nodes.get(i).getAsJsonObject();
                if(rule.has("tableId") && rule.has("type")) {
                    addRule(rule.get("tableId").getAsString(), new NodeRule(rule));
                }else{
                    System.err.println("Invalid NodeRule: " + rule.toString());
                }
            }
        }

        if(ruleObj.has("edges")) {
            JsonArray edges = ruleObj.get("edges").getAsJsonArray();
            for (int i = 0; i < edges.size(); ++i) {
                JsonObject rule = edges.get(i).getAsJsonObject();
                if(rule.has("tableId") && rule.has("fromId") && rule.has("toId")) {
                    addRule(rule.get("tableId").getAsString(), new EdgeRule(rule));
                }else{
                    System.err.println("Invalid EdgeRule: " + rule.toString());
                }
            }
        }
    }

    public Session extract() {
        Session session = new Session();
        session.setIdPrefix(idPrefix);
        for (String tableId : extractorMap.keySet()) {
            extractorMap.get(tableId).extract(session);
        }
        return session;
    }

    private void addRule(String tableId, ExtractRule rule){
        TableExtractor tableExtractor;
        if(extractorMap.containsKey(tableId)){
            tableExtractor = extractorMap.get(tableId);
        } else {
            tableExtractor = new MongoDBTable(tableId, mongodbService, tableFormat);
            extractorMap.put(tableId, tableExtractor);
        }
        tableExtractor.addRule(rule);
    }
}
