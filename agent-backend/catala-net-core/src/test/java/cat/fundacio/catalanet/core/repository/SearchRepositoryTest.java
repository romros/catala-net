package cat.fundacio.catalanet.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import cat.fundacio.catalanet.core.config.CoreConfig;
import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.Search;
import cat.fundacio.catalanet.core.model.SearchResult;
import cat.fundacio.catalanet.core.model.UriEntity;

@DataJpaTest
@ContextConfiguration(classes = CoreConfig.class) // Aquesta és la classe amb @SpringBootApplication
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SearchRepositoryTest {

    @MockBean
    private Device currentDevice;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SearchRepository searchRepository;

    @Test
    public void givenSearch_whenSaved_thenPersisted() {
        // Creem una entitat de cerca bàsica
        Search search = new Search();
        search.setTimestamp(new Date());

        // Guardem l'entitat
        searchRepository.save(search);

        // Verifiquem que es pot recuperar de la base de dades
        Search retrievedSearch = entityManager.find(Search.class, search.getId());
        assertNotNull(retrievedSearch);
    }

    @Test
    public void givenSearchWithSearchResults_whenSaved_thenSearchResultsArePersisted() {
        Search search = new Search();
        search.setTimestamp(new Date());

        SearchResult result1 = new SearchResult();
        result1.setPosition(1);
        result1.setSearch(search);
        UriEntity uri1 = new UriEntity();
        uri1.setUrl("https://example.com");
        uri1.setTitle("Example");
        uri1.setDescription("This is an example description.");
        uri1.setLanguage("en");
        result1.setUri(uri1);
        search.getSearchResults().add(result1);

        searchRepository.save(search);
        entityManager.flush();

        Search retrievedSearch = entityManager.find(Search.class, search.getId());
        assertNotNull(retrievedSearch);
        assertFalse(retrievedSearch.getSearchResults().isEmpty());
    }

    @Test
    public void givenSearchWithUnorderedSearchResults_whenRetrieved_thenSearchResultsAreOrdered() {
        Search search = new Search();
        search.setTimestamp(new Date());

        // Crear UriEntity per a cada SearchResult
        UriEntity uri1 = new UriEntity();
        uri1.setUrl("https://example1.com");
        uri1.setTitle("Example 1");

        UriEntity uri2 = new UriEntity();
        uri2.setUrl("https://example2.com");
        uri2.setTitle("Example 2");

        UriEntity uri3 = new UriEntity();
        uri3.setUrl("https://example3.com");
        uri3.setTitle("Example 3");

        SearchResult result1 = new SearchResult();
        result1.setPosition(3);
        result1.setSearch(search);
        result1.setUri(uri1);
        search.getSearchResults().add(result1);

        SearchResult result2 = new SearchResult();
        result2.setPosition(1);
        result2.setSearch(search);
        result2.setUri(uri2);
        search.getSearchResults().add(result2);

        SearchResult result3 = new SearchResult();
        result3.setPosition(2);
        result3.setSearch(search);
        result3.setUri(uri3);
        search.getSearchResults().add(result3);

        searchRepository.save(search);
        entityManager.flush();
        entityManager.clear();

        Search retrievedSearch = searchRepository.findById(search.getId()).orElse(null);
        assertNotNull(retrievedSearch);
        assertFalse(retrievedSearch.getSearchResults().isEmpty());

        // Verificar l'ordre dels resultats
        assertEquals(Integer.valueOf(1), retrievedSearch.getSearchResults().get(0).getPosition());
        assertEquals(Integer.valueOf(2), retrievedSearch.getSearchResults().get(1).getPosition());
        assertEquals(Integer.valueOf(3), retrievedSearch.getSearchResults().get(2).getPosition());
    }

}
