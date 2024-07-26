package  com.golaxy.cn.extract;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


// 注释后回滚
@SpringBootTest
@Transactional
class MongoDBTest {

    /**
     *
     */
    @Autowired
    //private MongodbService mongodbService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    void insertOne() {
        Document doc = new Document();
        doc.put("name", "world");
        doc.put("age", 16);
        //mongoUtils.insertOne("collection", doc);
    }


    void insertMany() {
        List<Document> documentList = new ArrayList<Document>();
        Document doc = new Document();
        doc.put("name", "sam");
        doc.put("age", 30);
        documentList.add(doc);
        doc = new Document();
        doc.put("name", "nicole");
        doc.put("age", 30);
        documentList.add(doc);
        //mongoUtils.insertMany("collection", documentList);
    }


    void findById() {
       // System.out.println(mongoUtils.findById("collection", "5db7a5e66957f95f2a7459a6"));
    }

    void updateOne() {
       // Document doc = mongoUtils.findById("collection", "5db7a5e66957f95f2a7459a6");
        //doc.replace("name", "Jully");
        //mongoUtils.updateOne("collection", doc);
    }


    void findOne() {
        Document doc = new Document();
        doc.put("name", "hello");
        //System.out.println(mongoUtils.findOne("collection", doc));
    }


    void findMany() {
        Document doc = new Document();
        doc.put("id", "5db7a5e66957f95f2a7459a6");
       // System.out.println(mongoUtils.findMany("collection", doc));
    }


    void findAll() {
        //System.out.println(mongoUtils.findAll("collection"));
    }
}