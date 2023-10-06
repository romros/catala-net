package cat.fundacio.catalanet.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import cat.fundacio.catalanet.core.boot.ClientmonitorApplication;
import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.Search;
import cat.fundacio.catalanet.core.model.SearchResult;
import cat.fundacio.catalanet.core.model.UriEntity;

@DataJpaTest
@ContextConfiguration(classes = ClientmonitorApplication.class) // Aquesta és la classe amb @SpringBootApplication
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SearchResultRepositoryTest {

    @MockBean
    private Device currentDevice;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SearchResultRepository searchResultRepository;

    @Test
    public void givenSearchResult_whenSaved_thenCanBeRetrieved() {
        Search mockSearch = new Search();
        // Omplir els camps necessaris de mockSearch
        entityManager.persist(mockSearch);

        UriEntity mockUri = new UriEntity();
        mockUri.setTitle("Example Website");
        mockUri.setUrl("http://www.example.com");

        // Omplir els camps necessaris de mockUri
        entityManager.persist(mockUri);

        SearchResult result = new SearchResult();
        result.setSearch(mockSearch);
        result.setUri(mockUri);
        result.setPosition(1);

        searchResultRepository.save(result);

        Optional<SearchResult> retrievedResult = searchResultRepository.findById(result.getId());
        assertTrue(retrievedResult.isPresent());
        assertEquals(result.getId(), retrievedResult.get().getId());
    }

    @Test
    public void givenSearchResultWithoutPosition_whenSaved_thenThrowsException() {
        SearchResult result = new SearchResult();

        assertThrows(Exception.class, () -> {
            searchResultRepository.save(result);
        });
    }

    @Test
    public void givenSearchResultWithoutValidSearchOrUri_whenSaved_thenThrowsException() {
        SearchResult result = new SearchResult();
        result.setPosition(1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            searchResultRepository.save(result);
            entityManager.flush();
        });
    }
}
