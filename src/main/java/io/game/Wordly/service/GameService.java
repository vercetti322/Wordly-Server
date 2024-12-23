package io.game.Wordly.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.game.Wordly.dto.GameDto;
import io.game.Wordly.entity.Game;
import io.game.Wordly.entity.Player;
import org.springframework.stereotype.Service;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class GameService {

    private final SimpMessagingTemplate messagingTemplate;

    private final List<Game> games = new CopyOnWriteArrayList<>();

    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void createGame(Player firstPlayer, Player secondPlayer) {
        Game game = new Game(firstPlayer, secondPlayer);
        this.games.add(game);
        this.broadcastGame(game);
        System.out.println("Game created between: " + firstPlayer + " and " + secondPlayer);
    }

    private void broadcastGame(Game game) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (Player player : game.getPlayers()) {
                    messagingTemplate.convertAndSend("/player/gameObject/" +
                            player.getPlayerId(), getGameDto(game, player));
                }
            }
        }, 175);
    }


    public Player getOtherPlayer(Player player) {
        // Find the game the player is part of
        Game currGame = null;
        for (Game game : this.games) {
            if (game.contains(player)) {
                currGame = game;
                break;
            }
        }

        if (currGame != null)
            return currGame.getOtherPlayer(player);

        return null;
    }

    public GameDto getGameDto(Game game, Player player) {
        return new GameDto(game.getGameId(), game
                .getOtherPlayer(player), game.getGrid(),
                game.getTheme(), game.getPlayerScores());
    }


    public Game getGame(Player player) {
        for (Game game : this.games) {
            if (game.contains(player)) {
                return game;
            }
        }

        return null;
    }

    public void endGame(Player currPlayer) {
        Game gameToRemove = null;
        for (Game game : this.games) {
            if (game.contains(currPlayer)) {
                gameToRemove = game;
                break;
            }
        }

        if (gameToRemove != null) {
            // Remove the game from the list
            this.games.remove(gameToRemove);
        }
    }
}
