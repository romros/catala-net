package cat.fundacio.catalanet.search.service.intern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.Search;
import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.model.SearchResult;
import cat.fundacio.catalanet.core.model.UriEntity;
import cat.fundacio.catalanet.core.repository.SearchQueryRepository;
import cat.fundacio.catalanet.core.repository.SearchRepository;
import cat.fundacio.catalanet.core.service.UriEntityService;
import cat.fundacio.catalanet.search.adapter.SearchEngineAdapter;
import cat.fundacio.catalanet.search.model.SearchResultDTO;
import cat.fundacio.catalanet.search.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Value("${storage.path}")
    private String storagePath;

    @Autowired
    private Device currentDevice;

    @Autowired
    private SearchQueryRepository queryRepository;

    @Autowired
    private SearchEngineAdapter searchEngineAdapter;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private UriEntityService uriEntityService;

    // altres repositoris segons necessitat...

    /**
     * Executes all active queries by searching for their query text using the
     * search engine adapter,
     * creating a new search object with the results, and saving it to the search
     * repository.
     * This method is transactional, meaning that if any exception is thrown during
     * its execution,
     * all changes made to the database will be rolled back.
     */
    @Override
    @Transactional
    public void executeActiveQueries() {

        List<SearchQuery> activeQueries = queryRepository.findByActiveTrue();

        for (SearchQuery query : activeQueries) {

            String directory = prepareDirectory();

            List<SearchResultDTO> searchResults = searchEngineAdapter.search(query.getQueryText(), directory);
            // Crea i guarda un nou Search
            Search newSearch = new Search();
            newSearch.setQuery(query);
            newSearch.setTimestamp(new Date()); // Estableix la data i hora actual

            List<SearchResult> dbSearchResults = new ArrayList<>();
            for (SearchResultDTO result : searchResults) {
                // Guarda la URI i crea un nou SearchResult
                UriEntity uri = uriEntityService.upsert(result.getUrl(), result.getTitle(), result.getDescription(),
                        result.getLanguage());

                SearchResult addSearchResult = new SearchResult();
                addSearchResult.setSearch(newSearch);
                addSearchResult.setUri(uri);
                addSearchResult.setPosition(result.getPosition());
                dbSearchResults.add(addSearchResult);
            }
            newSearch.setSearchResults(dbSearchResults);
            searchRepository.save(newSearch);
        }
    }

    private String prepareDirectory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = LocalDate.now().format(formatter);
        String directory = storagePath + currentDevice.getUniqueIdentifier() + "/" + today + "/";

        // Crear el directori si no existeix
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean dirsCreated = dir.mkdirs();
            if (!dirsCreated) {
                logger.error("Error creating directories for path: {}", directory);
                return null; // Retornar null si no es poden crear els directoris
            }
        }

        return directory;
    }

}
