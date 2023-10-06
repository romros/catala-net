package cat.fundacio.catalanet.scheduler.adapter;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.scheduler.config.SchedulerConfig;
import cat.fundacio.catalanet.scheduler.model.AgentConfigDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SchedulerConfig.class)
@TestPropertySource(locations = "classpath:rawTest.properties")
public class RawUrlAgentConfigFetcherImplTest {

    @Autowired
    private AgentConfigFetcher agentConfigFetcher;

    @Autowired
    private Device currentDevice;

    @Value("${cerques_agents.url}")
    private String cerquesAgentsUrl;

    @Test
    public void testFetch() {
        String currentDeviceId = currentDevice.getUniqueIdentifier();
        AgentConfigDTO result = agentConfigFetcher.fetch(cerquesAgentsUrl, currentDeviceId);
        assertNotNull(result);
        assertTrue(result.getDeviceSearchQueries().containsKey(currentDeviceId));

        // Add additional assertions to check the contents of the fetched DTO
    }
}
