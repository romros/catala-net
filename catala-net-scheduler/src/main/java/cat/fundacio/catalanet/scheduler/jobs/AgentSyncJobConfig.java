package cat.fundacio.catalanet.scheduler.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import cat.fundacio.catalanet.scheduler.service.AgentConfigService;

@Configuration
@EnableBatchProcessing
public class AgentSyncJobConfig {
    @Autowired
    private AgentConfigService agentConfigService;

    @Bean
    public Tasklet synchronizeAgentConfigurationTasklet() {
        return (contribution, chunkContext) -> {
            agentConfigService.synchronizeAgentConfiguration();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step synchronizeAgentConfigStep(JobRepository jobRepository, Tasklet synchronizeAgentConfigurationTasklet,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("synchronizeAgentConfigStep", jobRepository)
                .tasklet(synchronizeAgentConfigurationTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job synchronizeAgentConfigJob(JobRepository jobRepository, Step synchronizeAgentConfigStep) {
        return new JobBuilder("synchronizeAgentConfigJob", jobRepository)
                .start(synchronizeAgentConfigStep)
                .build();
    }
}
