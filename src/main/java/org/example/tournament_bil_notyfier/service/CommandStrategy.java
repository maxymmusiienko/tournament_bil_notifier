package org.example.tournament_bil_notyfier.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public interface CommandStrategy {
  String performCommand(Update update);
}
