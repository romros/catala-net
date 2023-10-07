package cat.fundacio.catalanet.scheduler.service.intern;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cat.fundacio.catalanet.scheduler.service.AgentSchedulerService;
import cat.fundacio.catalanet.scheduler.util.DeviceUtils;

@Service("agentSchedulerService")
public class AgentSchedulerServiceImp implements AgentSchedulerService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job synchronizeAgentConfigJob;

    @Autowired
    private DeviceUtils deviceUtils;

    @Value("${agentconfig.cpuLoadThreshold}")
    private double cpuLoadThreshold;

    @Override
    @Scheduled(fixedDelay = 60000) // comprova cada 60 segons, per exemple
    public void runJobBasedOnConditions() {
        if (checkCPULoad() && checkInternetConnection() && checkBandwidth()) {
            try {
                jobLauncher.run(synchronizeAgentConfigJob, new JobParameters());
            } catch (Exception e) {
                // Handle job execution exceptions
            }
        }
    }

    private boolean checkCPULoad() {
        double currentLoad = deviceUtils.cpuLoad();
        return currentLoad < cpuLoadThreshold;
    }

    private boolean checkInternetConnection() {
        // Implementa la lògica per comprovar la connexió a Internet
        // ToDo: implementar
        return true; // de moment, simplement retorna true
    }

    private boolean checkBandwidth() {
        // Implementa la lògica per comprovar l'ample de banda disponible
        // ToDo: implementar
        return true; // de moment, simplement retorna true
    }
}
