package cat.fundacio.catalanet.scheduler.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class AgentConfigDTO {

    private Map<String, List<String>> deviceSearchQueries = new HashMap<>();

    public AgentConfigDTO() {
    }

    public AgentConfigDTO(Map<String, List<String>> deviceSearchQueries) {
        this.deviceSearchQueries = deviceSearchQueries;
    }

    @JsonAnySetter
    public void addDeviceSearchQuery(String key, List<String> value) {
        this.deviceSearchQueries.put(key, value);
    }

    public Map<String, List<String>> getDeviceSearchQueries() {
        return deviceSearchQueries;
    }

    public void setDeviceSearchQueries(Map<String, List<String>> deviceSearchQueries) {
        this.deviceSearchQueries = deviceSearchQueries;
    }

    @Override
    public String toString() {
        return "AgentConfigDTO [deviceSearchQueries=" + deviceSearchQueries + "]";
    }
}
