package org.example.tournament_bil_notyfier.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TournamentParser {
  private final static String WEBSITE_URL = "https://tournamentservice.net/";
  private final static String UPCOMING_EVENTS_QUERY = "section#add-events";
  private final static String TOURNAMENT_QUERY = "div.item-row";
  private final static String CITY_TOURNAMENT_QUERY = "div > div:contains";
  private final static String FINAL_TOURNAMENT_NAME_QUERY = "a.stat-add, a.stat-ann";

  public String parseUpcomingTournamentsByCity(String cityName) {
    try {
      Document doc = Jsoup.connect(WEBSITE_URL).get();
      Element upcomingSection = doc.selectFirst(UPCOMING_EVENTS_QUERY);

      if (upcomingSection == null) {
        return "Section 'Upcoming tournaments' not found.";
      }

      Elements tournaments = upcomingSection.select(TOURNAMENT_QUERY);
      StringBuilder tournamentsNames = new StringBuilder();

      for (Element tournament : tournaments) {
        Element cityElement = tournament.selectFirst(CITY_TOURNAMENT_QUERY + "(" + cityName + ")");
        if (cityElement != null && cityElement.text().contains(cityName)) {
          Element nameElement = tournament.selectFirst(FINAL_TOURNAMENT_NAME_QUERY);
          if (nameElement != null) {
            tournamentsNames.append(nameElement.text()).append(System.lineSeparator());
          } else {
            tournamentsNames.append("Tournament name not found").append(System.lineSeparator());
          }
        }
      }

      if (tournamentsNames.isEmpty()) {
        return "No tournaments found for the city: " + cityName;
      }

      return tournamentsNames.toString();
    } catch (IOException e) {
      throw new RuntimeException("Error parsing tournament service", e);
    }
  }
}
