package cat.fundacio.catalanet.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
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
public class UriEntityRepositoryTest {

    @MockBean
    private Device currentDevice;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UriEntityRepository uriEntityRepository;

    @Test
    public void givenUriEntity_whenSaved_thenCanBeRetrieved() {
        UriEntity uriEntity = new UriEntity();
        uriEntity.setUrl("https://example.com");
        uriEntity.setTitle("Example");
        uriEntity.setDescription("This is an example description.");
        uriEntity.setLanguage("en");

        UriEntity savedEntity = uriEntityRepository.save(uriEntity);
        UriEntity retrievedEntity = uriEntityRepository.findById(savedEntity.getId()).orElse(null);

        assertNotNull(retrievedEntity);
        assertEquals("https://example.com", retrievedEntity.getUrl());
    }

    @Test
    public void givenUriEntityWithSearchResults_whenSaved_thenSearchResultsArePersisted() {
        UriEntity uriEntity = new UriEntity();
        uriEntity.setUrl("https://example2.com");
        uriEntity.setTitle("Example 2");
        uriEntity.setDescription("This is another example description.");
        uriEntity.setLanguage("en");

        Search search = new Search();
        search.setTimestamp(new Date());

        // Guarda l'entitat Search primer, ja que SearchResult fa referència a ella.
        entityManager.persist(search);

        SearchResult searchResult = new SearchResult();
        searchResult.setPosition(1);
        searchResult.setUri(uriEntity);
        searchResult.setSearch(search);
        uriEntity.getSearchResults().add(searchResult);

        UriEntity savedEntity = uriEntityRepository.save(uriEntity);
        entityManager.flush();

        UriEntity retrievedEntity = uriEntityRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(retrievedEntity);
        assertFalse(retrievedEntity.getSearchResults().isEmpty());
    }

    @Test
    public void givenUriEntityWithDuplicateUrl_whenSaved_thenThrowsDataIntegrityViolationException() {
        UriEntity uriEntity1 = new UriEntity();
        uriEntity1.setUrl("https://duplicate.com");
        uriEntity1.setTitle("Duplicate Example");
        uriEntityRepository.save(uriEntity1);

        UriEntity uriEntity2 = new UriEntity();
        uriEntity2.setUrl("https://duplicate.com");
        uriEntity2.setTitle("Duplicate Example 2");

        assertThrows(DataIntegrityViolationException.class, () -> {
            uriEntityRepository.save(uriEntity2);
            entityManager.flush();
        });
    }

}
