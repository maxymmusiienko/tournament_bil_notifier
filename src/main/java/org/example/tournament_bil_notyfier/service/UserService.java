package org.example.tournament_bil_notyfier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tournament_bil_notyfier.model.User;
import org.example.tournament_bil_notyfier.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final static Logger logger = LoggerFactory.getLogger(UserService.class);

  public List<User> getAllUsers() {
    return userRepository.findBySubscribedTrue();
  }

  public void saveUser(User user) {
    User returnUser = userRepository.save(user);
    logger.info("Saved user {}", returnUser);
  }

  public Optional<User> findByUserId(Long userId) {
    return userRepository.findByUserId(userId);
  }

  public void updateUser(User user) {
    boolean subscribed = user.isSubscribed();
    Long userId = user.getUserId();
    userRepository.setUserSubscriptionById(subscribed, userId);
    logger.info("Updated user {}", user);
  }
}
