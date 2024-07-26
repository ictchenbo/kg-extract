package com.golaxy.cn.extract.service.extract;

import com.golaxy.cn.extract.service.MongodbService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bson.Document;

import java.util.Iterator;

public class MongoDBTable extends TableExtractor{
    public static final String FORMAT_FLAT = "flat";

    private final MongodbService mongodbService;

    protected String tableName;
    private String tableFormat;

    public MongoDBTable(String id, MongodbService mongodbService, String tableFormat) {
        super(id);
        this.mongodbService = mongodbService;
        this.tableFormat = tableFormat;

        System.out.println(tableFormat);

        tableName = "tabular_" + id;
    }

    private String getId(JsonObject row){
        JsonElement idEle = row.get("_id");
        String _id;
        if(idEle.isJsonObject()){
            _id = idEle.getAsJsonObject().get("$oid").getAsString();
        } else{
            _id = idEle.getAsString();
        }
        return _id;
    }

    @Override
    public Iterator<RowData> scan() {

        return new Iterator<RowData>() {
            private Iterator<Document> iterator = mongodbService.findAll(tableName).iterator();
            private final Gson gson = new Gson();
            private JsonObject current = null;

            private JsonObject nextRow() {
                while (iterator.hasNext()) {
                    Document doc = iterator.next();
                    // TODO 这里可以避免格式转换 提高效率
                    JsonObject row = gson.fromJson(doc.toJson(), JsonObject.class);

                    if (FORMAT_FLAT.equals(tableFormat)) {//flat 表格式下对格式无要求
                        return row;
                    }

                    if (row.has("keys") && row.has("values")) {
                        return row;
                    }

                    // 其他表格式 跳过无效数据
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                current = nextRow();
                return current != null;
            }

            @Override
            public RowData next() {
                String id = getId(current);
                if(FORMAT_FLAT.equals(tableFormat)){
                    return new RowDataFlat(id, current);
                }
                return new RowDataKV(id, current.get("values").getAsJsonArray(), current.get("keys").getAsJsonArray());
            }
        };
    }
}
