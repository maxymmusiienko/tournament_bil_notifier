package org.example.tournament_bil_notyfier.service;

import lombok.RequiredArgsConstructor;
import org.example.tournament_bil_notyfier.model.User;
import org.example.tournament_bil_notyfier.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  //todo add logs
  private final UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findBySubscribedTrue();
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }
}
