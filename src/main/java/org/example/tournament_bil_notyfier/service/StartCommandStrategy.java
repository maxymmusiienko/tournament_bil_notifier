package org.example.tournament_bil_notyfier.service;

import lombok.RequiredArgsConstructor;
import org.example.tournament_bil_notyfier.model.TournamentDto;
import org.example.tournament_bil_notyfier.model.User;
import org.example.tournament_bil_notyfier.util.Responses;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StartCommandStrategy implements CommandStrategy {
  private final UserService userService;
  private final TournamentParser tournamentParser;
  private final MessageCreator messageCreator;

  @Override
  public String performCommand(Update update) {
    long chatId = update.getMessage().getChatId();
    Optional<User> user = userService.findByUserId(chatId);
    List<TournamentDto> tournaments = tournamentParser.parseUpcomingTournamentsByCity("Київ");
    String text = messageCreator.makeListOfTournaments(tournaments);
    if (user.isEmpty()) {
      userService.saveUser(new User(chatId, true));
      return Responses.START_COMMAND_TEXT + System.lineSeparator() + text;
    }
    User newUser = new User(chatId, true);
    userService.updateUser(newUser);
    return Responses.WELCOME_BACK_TEXT + System.lineSeparator() + text;
  }
}
