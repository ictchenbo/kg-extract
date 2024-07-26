package com.golaxy.cn.extract;

import com.golaxy.cn.extract.bean.ResultConflicts;
import com.golaxy.cn.extract.nlubean.ResultNluEdge;
import com.golaxy.cn.extract.nlubean.ResultNluNode;
import com.golaxy.cn.extract.utils.StringUtil;
import com.google.gson.*;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        String jsonString="{\"result\":{\"_nlu_event\":[[[]],[[]]],\"_nlu_nel\":[{\"0\":[{\"content\":\"美国\",\"end\":1,\"entity_id\":\"Q30\",\"location\":[-100,40],\"start\":0},{\"content\":\"特朗普\",\"end\":5,\"entity_id\":\"Q22686\",\"start\":4},{\"content\":\"美国\",\"end\":9,\"entity_id\":\"Q30\",\"location\":[-100,40],\"start\":8}]},{\"0\":[{\"content\":\"中国\",\"end\":1,\"entity_id\":\"Q148\",\"location\":[103,35],\"start\":0},{\"content\":\"美国\",\"end\":8,\"entity_id\":\"Q30\",\"location\":[-100,40],\"start\":7}]}],\"_nlu_ner\":[{\"0\":[[\"美国\",\"GPE\"],[\"大\",\"O\"],[\"选\",\"O\"],[\"，\",\"O\"],[\"特朗普\",\"PERSON\"],[\"落\",\"O\"],[\"败\",\"O\"],[\"，\",\"O\"],[\"美国\",\"GPE\"],[\"疫\",\"O\"],[\"情\",\"O\"],[\"失\",\"O\"],[\"控\",\"O\"]]},{\"0\":[[\"中国\",\"GPE\"],[\"发\",\"O\"],[\"展\",\"O\"],[\"迅\",\"O\"],[\"猛\",\"O\"],[\"，\",\"O\"],[\"让\",\"O\"],[\"美国\",\"GPE\"],[\"感\",\"O\"],[\"到\",\"O\"],[\"堪\",\"O\"],[\"忧\",\"O\"]]}],\"_nlu_relation\":[[{\"head\":\"特朗普\",\"rel\":\"org:subsidiaries\",\"tail\":\"美国\"},{\"head\":\"美国\",\"rel\":\"org:subsidiaries\",\"tail\":\"美国\"}],[{\"head\":\"美国\",\"rel\":\"org:subsidiaries\",\"tail\":\"中国\"}]],\"_nlu_sentences\":[{\"0\":[\"美\",\"国\",\"大\",\"选\",\"，\",\"特\",\"朗\",\"普\",\"落\",\"败\",\"，\",\"美\",\"国\",\"疫\",\"情\",\"失\",\"控\"]},{\"0\":[\"中\",\"国\",\"发\",\"展\",\"迅\",\"猛\",\"，\",\"让\",\"美\",\"国\",\"感\",\"到\",\"堪\",\"忧\"]}],\"_nlu_tokens\":[[\"美\",\"国\",\"大\",\"选\",\"，\",\"特\",\"朗\",\"普\",\"落\",\"败\",\"，\",\"美\",\"国\",\"疫\",\"情\",\"失\",\"控\"],[\"中\",\"国\",\"发\",\"展\",\"迅\",\"猛\",\"，\",\"让\",\"美\",\"国\",\"感\",\"到\",\"堪\",\"忧\"]],\"_nlu_topic\":[\"Daily\",\"Daily\"]},\"status\":200}";
        JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
        List<String> topicList = new ArrayList();
        Map resultRootMap = new HashMap();
        JsonObject result = jobj.getAsJsonObject("result");
        List<ResultNluNode> resultNodeList = new LinkedList();
        List<ResultNluEdge> resultRedgeList = new LinkedList();
        List<ResultConflicts> resultConfcList = new LinkedList();


        JsonArray nlu_ner = result.getAsJsonArray("_nlu_ner").getAsJsonArray();

        /**
         *查找Node的关系
         *
         */
        Set nameTypeSet = new TreeSet();
        nlu_ner.forEach(ner-> {

            JsonObject asJsonObject = ner.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = asJsonObject.entrySet();
            entries.stream().forEach(el->{

                String key = el.getKey();
                JsonElement value = el.getValue();
                JsonArray asJsonArray = value.getAsJsonArray();
                asJsonArray.forEach(eee->{
                    JsonArray jes = eee.getAsJsonArray();
                    if(jes.toString().length() >= 10){
                        nameTypeSet.add(jes.toString());
                    }
                });
            });
        });

        final JsonParser jsonParser = new JsonParser();

        nameTypeSet.forEach(nts ->{
            ResultNluNode rnn = new ResultNluNode();
            String nameType = nts.toString();
            JsonArray objects = jsonParser.parse(nameType).getAsJsonArray();
            String name = objects.get(0).getAsString();
            String type = objects.get(1).getAsString();
            rnn.setId(StringUtil.getUUID());
            rnn.setName(name);
            rnn.setType(type);
            rnn.setUid("");
            rnn.setProps(new ArrayList<>());
            resultNodeList.add(rnn);
        });

        Map map  = new HashMap();
        JsonArray nlu_nel = result.getAsJsonArray("_nlu_nel").getAsJsonArray();
        nlu_nel.forEach(nel->{
            JsonObject asJsonObject = nel.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = asJsonObject.entrySet();
            entries.stream().forEach(entry->{
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                JsonArray asJsonArray = value.getAsJsonArray();
                asJsonArray.forEach(array->{
                    String entity_id = array.getAsJsonObject().get("entity_id").getAsString();
                    String content = array.getAsJsonObject().get("content").getAsString();

                });
            });
        });

        JsonArray nlu_relation = result.getAsJsonArray("_nlu_relation").getAsJsonArray();

        nlu_relation.forEach(relation->{
            JsonArray relationArray = relation.getAsJsonArray();
            relationArray.forEach(rel->{

                String head = rel.getAsJsonObject().get("head").getAsString();
                String tail = rel.getAsJsonObject().get("tail").getAsString();
                String relValue = rel.getAsJsonObject().get("rel").getAsString();
                ResultNluEdge resultNluEdge = new ResultNluEdge();
                resultNluEdge.setId(StringUtil.getUUID());
                resultNluEdge.setFrom(head);
                resultNluEdge.setTo(tail);
                resultNluEdge.setType(relValue);
                resultNluEdge.setProps(new ArrayList());
                resultRedgeList.add(resultNluEdge);

            });
        });


        ResultConflicts cflict = new ResultConflicts();
        cflict.setItems(new ArrayList<>());
        cflict.setType("node");
        cflict.setType("edge");
        cflict.setItems(new ArrayList<>());
        resultConfcList.add(cflict);
        resultRootMap.put("nodes",resultNodeList);
        resultRootMap.put("edges",resultRedgeList);
        resultRootMap.put("conflicts",resultConfcList);
        resultRootMap.put("topic",topicList);
        JsonArray nlu_topic = result.getAsJsonArray("_nlu_topic").getAsJsonArray();
        nlu_topic.forEach(fe->{
            topicList.add(fe.getAsString());
        });

        JsonElement json = new Gson().toJsonTree(resultRootMap);
        JsonObject resultJson = new JsonObject();

        resultJson.addProperty("message", "请求成功");
        resultJson.addProperty("code", "20000");
        resultJson.add("data", json);
        System.out.println(resultJson.toString());
    }


    private static void superMap() {
        String jsonString = "{\"status\":\"OK\",\"search_result\":[{\"product\":\"abc\",\"id\":\"1132\",\"question_mark\":{\"141\":{\"count\":\"141\",\"more_description\":\"this is abc\",\"seq\":\"2\"},\"8911\":{\"count\":\"8911\",\"more_desc\":\"this is cup\",\"seq\":\"1\"}},\"name\":\"some name\",\"description\":\"This is some product\"},{\"product\":\"XYZ\",\"id\":\"1129\",\"question_mark\":{\"379\":{\"count\":\"379\",\"more_desc\":\"this is xyz\",\"seq\":\"5\"},\"845\":{\"count\":\"845\",\"more_desc\":\"this is table\",\"seq\":\"6\"},\"12383\":{\"count\":\"12383\",\"more_desc\":\"Jumbo\",\"seq\":\"4\"},\"257258\":{\"count\":\"257258\",\"more_desc\":\"large\",\"seq\":\"1\"}},\"name\":\"some other name\",\"description\":\"this is some other product\"}]}";
        JsonObject jobj = new Gson().fromJson(jsonString, JsonObject.class);
        JsonArray ja = jobj.get("search_result").getAsJsonArray();

        ja.forEach(el -> {
            System.out.println("product: " + el.getAsJsonObject().get("product").getAsString());
            JsonObject jo = el.getAsJsonObject().get("question_mark").getAsJsonObject();
            jo.entrySet().stream().forEach(qm -> {
                String key = qm.getKey();
                JsonElement je = qm.getValue();
                System.out.println("key: " + key);
                JsonObject o = je.getAsJsonObject();
                o.entrySet().stream().forEach(prop -> {
                    System.out.println("\tname: " + prop.getKey() + " (value: " + prop.getValue().getAsString() + ")");
                });
            });
            System.out.println("");
        });
    }
}
