package Trade;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Trading {
    public static void main(String[] args) {
        MongoUtil.init();
        MongoCollection col = MongoUtil.getMongoDatabase().getCollection("BuyOffers");
        Object d = col.find(com.mongodb.client.model.Filters.eq("_id",new ObjectId("5a88e73af9ca9f15c46e13c5"))).first();
        System.out.println(d);
        //SpringApplication.run(Trading.class,args);
    }
}
