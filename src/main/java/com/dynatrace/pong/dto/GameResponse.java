package com.dynatrace.pong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResponse {
    private Long id;
    private String playerName1;
    private String playerName2;
    private int scorePlayer1;
    private int scorePlayer2;
}
