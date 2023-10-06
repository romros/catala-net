package cat.fundacio.catalanet.search.adapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cat.fundacio.catalanet.search.config.ConfigCerques;
import cat.fundacio.catalanet.search.model.SearchResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ConfigCerques.class)
public class SearchEngineAdapterGoogleImplTest {

    @Autowired
    private SearchEngineAdapterGoogleImpl searchEngineAdapter;

    private String loadHtmlFromFile(String path) throws IOException {
        System.out.println(new File(path).getAbsolutePath());
        return new String(Files.readAllBytes(new File(path).toPath()), StandardCharsets.UTF_8);
    }

    @Test
    public void testProcessDocumentBrillantor() throws IOException {
        // Carrega el document HTML des del fitxer
        Document mockDocument = Jsoup
                .parse(loadHtmlFromFile("src/test/resources/cerques/examples/augmentar_brillantor_apple.html"));

        // Crida al mètode que vols provar
        List<SearchResultDTO> actualResults = searchEngineAdapter.processDocument(mockDocument);

        // Llegeix el contingut del fitxer JSON
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResultDTO> expectedResults = mapper.readValue(
                new File("src/test/resources/cerques/examples/augmentar_brillantor_apple_results.json"),
                new TypeReference<List<SearchResultDTO>>() {
                });

        // Compara els resultats
        assertEquals(expectedResults.size(), actualResults.size());
        for (int i = 0; i < expectedResults.size(); i++) {
            SearchResultDTO expected = expectedResults.get(i);
            SearchResultDTO actual = actualResults.get(i);
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getUrl(), actual.getUrl());
            // Continua comparant altres camps si cal
        }
    }

    @Test
    public void testProcessDocumentBarcelona() throws IOException {
        // Carrega el document HTML des del fitxer
        Document mockDocument = Jsoup
                .parse(loadHtmlFromFile("src/test/resources/cerques/examples/Barcelona.html"));

        // Crida al mètode que vols provar
        List<SearchResultDTO> actualResults = searchEngineAdapter.processDocument(mockDocument);

        // Llegeix el contingut del fitxer JSON
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResultDTO> expectedResults = mapper.readValue(
                new File("src/test/resources/cerques/examples/Barcelona_results.json"),
                new TypeReference<List<SearchResultDTO>>() {
                });

        // Compara els resultats
        assertEquals(expectedResults.size(), actualResults.size());
        for (int i = 0; i < expectedResults.size(); i++) {
            SearchResultDTO expected = expectedResults.get(i);
            SearchResultDTO actual = actualResults.get(i);
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getUrl(), actual.getUrl());
            // Continua comparant altres camps si cal
        }
    }

}
