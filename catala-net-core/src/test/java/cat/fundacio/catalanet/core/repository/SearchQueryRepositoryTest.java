package cat.fundacio.catalanet.core.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

import cat.fundacio.catalanet.core.boot.ClientmonitorApplication;
import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.Search;
import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.service.DeviceService;

@DataJpaTest
@ContextConfiguration(classes = { ClientmonitorApplication.class })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SearchQueryRepositoryTest {

    @MockBean
    private Device currentDevice;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SearchQueryRepository searchQueryRepository;

    // fes un setUp() per aquesta classe
    @BeforeEach
    public void setUp() {
        // Fes una instancei de deviceServiceImpl
        this.currentDevice = deviceService.detectCurrentDevice();
    }

    @Test
    public void givenNewSearchQuery_whenSaved_thenSearchesIsNotNullAndSizeZero() {
        SearchQuery query = new SearchQuery();
        query.setQueryText("example");
        query.setActive(true);

        SearchQuery savedQuery = searchQueryRepository.save(query);

        assertNotNull(savedQuery.getSearches());
        assertTrue(savedQuery.getSearches().isEmpty());
    }

    @Test
    public void givenSearchQueryWithSearches_whenDeleted_thenSearchesAlsoDeleted() {
        SearchQuery query = new SearchQuery();
        query.setQueryText("example2");
        query.setActive(true);

        Search search = new Search();
        search.setQuery(query);
        search.setTimestamp(new Date());
        query.getSearches().add(search);

        SearchQuery savedQuery = entityManager.persistAndFlush(query);

        searchQueryRepository.delete(savedQuery);

        Search searchFromDb = entityManager.find(Search.class, search.getId());
        assertTrue(searchFromDb == null);

    }

    @Test
    public void givenDuplicateQueryText_whenSaving_thenThrowsDataIntegrityViolationException() {
        SearchQuery query1 = new SearchQuery();
        query1.setQueryText("duplicate");
        query1.setActive(true);
        entityManager.persistAndFlush(query1);

        SearchQuery query2 = new SearchQuery();
        query2.setQueryText("duplicate");
        query2.setActive(true);

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(query2);
        });
    }
}
