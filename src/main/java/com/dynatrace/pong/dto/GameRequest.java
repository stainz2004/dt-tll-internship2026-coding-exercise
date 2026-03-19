package com.dynatrace.pong.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GameRequest {

    @NotBlank
    private Long playerId1;
    @NotBlank
    private Long playerId2;
    @NotBlank
    private int scorePlayer1;
    @NotBlank
    private int scorePlayer2;

}
