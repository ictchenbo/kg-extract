package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Session {
    private String idPrefix = "";
    private JsonArray nodes;
    private JsonArray edges;

    public String getIdPrefix() {
        return idPrefix;
    }

    public JsonArray getNodes() {
        return nodes;
    }

    public JsonArray getEdges() {
        return edges;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public Session() {
        nodes = new JsonArray();
        edges = new JsonArray();
    }

    public void emit(JsonObject item) {
        if (item.has("fromId")) {
            edges.add(item);
        } else {
            nodes.add(item);
        }
    }

    public JsonObject asData(){
        JsonObject data = new JsonObject();
        data.addProperty("idPrefix", idPrefix);
        data.add("nodes", nodes);
        data.add("edges", edges);

        return data;
    }
}
