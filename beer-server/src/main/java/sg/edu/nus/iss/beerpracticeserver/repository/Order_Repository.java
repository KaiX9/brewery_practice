package sg.edu.nus.iss.beerpracticeserver.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.beerpracticeserver.model.Order;
import sg.edu.nus.iss.beerpracticeserver.model.Quantity;

@Repository
public class Order_Repository {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public Document insertOrder(Order o) {

        List<Quantity> quantities = o.getOrders();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Quantity q : quantities) {
            arrBuilder.add(q.toJson());
        }
        JsonObject jObj = Json.createObjectBuilder()
            .add("orderId", o.getOrderId())
            .add("date", o.getDate().toString())
            .add("breweryId", o.getBreweryId())
            .add("orders", arrBuilder)
            .build();

        Document doc = Document.parse(jObj.toString());
        return mongoTemplate.insert(doc, "orders");

    }
}
