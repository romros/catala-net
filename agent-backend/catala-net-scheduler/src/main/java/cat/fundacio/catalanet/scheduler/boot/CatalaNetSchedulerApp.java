package cat.fundacio.catalanet.scheduler.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class CatalaNetSchedulerApp {

	public static void main(String[] args) {
		SpringApplication.run(CatalaNetSchedulerApp.class, args);
	}

	// Tasca de prova que s'executa cada 10 segons després d'iniciar l'aplicació
	@Scheduled(fixedRate = 10000)
	public void executeSampleTask() {
		System.out.println("Executant tasca de prova...");
		// Aquí es podria posar la lògica per a comprovar configuració, fer cerques a
		// Google, etc.
		// Per ara, simplement imprimim un missatge al terminal.
	}
}
