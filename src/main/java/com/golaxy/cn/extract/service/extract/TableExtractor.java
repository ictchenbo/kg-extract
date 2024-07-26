package com.golaxy.cn.extract.service.extract;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class TableExtractor {
    protected String id;
    private List<ExtractRule> rules;

    public TableExtractor(String id) {
        this.id = id;
        this.rules = new ArrayList<>();
    }

    public abstract Iterator<RowData> scan();

    public void extract(Session session){
        Iterator<RowData> iterator = scan();

        while(iterator.hasNext()){
            RowData row = iterator.next();
            for(ExtractRule rule: rules){
                JsonObject item = rule.consume(row);
                if(item!=null){
                    session.emit(item);
                }
            }
        }
    }

    public void addRule(ExtractRule rule){
        this.rules.add(rule);
    }
}
