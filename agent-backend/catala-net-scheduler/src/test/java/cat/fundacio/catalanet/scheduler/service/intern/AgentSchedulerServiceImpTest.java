package cat.fundacio.catalanet.scheduler.service.intern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import cat.fundacio.catalanet.core.config.CoreConfig;
import cat.fundacio.catalanet.scheduler.config.SchedulerConfig;
import cat.fundacio.catalanet.scheduler.service.AgentSchedulerService;

@SpringBootTest(classes = { SchedulerConfig.class, CoreConfig.class })

@TestPropertySource(locations = "classpath:rawTest.properties")
public class AgentSchedulerServiceImpTest {

    @Autowired
    private AgentSchedulerService agentSchedulerService;

    // test if run normally without errors
    @Test
    void testRunJobBasedOnConditions() {
        agentSchedulerService.runJobBasedOnConditions();

    }
}
