package org.example.tournament_bil_notyfier.repository;

import jakarta.transaction.Transactional;
import org.example.tournament_bil_notyfier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findBySubscribedTrue();
  Optional<User> findByUserId(Long userId);

  @Transactional
  @Modifying
  @Query("update User u set u.subscribed = ?1 where u.userId = ?2")
  void setUserSubscriptionById(boolean subscribed, Long userId);
}
