package cat.fundacio.catalanet.scheduler.adapter;

import cat.fundacio.catalanet.scheduler.model.AgentConfigDTO;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("urlAgentConfigFetcher")
public class UrlAgentConfigFetcherImpl implements AgentConfigFetcher {

    private final RestTemplate restTemplate;

    public UrlAgentConfigFetcherImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AgentConfigDTO fetch(String source, String deviceId) {
        AgentConfigDTO config = restTemplate.getForObject(source, AgentConfigDTO.class);

        if (config == null || config.getDeviceSearchQueries() == null) {
            throw new RuntimeException("Fetched configuration is null or incomplete.");
        }

        List<String> deviceQueries = config.getDeviceSearchQueries().get(deviceId);

        if (deviceQueries == null) {
            throw new RuntimeException("Device ID not found in the fetched configuration.");
        }

        return new AgentConfigDTO(Collections.singletonMap(deviceId, deviceQueries));
    }
}
