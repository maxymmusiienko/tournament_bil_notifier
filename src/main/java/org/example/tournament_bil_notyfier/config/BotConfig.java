package org.example.tournament_bil_notyfier.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {
  @Value("${bot.name}")
  private String botName;

  @Value("${bot.token}")
  private String token;
}
