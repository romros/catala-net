package cat.fundacio.catalanet.scheduler.service.intern;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.repository.SearchQueryRepository;
import cat.fundacio.catalanet.scheduler.config.SchedulerConfig;
import cat.fundacio.catalanet.scheduler.service.AgentConfigService;

@SpringBootTest(classes = SchedulerConfig.class)
@TestPropertySource(locations = "classpath:rawTest.properties")
public class AgentConfigServiceImpTest {

        @Autowired
        private AgentConfigService agentConfigService;

        @Autowired
        private SearchQueryRepository searchQueryRepository;

        @Test
        void testSynchronizeAgentConfiguration() {
                // Executa el mètode
                agentConfigService.synchronizeAgentConfiguration();

                // Verifica les dades
                List<String> result = searchQueryRepository.findByActiveTrue().stream()
                                .map(SearchQuery::getQueryText)
                                .collect(Collectors.toList());

                assertTrue(result.contains("augmentar brillantor apple"),
                                "La cerca 'augmentar brillantor apple' no s'ha trobat");
                assertTrue(result.contains("Barcelona"), "La cerca 'Barcelona' no s'ha trobat");
                assertTrue(result.contains("biografia Gerard Romero"),
                                "La cerca 'biografia Gerard Romero' no s'ha trobat");

        }
}
