package cat.fundacio.catalanet.scheduler.service.intern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.repository.SearchQueryRepository;
import cat.fundacio.catalanet.scheduler.adapter.AgentConfigFetcher;
import cat.fundacio.catalanet.scheduler.model.AgentConfigDTO;
import cat.fundacio.catalanet.scheduler.service.AgentConfigService;

import java.util.List;
import java.util.Map;

@Service("agentConfigService")
public class AgentConfigServiceImp implements AgentConfigService {

    @Autowired
    private AgentConfigFetcher agentConfigFetcher;

    @Autowired
    private SearchQueryRepository searchQueryRepository;

    @Autowired
    private Device currentDevice;

    @Value("${agentconfig.config_url}")
    private String cerquesAgentsUrl;

    @Transactional
    public void synchronizeAgentConfiguration() {
        String currentDeviceId = currentDevice.getUniqueIdentifier();
        AgentConfigDTO config = agentConfigFetcher.fetch(cerquesAgentsUrl, currentDeviceId);
        Map<String, List<String>> searchQueries = config.getDeviceSearchQueries();

        deactivateAllSearchQueries();
        processQueries(searchQueries);
    }

    private void deactivateAllSearchQueries() {
        List<SearchQuery> activeQueries = searchQueryRepository.findByActiveTrue();
        for (SearchQuery query : activeQueries) {
            query.setActive(false);
        }
    }

    private void processQueries(Map<String, List<String>> searchQueries) {
        for (List<String> queries : searchQueries.values()) {
            for (String queryText : queries) {
                SearchQuery searchQuery = searchQueryRepository.findByQueryText(queryText);
                if (searchQuery == null) {
                    addNewSearchQuery(queryText);
                } else {
                    activateSearchQuery(searchQuery);
                }
            }
        }
    }

    private void addNewSearchQuery(String queryText) {
        SearchQuery newQuery = new SearchQuery();
        newQuery.setQueryText(queryText);
        newQuery.setActive(true);
        searchQueryRepository.save(newQuery);
    }

    private void activateSearchQuery(SearchQuery searchQuery) {
        searchQuery.setActive(true);
        searchQueryRepository.save(searchQuery);
    }
}
