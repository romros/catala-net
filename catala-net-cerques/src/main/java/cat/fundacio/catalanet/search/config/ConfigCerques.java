package cat.fundacio.catalanet.search.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import cat.fundacio.catalanet.core.config.CoreConfig;

@Configuration
@Import(CoreConfig.class)
@PropertySource("classpath:cerques.properties")
@ComponentScan(basePackages = "cat.fundacio.catalanet.search")
public class ConfigCerques {
    // Configuració específica per a 'cerques' si n'hi ha
}
