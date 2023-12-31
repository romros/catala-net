package cat.fundacio.catalanet.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.service.DeviceService;
import oshi.SystemInfo;

@Configuration
@PropertySource("classpath:core.properties")
@EntityScan(basePackages = { "cat.fundacio.catalanet.core.model" })
@EnableJpaRepositories(basePackages = { "cat.fundacio.catalanet.core.repository" })
@ComponentScan(basePackages = "cat.fundacio.catalanet.core")
public class CoreConfig {

    @Bean
    public Device currentDevice(DeviceService deviceService) {
        return deviceService.getCurrentDevice();
    }

    @Bean
    public SystemInfo systemInfo() {
        return new SystemInfo();
    }
}
