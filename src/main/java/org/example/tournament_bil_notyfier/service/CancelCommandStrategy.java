package org.example.tournament_bil_notyfier.service;

import lombok.RequiredArgsConstructor;
import org.example.tournament_bil_notyfier.model.User;
import org.example.tournament_bil_notyfier.util.Responses;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class CancelCommandStrategy implements CommandStrategy {
  private final UserService userService;

  @Override
  public String performCommand(Update update) {
    long chatId = update.getMessage().getChatId();
    User user = new User(chatId, false);
    userService.updateUser(user);
    return Responses.CANCEL_COMMAND_TEXT;
  }
}
