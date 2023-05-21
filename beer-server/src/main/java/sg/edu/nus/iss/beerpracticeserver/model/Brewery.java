package sg.edu.nus.iss.beerpracticeserver.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Brewery {

	private int breweryId;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String phone;
	private String website;
	private String description;
	private List<Beer> beers = new LinkedList<>();

	public int getBreweryId() {
		return breweryId;
	}
	public void setBreweryId(int breweryId) {
		this.breweryId = breweryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Beer> getBeers() {
		return beers;
	}
	public void setBeers(List<Beer> beers) {
		this.beers = beers;
	}
	public void addBeer(Beer beer) {
		this.beers.add(beer);
	}

	@Override
	public String toString() {
		return "Brewery [breweryId=" + breweryId + ", name=" + name + ", address1=" + address1 + ", address2="
				+ address2 + ", city=" + city + ", phone=" + phone + ", website=" + website + ", description="
				+ description + ", beers=" + beers + "]";
	}

    public static Brewery createFromRs(SqlRowSet rs) {
        Brewery br = new Brewery();
        br.setBreweryId(rs.getInt("brewery_id"));
        br.setName(rs.getString("brewery_name"));
        br.setDescription(rs.getString("description"));
        br.setAddress1(rs.getString("address1"));
        br.setAddress2(rs.getString("address2"));
        br.setCity(rs.getString("city"));
        br.setPhone(rs.getString("phone"));
        br.setWebsite(rs.getString("website"));
        List<Beer> beerList = new LinkedList<Beer>();
        while (rs.next()) {
            Beer b = new Beer();
            b.setBeerId(rs.getInt("beer_id"));
            b.setBeerName(rs.getString("beer_name"));
            b.setBeerDescription(rs.getString("beer_description"));
            beerList.add(b);
        }
        br.setBeers(beerList);
        
        return br;
    }

    public JsonObject toJSON() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Beer b : beers) {
            arrBuilder.add(b.toJSONForBeers());
        }

        return Json.createObjectBuilder()
            .add("brewery_id", getBreweryId())
            .add("brewery_name", getName())
            .add("brewery_descript", getDescription())
            .add("address1", getAddress1())
            .add("address2", getAddress2())
            .add("city", getCity())
            .add("phone", getPhone())
            .add("website", getWebsite())
            .add("beerList", arrBuilder)
            .build();
    }

}