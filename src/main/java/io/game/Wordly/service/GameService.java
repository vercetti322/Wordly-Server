package io.game.Wordly.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
                messagingTemplate.convertAndSend("/player/gameObject/" +
                        game.getFirstPlayer().getPlayerId(), game);

                messagingTemplate.convertAndSend("/player/gameObject/" +
                        game.getSecondPlayer().getPlayerId(), game);

                System.out.println("Game broadcast to players: " + game.getFirstPlayer()
                        .getPlayerId() + ", " + game.getSecondPlayer().getPlayerId());
            }
        }, 150);
    }


    public Player getOtherPlayer(Player player) {
        // Find the game the player is part of
        Game gameToRemove = null;
        for (Game game : this.games) {
            if (game.getFirstPlayer().equals(player) || game.getSecondPlayer().equals(player)) {
                gameToRemove = game;
                break;
            }
        }

        if (gameToRemove != null) {
            return gameToRemove.getFirstPlayer().equals(player)
                    ? gameToRemove.getSecondPlayer()
                    : gameToRemove.getFirstPlayer();
        }

        return null;
    }


    public Game getGame(Player player) {
        for (Game game : this.games) {
            if (game.getFirstPlayer().equals(player) ||
                    game.getSecondPlayer().equals(player)) {
                return game;
            }
        }

        return null;
    }

    public void endGame(Player currPlayer) {
        Game gameToRemove = null;
        for (Game game : this.games) {
            if (game.getFirstPlayer().equals(currPlayer) ||
                    game.getSecondPlayer().equals(currPlayer)) {
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
