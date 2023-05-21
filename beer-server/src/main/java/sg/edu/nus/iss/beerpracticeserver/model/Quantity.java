package sg.edu.nus.iss.beerpracticeserver.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Quantity {
    
    private Integer beerId;
    private Integer quantity;

    public Quantity() {

    }
    
    public Quantity(Integer beerId, Integer quantity) {
        this.beerId = beerId;
        this.quantity = quantity;
    }

    public Integer getBeerId() {
        return beerId;
    }
    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Quantity [beerId=" + beerId + ", quantity=" + quantity + "]";
    }

    public static Quantity createFromJson(JsonObject jObj) {
        Quantity q = new Quantity();
        q.setBeerId(jObj.getInt("beer_id"));
        q.setQuantity(jObj.getInt("quantity"));
        return q;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("beerId", getBeerId())
            .add("quantity", getQuantity())
            .build();
    }
    
}
