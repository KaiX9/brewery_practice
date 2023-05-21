package sg.edu.nus.iss.beerpracticeserver.repository;

public class DBQueries {
    
    public static final String GET_STYLES = """
        select s.id as styles_id, s.style_name as styles_name, 
        count(*) as beer_count from styles as s join categories 
        as c on s.cat_id = c.id join beers as b on s.id = b.style_id
        group by styles_id order by beer_count desc, styles_name asc
    """;

    public static final String GET_BREWERIES_BY_STYLE = """
        select b.id as beer_id, b.name as beer_name, b.descript as description, 
        br.id as brewery_id, br.name as brewery_name from beers 
        as b join breweries as br on b.brewery_id = br.id join 
        styles as s on b.style_id = s.id where s.id = ? order by
        beer_name asc     
    """;

    public static final String GET_BEER_BY_BREWERY = """
        select br.id as brewery_id, br.name as brewery_name, br.descript as 
        description, br.address1 as address1, br.address2 as address2, br.city
        as city, br.phone as phone, br.website as website, b.id as beer_id, b.name 
        as beer_name, b.descript as beer_description from breweries as br join beers 
        as b on b.brewery_id = br.id where br.id = ? order by beer_name asc          
    """;

}
