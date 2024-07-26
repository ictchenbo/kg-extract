package com.golaxy.cn.extract.service;

import java.util.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.regex.Pattern;


@Service
public class MongodbService {
   // private static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insertOne(String collection, Document document) {
        mongoTemplate.getCollection(collection).insertOne(document);
    }

    public void insertMany(String collection, List<Document> documentList) {
        mongoTemplate.getCollection(collection).insertMany(documentList);
    }

    public Document findById(String collection, String id) {
        Document query = new Document();
        query.put("_id", id);
        return findOne(collection, query);
    }

    public Document findOne(String collection, Bson filter) {
        return findMany(collection, filter).get(0);
    }

    public List<Document> findAll(String collection) {
        return findMany(collection, null);
    }

    public List<Document> findMany(String dbname, String collection, Bson filter){
        return null;
    }

    public List<Document> findMany(String collection, Bson filter) {
        filter = handelId(filter);
        MongoCollection<Document> mongoCollection = mongoTemplate.getCollection(collection);
        FindIterable<Document> findIterable = mongoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.filter(filter).iterator();
        List<Document> documentList = new ArrayList<Document>();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            documentList.add(document);
        }
        return documentList;
    }

    public void updateOne(String collection, Document document) {
        Document query = new Document();
        query.put("_id", new ObjectId(document.get("_id").toString()));
        document.remove("_id");
        Document updateDoc = new Document();
        updateDoc.put("$set", document);
        mongoTemplate.getCollection(collection).updateOne(query, updateDoc);
    }

    private Document handelId(Bson bson) {
        if (bson != null) {
            Document document = (Document) bson;
            Object id = null;
            if (document.containsKey("id")) {
                id = document.get("id");
                document.remove("id");
            } else if (document.containsKey("_id")) {
                id = document.get("_id");
            }
            if (id != null) {
                ObjectId objId = new ObjectId(id.toString());
                document.put("_id", objId);
            }
            return document;
        }
        return new Document();
    }

}
