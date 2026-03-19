package com.dynatrace.pong.service;

import com.dynatrace.pong.dto.GameRequest;
import com.dynatrace.pong.dto.GameResponse;
import com.dynatrace.pong.dto.PlayerResponse;
import com.dynatrace.pong.model.Game;
import com.dynatrace.pong.model.Player;
import com.dynatrace.pong.repository.GameRepository;
import com.dynatrace.pong.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;


    @Transactional(readOnly = true)
    public GameResponse getGameById(long id) {
        return gameRepository.findById(id)
                .map(this::toResponseGame)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    @Transactional
    public void createGameScore(GameRequest gameRequest) {
        Long playerId1 = gameRequest.getPlayerId1();
        Long playerId2 = gameRequest.getPlayerId2();
        int scorePlayer1 = gameRequest.getScorePlayer1();
        int scorePlayer2 = gameRequest.getScorePlayer2();

        if (playerRepository.findById(playerId1).equals(Optional.empty()) || playerRepository.findById(playerId2).equals(Optional.empty())) {
            throw new RuntimeException("One or both players not found with provided IDs.");
        }

        if (scorePlayer1 < 0 || scorePlayer2 < 0) {
            throw new RuntimeException("Scores must be non-negative.");
        }

        if (scorePlayer1 == scorePlayer2) {
            throw new RuntimeException("Scores cannot be equal. There must be a winner.");
        }

        if ((scorePlayer1 > 10 && scorePlayer2 > 10) && Math.abs(scorePlayer1 - scorePlayer2) < 2) {
            throw new RuntimeException("In a deuce situation, a player must win by at least 2 points.");
        }

        Game game = new Game();
        game.setPlayer1(playerRepository.findById(playerId1).get());
        game.setPlayer2(playerRepository.findById(playerId2).get());
        game.setScorePlayer1(scorePlayer1);
        game.setScorePlayer2(scorePlayer2);

        gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public PlayerResponse getGameWinnerByGameId(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + gameId));

        if (game.getScorePlayer1() > game.getScorePlayer2()) {
            return toResponse(game.getPlayer1());
        } else {
            return toResponse(game.getPlayer2());
        }
    }

    @Transactional
    public void deleteGameById(Long gameId) {
        if (!gameRepository.existsById(gameId)) {
            throw new RuntimeException("Game not found with id: " + gameId);
        }
        gameRepository.deleteById(gameId);
    }

    private PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getEmail(),
                player.getCountry(),
                player.getRanking()
        );
    }

    private GameResponse toResponseGame(Game game) {
        return new GameResponse(
                game.getId(),
                game.getPlayer1().getFirstName(),
                game.getPlayer2().getFirstName(),
                game.getScorePlayer1(),
                game.getScorePlayer2()
        );
    }
}
