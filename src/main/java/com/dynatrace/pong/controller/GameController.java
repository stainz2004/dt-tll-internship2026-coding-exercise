package com.dynatrace.pong.controller;

import com.dynatrace.pong.dto.GameRequest;
import com.dynatrace.pong.dto.GameResponse;
import com.dynatrace.pong.dto.PlayerResponse;
import com.dynatrace.pong.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable long id) {
        GameResponse gameResponse =gameService.getGameById(id);
        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping()
    public ResponseEntity createGameScore(@Valid @RequestParam GameRequest gameRequest) {
        gameService.createGameScore(gameRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/winner/{gameId}")
    public ResponseEntity<PlayerResponse> getGameWinnerByGameId(@PathVariable long gameId) {
        PlayerResponse winner = gameService.getGameWinnerByGameId(gameId);
        return ResponseEntity.ok(winner);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteGameById(@RequestParam long id) {
        gameService.deleteGameById(id);
        return ResponseEntity.noContent().build();
    }
}
