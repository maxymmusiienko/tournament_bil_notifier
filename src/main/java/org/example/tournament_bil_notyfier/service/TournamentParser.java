package org.example.tournament_bil_notyfier.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tournament_bil_notyfier.model.TournamentDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TournamentParser {
  private final static String WEBSITE_URL = "https://tournamentservice.net/";
  private final static String UPCOMING_EVENTS_QUERY = "section#add-events";
  private final static String TOURNAMENT_QUERY = "div.item-row";
  private final static String CITY_TOURNAMENT_QUERY = "div";
  private final static String CLUB_NAME_QUERY = "div > span";
  private final static String DATE_TOURNAMENT_QUERY = "time";
  private final static String FINAL_TOURNAMENT_NAME_QUERY = "a.stat-add, a.stat-ann";
  private final static Logger LOGGER = LoggerFactory.getLogger(TournamentParser.class);

  public List<TournamentDto> parseUpcomingTournamentsByCity(String cityName) {
    Elements tournamentElements = parseUpcomingTournaments();
    return filterByCity(tournamentElements, cityName);
  }

  private Elements parseUpcomingTournaments() {
    try {
      Document doc = Jsoup.connect(WEBSITE_URL).get();
      Element upcomingSection = doc.selectFirst(UPCOMING_EVENTS_QUERY);

      if (upcomingSection == null) {
        LOGGER.error("Section 'Upcoming tournaments' not found.");
        throw new RuntimeException("Section 'Upcoming tournaments' not found.");
      }

      Elements tournaments = upcomingSection.select(TOURNAMENT_QUERY);
      LOGGER.info("Found {} tournaments", tournaments.text());

      return tournaments;
    } catch (IOException e) {
      throw new RuntimeException("Error parsing tournament service", e);
    }
  }

  private List<TournamentDto> filterByCity(Elements tournamentElements, String cityName) {
    List<TournamentDto> tournaments = new ArrayList<>();
    for (Element tournament : tournamentElements) {
      Element cityElement = tournament.selectFirst(CITY_TOURNAMENT_QUERY);
      if (cityElement != null && cityElement.text().contains(cityName)) {
        Element clubName = tournament.selectFirst(CLUB_NAME_QUERY);
        Element date = tournament.selectFirst(DATE_TOURNAMENT_QUERY);
        Element nameElement = tournament.selectFirst(FINAL_TOURNAMENT_NAME_QUERY);
        tournaments.add(new TournamentDto(nameElement, cityName, date, clubName));
      }
    }
    return tournaments;
  }
}
