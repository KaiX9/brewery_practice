package sg.edu.nus.iss.beerpracticeserver.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Beer {

	private int beerId;
	private String beerName;
	private String beerDescription;

	private int breweryId;
	private String breweryName;

	public int getBeerId() {
		return beerId;
	}
	public void setBeerId(int beerId) {
		this.beerId = beerId;
	}
	public String getBeerName() {
		return beerName;
	}
	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}
	public String getBeerDescription() {
		return beerDescription;
	}
	public void setBeerDescription(String beerDescription) {
		this.beerDescription = beerDescription;
	}
	public int getBreweryId() {
		return breweryId;
	}
	public void setBreweryId(int breweryId) {
		this.breweryId = breweryId;
	}
	public String getBreweryName() {
		return breweryName;
	}
	public void setBreweryName(String breweryName) {
		this.breweryName = breweryName;
	}

	@Override
	public String toString() {
		return "Beer [beerId=" + beerId + ", beerName=" + beerName + ", beerDescription=" + beerDescription
				+ ", breweryId=" + breweryId + ", breweryName=" + breweryName + "]";
	}

    public static Beer createFromRs(SqlRowSet rs) {
        Beer b = new Beer();
        b.setBeerId(rs.getInt("beer_id"));
        b.setBeerName(rs.getString("beer_name"));
        b.setBeerDescription(rs.getString("description"));
        b.setBreweryId(rs.getInt("brewery_id"));
        b.setBreweryName(rs.getString("brewery_name"));

        return b;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("beer_id", getBeerId())
            .add("beer_name", getBeerName())
            .add("brewery_id", getBreweryId())
            .add("brewery_name", getBreweryName())
            .build();
    }

	public JsonObject toJSONForBeers() {
		return Json.createObjectBuilder()
			.add("beer_id", getBeerId())
			.add("beer_name", getBeerName())
			.add("beer_descript", getBeerDescription())
			.build();
	}

}