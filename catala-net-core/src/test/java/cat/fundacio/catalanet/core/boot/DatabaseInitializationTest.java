package cat.fundacio.catalanet.core.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.repository.SearchQueryRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DatabaseInitializationTest {

    @MockBean
    private Device currentDevice;

    @Autowired
    private SearchQueryRepository searchQueryRepository; // Suposem que tens un repositori per a la classe Query

    @Test
    public void testDataInitializationForQueries() {
        // Comproveu que les consultes s'han inserit correctament
        SearchQuery query1 = searchQueryRepository.findByQueryText("augmentar brillantor apple"); // Suposem que tens un
        // mètode per
        // a aquesta cerca
        assertThat(query1).isNotNull();
        assertThat(query1.getActive()).isTrue();

        SearchQuery query2 = searchQueryRepository.findByQueryText("Barcelona");
        assertThat(query2).isNotNull();
        assertThat(query2.getActive()).isTrue();

        SearchQuery query3 = searchQueryRepository.findByQueryText("biografia Gerard Romero");
        assertThat(query3).isNotNull();
        assertThat(query3.getActive()).isTrue();
    }
}
