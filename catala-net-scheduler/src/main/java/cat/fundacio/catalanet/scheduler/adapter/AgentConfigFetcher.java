package cat.fundacio.catalanet.scheduler.adapter;

import cat.fundacio.catalanet.scheduler.model.AgentConfigDTO;

public interface AgentConfigFetcher {
    AgentConfigDTO fetch(String location, String deviceID);
}
