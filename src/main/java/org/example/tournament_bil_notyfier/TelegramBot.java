package org.example.tournament_bil_notyfier;

import lombok.RequiredArgsConstructor;
import org.example.tournament_bil_notyfier.config.BotConfig;
import org.example.tournament_bil_notyfier.model.TournamentDto;
import org.example.tournament_bil_notyfier.service.MessageCreator;
import org.example.tournament_bil_notyfier.service.TournamentParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
  private final BotConfig botConfig;
  private final TournamentParser tournamentParser;
  private final MessageCreator messageCreator;

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
    } catch (TelegramApiException e) {
      throw new RuntimeException("Can't send message: " + sendMessage.getText(), e);
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      long chatId = update.getMessage().getChatId();
      List<TournamentDto> tournaments = tournamentParser.parseUpcomingTournamentsByCity("Київ");
      String text = messageCreator.makeListOfTournaments(tournaments);
      sendMessage(chatId, text);
    }
  }
}
