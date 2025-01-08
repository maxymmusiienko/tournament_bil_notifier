package org.example.tournament_bil_notyfier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tournament_bil_notyfier.config.BotConfig;
import org.example.tournament_bil_notyfier.model.TournamentDto;
import org.example.tournament_bil_notyfier.model.User;
import org.example.tournament_bil_notyfier.service.*;
import org.example.tournament_bil_notyfier.util.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
  private final BotConfig botConfig;
  private final UserService userService;
  private final TournamentParser tournamentParser;
  private final MessageCreator messageCreator;
  private final static Logger LOGGER = LoggerFactory.getLogger(TelegramBot.class);

  @Override
  public String getBotUsername() {
    return botConfig.getBotName();
  }

  @Override
  public String getBotToken() {
    return botConfig.getToken();
  }

  private void sendMessage(Long chatId, String textToSend) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chatId));
    sendMessage.setText(textToSend);
    try {
      execute(sendMessage);
      LOGGER.info("Sent a message {} to chat {}", textToSend, chatId);
    } catch (TelegramApiException e) {
      throw new RuntimeException("Can't send message: " + sendMessage.getText(), e);
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      Long chatId = update.getMessage().getChatId();
      String text = update.getMessage().getText();
      String response;
      CommandStrategy commandStrategy;
      if (text.trim().equals("/start")) {
        LOGGER.info("/start triggered by user {}", chatId);
        commandStrategy = new StartCommandStrategy(userService, tournamentParser, messageCreator);
        response = commandStrategy.performCommand(update);
        sendMessage(chatId, response);
      } else if (text.trim().equals("/cancel")) {
        LOGGER.info("/cancel triggered by user {}", chatId);
        commandStrategy = new CancelCommandStrategy(userService);
        response = commandStrategy.performCommand(update);
        sendMessage(chatId, response);
      } else {
        sendMessage(chatId, Responses.NO_SUCH_COMMAND_TEXT);
      }
    }
  }

  @Scheduled(cron = "0 15 15 * * ?")
  public void sendTournaments() {
    LOGGER.info("Sending tournaments to all subs...");
    List<User> users = userService.getAllUsers();
    List<TournamentDto> tournaments = tournamentParser.parseUpcomingTournamentsByCity("Київ");
    String text = messageCreator.makeListOfTournaments(tournaments);
    for (User user : users) {
      sendMessage(user.getUserId(), text);
    }
    LOGGER.info("Sending successfully completed.");
  }
}
