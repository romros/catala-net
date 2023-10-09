package cat.fundacio.catalanet.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import cat.fundacio.catalanet.scheduler.adapter.AgentConfigFetcher;
import cat.fundacio.catalanet.search.config.ConfigCerques;

@Configuration
@PropertySource("classpath:scheduler.properties")
@ComponentScan(basePackages = "cat.fundacio.catalanet.scheduler")
@Import(ConfigCerques.class)
public class SchedulerConfig {

    @Autowired
    private ApplicationContext context;

    // Aquí definim "rawUrlAgentConfigFetcher" com a valor per defecte si no es
    // proporciona un valor en el fitxer de propietats
    @Value("${agentconfig.fetcher_strategy:rawUrlAgentConfigFetcher}")
    private String strategyBeanName;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AgentConfigFetcher agentConfigFetcher() {
        return (AgentConfigFetcher) context.getBean(strategyBeanName);
    }

}
