package org.example.tournament_bil_notyfier.repository;

import org.example.tournament_bil_notyfier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findBySubscribedTrue();
}
