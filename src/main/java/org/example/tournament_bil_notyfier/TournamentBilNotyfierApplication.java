package org.example.tournament_bil_notyfier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TournamentBilNotyfierApplication {

  public static void main(String[] args) {
    SpringApplication.run(TournamentBilNotyfierApplication.class, args);
  }

}
