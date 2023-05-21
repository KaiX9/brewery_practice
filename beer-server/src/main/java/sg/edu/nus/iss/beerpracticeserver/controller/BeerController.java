package sg.edu.nus.iss.beerpracticeserver.controller;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.beerpracticeserver.model.Beer;
import sg.edu.nus.iss.beerpracticeserver.model.Brewery;
import sg.edu.nus.iss.beerpracticeserver.model.Order;
import sg.edu.nus.iss.beerpracticeserver.model.Style;
import sg.edu.nus.iss.beerpracticeserver.repository.Beer_Repository;
import sg.edu.nus.iss.beerpracticeserver.repository.Order_Repository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeerController {
    
    @Autowired
    private Beer_Repository beerRepo;

    @Autowired
    private Order_Repository orderRepo;

    @GetMapping(path="/styles")
    public ResponseEntity<String> getStyles() {

        List<Style> styles = this.beerRepo.getStyles();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (Style s : styles) {
            arrBuilder.add(s.toJSON());
        }

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="/brewery")
    public ResponseEntity<String> getBreweryByStyle(@RequestParam Integer id) {

        List<Beer> breweries = this.beerRepo.getBreweryByStyle(id);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Beer b : breweries) {
            arrBuilder.add(b.toJSON());
        }

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="/beers")
    public ResponseEntity<String> getBeersByBrewery(@RequestParam Integer brewery_id) {

        Optional<Brewery> beers = this.beerRepo.getBeersByBrewery(brewery_id);
        System.out.println("beerList in controller: " + beers);

        if (!beers.isEmpty()){
            return ResponseEntity.ok(beers.get().toJSON().toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("Brewery not found");
    }

    @PostMapping(path="/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processOrder(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = reader.readObject();
        Integer brewery_id = jsonObj.getInt("brewery_id");
        
        Order order = Order.createFromJson(payload);
        order.setBreweryId(brewery_id);
        Document doc = this.orderRepo.insertOrder(order);
        System.out.println("doc: " + doc);


        String order_id = order.getOrderId();
        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
            .add("order_id", order_id);
        JsonObject result = objBuilder.build();
        
        return ResponseEntity.ok(result.toString());
    }

}
