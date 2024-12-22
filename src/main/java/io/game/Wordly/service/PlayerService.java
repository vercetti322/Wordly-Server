package io.game.Wordly.service;

import io.game.Wordly.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    private final SimpMessagingTemplate messagingTemplate;

    private final List<Player> playerList = Collections.synchronizedList(new LinkedList<>());

    @Autowired
    private final GameService gameService;

    public PlayerService(SimpMessagingTemplate messagingTemplate, GameService gameService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
    }

    public synchronized Player addPlayer(String name, String sessionId) {
        Player player = new Player(sessionId, name);
        this.playerList.add(player);
        this.sendPlayerId(player);
        this.broadcastOnlinePlayers();
        this.matchMake();
        return player;
    }

    private void sendPlayerId(Player player) {
        this.messagingTemplate.convertAndSend("/player/" + player
                .getName(), player.getPlayerId());
    }

    private synchronized void matchMake() {
        if (this.playerList.size() >= 2) {
            List<Player> availablePlayers = this.playerList
                    .stream().filter(player -> !player.isInGame())
                    .limit(2).toList();

            if (availablePlayers.size() == 2) {
                availablePlayers.forEach(player -> player.setInGame(true));
                this.gameService.createGame(availablePlayers.getFirst(),
                        availablePlayers.get(1));
            }
        }
    }

    public synchronized void removePlayer(String sessionId) {
        Player currPlayer = null;
        for (Player player : playerList) {
            if (player.getPlayerId().equals(sessionId)) {
                currPlayer = player;
                break;
            }
        }

        // If the current player is found
        if (currPlayer != null) {
            // Find the other player in the game
            Player otherPlayer = this.gameService.getOtherPlayer(currPlayer);
            System.out.println(otherPlayer);

            if (otherPlayer != null) {
                this.playerList.remove(currPlayer);
                this.playerList.remove(otherPlayer);

                // End the game by removing it
                this.messagingTemplate.convertAndSend("/game/" +
                        this.gameService.getGame(currPlayer).getGameId(), "ended");

                this.gameService.endGame(currPlayer);
            }
        }

        // Broadcast updated player count
        this.broadcastOnlinePlayers();
    }


    public void broadcastOnlinePlayers() {
        this.messagingTemplate.convertAndSend("/player"
                , this.playerList.size());
    }
}
