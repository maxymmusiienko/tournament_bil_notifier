package org.example.tournament_bil_notyfier.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tournament_bil_notyfier.model.TournamentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class MessageCreator {
  //todo make beautiful date
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageCreator.class);

  public String makeListOfTournaments(List<TournamentDto> tournaments) {
    StringBuilder tournamentsNames = new StringBuilder();
    String city = tournaments.get(0).city();
    tournamentsNames.append("Найблищі турніри в ")
            .append(city)
            .append(":").append(System.lineSeparator());
    for (TournamentDto tournament : tournaments) {
      if (tournament.name() != null && tournament.club() != null && tournament.date() != null) {
        LOGGER.info("Found tournament in {}, {}",tournament.city(), tournament.date().text());
        tournamentsNames.append("-----------------------------------").append(System.lineSeparator())
                .append(tournament.name().text()).append(", ").append(tournament.club().text())
                .append(", ").append(tournament.date().text()).append(System.lineSeparator())
                .append("-----------------------------------").append(System.lineSeparator());
      } else {
        tournamentsNames.append("Tournament name not found").append(System.lineSeparator());
      }
    }

    if (tournamentsNames.isEmpty()) {
      return "No tournaments found for the city: " + city;
    }

    return tournamentsNames.toString();
  }
}
