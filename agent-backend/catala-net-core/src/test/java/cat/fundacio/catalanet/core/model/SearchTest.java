// BEGIN: 4a5c6d7e8fgh
package cat.fundacio.catalanet.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchTest {

    private Search search;
    private SearchQuery query;
    private Device device;
    private Date timestamp;
    private String ipOrigin;
    private String acceptLanguage;
    private List<SearchResult> searchResults;

    @BeforeEach
    public void setUp() {
        search = new Search();
        query = new SearchQuery();
        device = new Device();
        timestamp = new Date();
        ipOrigin = "127.0.0.1";
        acceptLanguage = "en-US,en;q=0.5";
        searchResults = new ArrayList<>();
    }

    @Test
    public void testGettersAndSetters() {
        search.setId(1L);
        search.setQuery(query);
        search.setDevice(device);
        search.setTimestamp(timestamp);
        search.setIpOrigin(ipOrigin);
        search.setAcceptLanguage(acceptLanguage);
        search.setSearchResults(searchResults);

        assertEquals(1L, search.getId());
        assertEquals(query, search.getQuery());
        assertEquals(device, search.getDevice());
        assertEquals(timestamp, search.getTimestamp());
        assertEquals(ipOrigin, search.getIpOrigin());
        assertEquals(acceptLanguage, search.getAcceptLanguage());
        assertEquals(searchResults, search.getSearchResults());
    }

    @Test
    public void testConstructor() {
        assertNotNull(search);
    }

    // comporvar que totes les lliste son null al començament
    @Test
    public void testSearchResults() {
        assertEquals(0, search.getSearchResults().size());
    }

    // comprovar que si afegim per odre de position llavors ho retorna en ordre
    @Test
    public void testSearchResultsOrder() {
        // Inicialització de la cerca
        Search search = new Search();
        // comprova que donada varies searchresult amb position diferents ordena per
        // position
        SearchResult searchResult1 = createSearchResult(search, 1);
        SearchResult searchResult2 = createSearchResult(search, 2);
        SearchResult searchResult3 = createSearchResult(search, 3);
        // afegir les searchresult a la cerca
        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.add(searchResult1);
        searchResults.add(searchResult2);
        searchResults.add(searchResult3);
        search.setSearchResults(searchResults);
        // comprova que la llista de searchresult esta ordenada per position
        assertEquals(1, search.getSearchResults().get(0).getPosition());
        assertEquals(2, search.getSearchResults().get(1).getPosition());
        assertEquals(3, search.getSearchResults().get(2).getPosition());

    }

    // crea un Searchresult amb position i el afegeix a la cerca
    private SearchResult createSearchResult(Search search2, int i) {
        SearchResult searchResult = new SearchResult();
        searchResult.setPosition(i);
        searchResult.setSearch(search2);
        return searchResult;
    }
}
// END: 4a5c6d7e8fgh