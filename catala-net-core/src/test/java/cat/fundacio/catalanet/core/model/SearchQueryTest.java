// BEGIN: 6f4d5e7c7d4c
package cat.fundacio.catalanet.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchQueryTest {

    private SearchQuery searchQuery;

    @BeforeEach
    public void setUp() {
        searchQuery = new SearchQuery();
    }

    @Test
    public void testGetId() {
        assertNull(searchQuery.getId());
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        searchQuery.setId(id);
        assertEquals(id, searchQuery.getId());
    }

    @Test
    public void testGetQueryText() {
        assertNull(searchQuery.getQueryText());
    }

    @Test
    public void testSetQueryText() {
        String queryText = "test query";
        searchQuery.setQueryText(queryText);
        assertEquals(queryText, searchQuery.getQueryText());
    }

    @Test
    public void testGetActive() {
        // fer un assert que comprovi que el valor per defecte és true de getActive
        assertTrue(searchQuery.getActive());
    }

    @Test
    public void testSetActive() {
        Boolean active = true;
        searchQuery.setActive(active);
        assertEquals(active, searchQuery.getActive());
    }

    @Test
    public void testGetSearches() {
        assertNotNull(searchQuery.getSearches());
        assertTrue(searchQuery.getSearches().isEmpty());
    }

    @Test
    public void testSetSearches() {
        List<Search> searches = new ArrayList<>();
        searches.add(new Search());
        searchQuery.setSearches(searches);
        assertEquals(searches, searchQuery.getSearches());
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String queryText = "test query";
        Boolean active = true;
        List<Search> searches = new ArrayList<>();
        searches.add(new Search());

        SearchQuery searchQuery = new SearchQuery(id, queryText, active);
        searchQuery.setSearches(searches);

        assertEquals(id, searchQuery.getId());
        assertEquals(queryText, searchQuery.getQueryText());
        assertEquals(active, searchQuery.getActive());
        assertEquals(searches, searchQuery.getSearches());
    }

    // test that all list in the beggining are empty
    @Test
    public void testConstructorEmpty() {
        SearchQuery searchQuery = new SearchQuery();
        assertNotNull(searchQuery.getSearches());
        assertTrue(searchQuery.getSearches().isEmpty());
    }
}
// END: 6f4d5e7c7d4c