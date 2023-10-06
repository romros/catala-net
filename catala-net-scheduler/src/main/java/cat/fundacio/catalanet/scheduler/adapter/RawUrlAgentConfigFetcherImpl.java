package cat.fundacio.catalanet.scheduler.adapter;

import cat.fundacio.catalanet.scheduler.model.AgentConfigDTO;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servei que implementa l'interfície {@link AgentConfigFetcher} per a
 * la recuperació d'informació de configuració d'agent des de fonts que retornen
 * JSON en format de text pla a través d'URLs, com per exemple els continguts
 * raw
 * de GitHub.
 * 
 * <p>
 * Aquesta implementació utilitza {@link RestTemplate} per obtenir la resposta
 * en format de text i després utilitza la llibreria Jackson per convertir la
 * resposta
 * en un objecte {@link AgentConfigDTO}.
 * 
 * <p>
 * Exemple d'ús:
 * 
 * <pre>
 * {@code
 * AgentConfigDTO config = rawUrlAgentConfigFetcher.fetch("https://raw.example.com/config.json");
 * }
 * </pre>
 * 
 * @author Roman Roset Mayals
 * @version 1.0
 * @see AgentConfigFetcher
 */
@Service("rawUrlAgentConfigFetcher")
public class RawUrlAgentConfigFetcherImpl implements AgentConfigFetcher {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AgentConfigDTO fetch(String source, String deviceId) {
        ResponseEntity<String> response = restTemplate.getForEntity(source, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                System.out.println("Raw JSON response:");
                System.out.println(responseBody);

                AgentConfigDTO config = objectMapper.readValue(responseBody, AgentConfigDTO.class);
                List<String> deviceQueries = config.getDeviceSearchQueries().get(deviceId);
                if (deviceQueries != null) {
                    AgentConfigDTO newConfig = new AgentConfigDTO(Collections.singletonMap(deviceId, deviceQueries));
                    return newConfig;
                } else {
                    throw new RuntimeException("Device ID not found in the fetched JSON.");
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error parsing JSON from raw URL.", e);
            }
        } else {
            throw new RuntimeException("Failed to fetch from raw URL. Status: " + response.getStatusCode());
        }
    }

}
