package cat.fundacio.catalanet.search.service.intern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.repository.SearchQueryRepository;
import cat.fundacio.catalanet.search.config.ConfigCerques;
import cat.fundacio.catalanet.search.service.SearchService;
import static cat.fundacio.catalanet.search.util.FileNameUtils.sanitizeFileName;

@SpringBootTest(classes = ConfigCerques.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SearchServiceImplTest {

    @Autowired
    private SearchQueryRepository searchQueryRepository;

    @Autowired
    private SearchService searchService;

    @Value("${storage.path}")
    private String storagePath;

    @Autowired
    Device currentDevice;

    @Test
    public void testThreeQueriesInDatabase() {
        List<SearchQuery> cerques = searchQueryRepository.findByActiveTrue();
        assertEquals(3, cerques.size());
    }

    @Test
    public void testExecuteActiveQueries() {
        searchService.executeActiveQueries();
    }

    @Test
    void testDirectoryCreation() {
        searchService.executeActiveQueries();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = LocalDate.now().format(formatter);
        String directoryPath = storagePath + currentDevice.getUniqueIdentifier() + "/" + today + "/";
        File dir = new File(directoryPath);
        assertTrue(dir.exists(), "Directory should be created");
    }

    @Test
    void testFileWrites() {
        searchService.executeActiveQueries();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = LocalDate.now().format(formatter);
        String directoryPath = storagePath + currentDevice.getUniqueIdentifier() + "/" + today + "/";
        File dir = new File(directoryPath);

        List<SearchQuery> activeQueries = searchQueryRepository.findByActiveTrue();
        for (SearchQuery query : activeQueries) {
            File htmlFile = new File(dir, sanitizeFileName(query.getQueryText()) + ".html");
            File jsonFile = new File(dir, sanitizeFileName(query.getQueryText()) + "_results.json");

            assertTrue(htmlFile.exists(), "HTML file for query " + query.getQueryText() + " should exist");
            assertTrue(jsonFile.exists(), "JSON file for query " + query.getQueryText() + " should exist");
        }
    }

}
