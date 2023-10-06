package cat.fundacio.catalanet.search.adapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import jakarta.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pemistahl.lingua.api.*;

import cat.fundacio.catalanet.search.model.SearchResultDTO;

import static cat.fundacio.catalanet.search.util.FileNameUtils.sanitizeFileName;

import static com.github.pemistahl.lingua.api.Language.*;

@Service("searchEngineAdapter")
public class SearchEngineAdapterGoogleImpl implements SearchEngineAdapter {
    @Value("${google.search.url}")
    private String googleSearchUrl;
    LanguageDetector detector = null;
    private final Logger logger = LoggerFactory.getLogger(SearchEngineAdapterGoogleImpl.class);

    @Override
    public List<SearchResultDTO> search(String queryText, String directory) {
        List<SearchResultDTO> results;
        try {
            Document doc = Jsoup.connect(googleSearchUrl + queryText)
                    .header("Accept-Language", "ca-ES, es;q=0.9")
                    .get();

            // Guarda el document HTML a un fitxer
            if (directory != null && !directory.isEmpty()) {
                saveHtmlToFile(doc.html(), directory, sanitizeFileName(queryText) + ".html");
            }

            results = processDocument(doc);

            if (directory != null && !directory.isEmpty()) {
                saveResultsToJson(results, directory, sanitizeFileName(queryText) + "_results.json");
            }

        } catch (IOException e) {
            logger.error("Error accessing Google Search for query: {}", queryText, e);
            throw new RuntimeException("Error accessing Google Search", e);
        }

        return results;
    }

    public List<SearchResultDTO> processDocument(Document doc) {
        List<SearchResultDTO> results = new ArrayList<>();
        Elements divs = doc.select("div > div > div > div > span > a:has(h3)");

        int position = 1;
        for (Element div : divs) {
            String title = div.selectFirst("h3").text();
            String linkHref = div.attr("href");
            String description = extractDescriptionFromDiv(div);
            Language titleLang = detector.detectLanguageOf(title);
            Language descriptionLang = detector.detectLanguageOf(description);

            String chosenLanguage;
            if (!description.isEmpty() && description.length() > title.length()) {
                chosenLanguage = descriptionLang.getIsoCode639_1().name();
            } else {
                chosenLanguage = titleLang.getIsoCode639_1().name();
            }

            results.add(new SearchResultDTO(title, description, linkHref, position++, chosenLanguage));
        }
        return results;
    }

    private String extractDescriptionFromDiv(Element div) {
        try {
            Element root = div.parent().parent().parent().parent().parent().parent().parent();
            Elements spans = root.select("span");
            StringBuilder sb = new StringBuilder();
            for (Element span : spans) {
                String spanText = span.text().trim();
                // Comprovem si conté almenys dues paraules
                String[] words = spanText.split("\\s+");
                if (words.length > 1 && !spanText.isEmpty() && !spanText.equals("Tradueix aquesta pàgina")) {
                    if (sb.length() > 0) {
                        sb.append(" || ");
                    }
                    sb.append(spanText);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "No s'ha pogut obtenir la descripció";
        }
    }

    private void saveHtmlToFile(String htmlContent, String directory, String fileName) {
        try {
            File file = new File(directory + fileName);
            // Especifica la codificació UTF-8 quan guardis el fitxer
            try (FileOutputStream fos = new FileOutputStream(file);
                    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                    BufferedWriter writer = new BufferedWriter(osw)) {
                writer.write(htmlContent);
            }
        } catch (IOException e) {
            logger.error("Error saving HTML to file for fileName: {}", fileName, e);
        }
    }

    private void saveResultsToJson(List<SearchResultDTO> results, String directory, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(directory + fileName);
            // mapper.writeValue(file, results);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, results);
        } catch (IOException e) {
            logger.error("Error saving results to JSON for fileName: {}", fileName, e);
        }
    }

    @PostConstruct
    public void initLangDetect() {
        detector = LanguageDetectorBuilder.fromLanguages(ENGLISH, FRENCH, GERMAN, SPANISH, CATALAN).build();

    }

}
