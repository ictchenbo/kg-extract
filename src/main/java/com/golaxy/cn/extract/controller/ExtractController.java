package com.golaxy.cn.extract.controller;

import com.golaxy.cn.extract.service.ExtractManager;
import com.golaxy.cn.extract.service.MongodbService;
import com.golaxy.cn.extract.service.extract.Session;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
public class ExtractController {
    @Value("${data.tableFormat}")
    private String tableFormat;

    @Autowired
    private MongodbService mongodbService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**#
     * 结构化数据知识抽取接口V2.0
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{app}/_map/table", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String queryStructuredData_v3(@PathVariable(name="app") String app, @RequestBody String param) {
        String _body = param.replace("#", "@");
        logger.info("struct param is -> " + _body);
        JsonObject body = new JsonParser().parse(_body).getAsJsonObject();
        if (!body.has("rules")) {
            return asError(40001, "请求参数中没有rules字段！");
        }

        try {
            JsonObject rules = body.get("rules").getAsJsonObject();
            ExtractManager manager = new ExtractManager(rules, mongodbService, tableFormat);

            Session session = manager.extract();
            JsonObject root = new JsonObject();
            root.addProperty("code", 20000);
            root.add("data", session.asData());
            return root.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return asError(50001, e.getMessage());
        }
    }


    private String asError(int code, String msg) {
        JsonObject root = new JsonObject();
        root.addProperty("code", code);
        root.addProperty("message", msg);
        return root.toString();
    }
}
