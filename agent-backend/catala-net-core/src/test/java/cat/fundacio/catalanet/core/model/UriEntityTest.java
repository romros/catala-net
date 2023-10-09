// BEGIN: 5d8b1f7d8j3p
package cat.fundacio.catalanet.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class UriEntityTest {

    @Test
    public void testCreateUriEntity() {
        UriEntity uri = new UriEntity();
        assertNotNull(uri);
    }

    @Test
    public void testSetAndGetId() {
        UriEntity uri = new UriEntity();
        Long id = 1L;
        uri.setId(id);
        assertEquals(id, uri.getId());
    }

    @Test
    public void testSetAndGetUrl() {
        UriEntity uri = new UriEntity();
        String url = "http://www.example.com";
        uri.setUrl(url);
        assertEquals(url, uri.getUrl());
    }

    @Test
    public void testSetAndGetTitle() {
        UriEntity uri = new UriEntity();
        String title = "Example Website";
        uri.setTitle(title);
        assertEquals(title, uri.getTitle());
    }

    @Test
    public void testSetAndGetDescription() {
        UriEntity uri = new UriEntity();
        String description = "This is an example website.";
        uri.setDescription(description);
        assertEquals(description, uri.getDescription());
    }

    @Test
    public void testSetAndGetLanguage() {
        UriEntity uri = new UriEntity();
        String language = "en";
        uri.setLanguage(language);
        assertEquals(language, uri.getLanguage());
    }

    @Test
    public void testSetAndGetFirstFound() {
        UriEntity uri = new UriEntity();
        Date firstFound = new Date();
        uri.setFirstFound(firstFound);
        assertEquals(firstFound, uri.getFirstFound());
    }

    @Test
    public void testSetAndGetLastModified() {
        UriEntity uri = new UriEntity();
        Date lastModified = new Date();
        uri.setLastModified(lastModified);
        assertEquals(lastModified, uri.getLastModified());
    }

    @Test
    public void testSetAndGetSearchResults() {
        UriEntity uri = new UriEntity();
        SearchResult result = new SearchResult();
        uri.getSearchResults().add(result);
        assertEquals(1, uri.getSearchResults().size());
    }
}
// END: 5d8b1f7d8j3p