package Trade;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    public static void init() {
        mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase("trades");
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
