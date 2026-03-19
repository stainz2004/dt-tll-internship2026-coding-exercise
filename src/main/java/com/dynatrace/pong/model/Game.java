package com.dynatrace.pong.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private Player player2;
    private int scorePlayer1;
    private int scorePlayer2;
}
