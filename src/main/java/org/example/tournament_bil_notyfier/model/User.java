package org.example.tournament_bil_notyfier.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private Long userId;

  @Column(name = "is_subscribed")
  private boolean subscribed;

  public User(Long userId, boolean subscribed) {
    this.userId = userId;
    this.subscribed = subscribed;
  }
}
