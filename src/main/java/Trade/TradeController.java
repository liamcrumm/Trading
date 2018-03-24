package Trade;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mongodb.client.model.Aggregates.limit;

@RestController
public class TradeController {
    @RequestMapping("/api/trade/buy")
    public boolean buy(@RequestParam(value = "seller") int seller,
                       @RequestParam(value="item") long item,
                       @RequestParam(value="quantity") long quantity,
                       @RequestParam(value="price") double price) {
        MongoCollection commodities = MongoUtil.getMongoDatabase().getCollection("Commodities");
        Document d = new Document();
        d.put("item",item);
        d.put("quantity",quantity);
        d.put("price",price);
        d.put("seller",seller);
        commodities.insertOne(d);
        return true;
    }

    @RequestMapping("/api/trade/sell")
    public boolean sell(@RequestParam(value = "seller") int seller,
                        @RequestParam(value="item") long item,
                        @RequestParam(value="quantity") long quantity,
                        @RequestParam(value="price") double price) {
        return false;
    }

    @RequestMapping("/api/trade/register")
    public boolean register(@RequestParam(value = "username") String user,
                            @RequestParam(value = "password") String pass) {
        String passHash = TradeUtil.generateHash(pass);
        MongoCollection users = MongoUtil.getMongoDatabase().getCollection("Users");
        Document newUser = new Document();
        newUser.put("username", user);
        newUser.put("passHash", passHash);
        newUser.put("currency", 0.0);
        FindIterable cursor = users.find().sort(new BasicDBObject("id",-1)).limit(1);
        for(Object result: cursor) {
            newUser.put("id",Integer.parseInt(((Document)result).get("id").toString()) + 1);
        }
        users.insertOne(newUser);
        return true;
    }

    @RequestMapping("/api/trade/login")
    public boolean login(@RequestParam(value = "username") String user,
                         @RequestParam(value = "password") String pass) {
        return true;
    }
}
