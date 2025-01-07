package org.example.tournament_bil_notyfier.model;

import org.jsoup.nodes.Element;

public record TournamentDto (Element name, String city, Element date, Element club){
}
