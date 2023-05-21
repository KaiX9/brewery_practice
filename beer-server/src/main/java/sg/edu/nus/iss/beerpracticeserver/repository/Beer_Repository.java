package sg.edu.nus.iss.beerpracticeserver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.beerpracticeserver.model.Beer;
import sg.edu.nus.iss.beerpracticeserver.model.Brewery;
import sg.edu.nus.iss.beerpracticeserver.model.Style;

import static sg.edu.nus.iss.beerpracticeserver.repository.DBQueries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class Beer_Repository {
    
    @Autowired
    JdbcTemplate template;

    public List<Style> getStyles() {

        SqlRowSet rs = template.queryForRowSet(GET_STYLES);
        List<Style> styles = new ArrayList<Style>();

        while (rs.next()) {
            styles.add(Style.createFromRs(rs));
        }
        return styles;
    }

    public List<Beer> getBreweryByStyle(Integer style_id) {
        
        SqlRowSet rs = template.queryForRowSet(GET_BREWERIES_BY_STYLE, style_id);
        List<Beer> breweries = new ArrayList<Beer>();

        while (rs.next()) {
            breweries.add(Beer.createFromRs(rs));
        }
        return breweries;
    }

    public Optional<Brewery> getBeersByBrewery(Integer brewery_id) {
        SqlRowSet rs = template.queryForRowSet(GET_BEER_BY_BREWERY, brewery_id);

        if (rs.first()) {
            return Optional.of(Brewery.createFromRs(rs));
        }
        return Optional.empty();
    }
    
}
