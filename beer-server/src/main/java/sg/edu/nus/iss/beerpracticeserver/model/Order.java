package sg.edu.nus.iss.beerpracticeserver.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order {
    
    private String orderId;
    private Date date;
    private Integer breweryId;

    private List<Quantity> orders = new ArrayList<Quantity>();

    public Order() {

    }

    public Order(String orderId, Date date, Integer breweryId, List<Quantity> orders) {
        this.orderId = orderId;
        this.date = date;
        this.breweryId = breweryId;
        this.orders = orders;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Integer getBreweryId() {
        return breweryId;
    }
    public void setBreweryId(Integer breweryId) {
        this.breweryId = breweryId;
    }
    public List<Quantity> getOrders() {
        return orders;
    }
    public void setOrders(List<Quantity> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", date=" + date + ", breweryId=" + breweryId + ", orders=" + orders + "]";
    }

    public static Order createFromJson(String json) {
        Order o = new Order();
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jObj = reader.readObject();
        JsonArray jArr = jObj.getJsonArray("quantities");
        o.setOrders(jArr.stream()
            .filter(Objects::nonNull)
            .filter(q -> q instanceof JsonObject)
            .map(q -> (JsonObject)q)
            .map(q -> Quantity.createFromJson(q))
            .toList());
        String order_id = UUID.randomUUID().toString().substring(0, 8);
        o.setOrderId(order_id);
        o.setDate(new Date());
        
        return o;
    }
    
}
