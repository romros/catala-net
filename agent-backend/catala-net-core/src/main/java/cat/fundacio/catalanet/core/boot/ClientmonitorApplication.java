package cat.fundacio.catalanet.core.boot;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.model.SearchQuery;
import cat.fundacio.catalanet.core.repository.SearchQueryRepository;

@SpringBootApplication(scanBasePackages = "cat.fundacio.catalanet.core")
public class ClientmonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientmonitorApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, SearchQueryRepository queryRepository,
			Device currentDevice) {
		return args -> {
			/*
			 * test rapid
			 */
			System.out.println("Current Device: " + currentDevice);
			System.out.println("All Active Queries:");
			List<SearchQuery> activeQueries = queryRepository.findByActiveTrue();
			for (SearchQuery query : activeQueries) {
				System.out.println(query);
			}

		};
	}

}
