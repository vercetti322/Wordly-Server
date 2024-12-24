package io.game.Wordly.dto;

import io.game.Wordly.entity.Cell;
import io.game.Wordly.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameDto {

    private final int gameId;

    private final Map<String, Player> players;

    private final Map<String, String> playerColors;

    private final Cell[][] grid;

    private final String theme;

    private final Map<String, Integer> playerScores;

    public GameDto(int gameId, Player first, Player second, Map<String, String> playerColors, Cell[][] grid,
                   String theme, Map<String, Integer> playerScores) {
        this.gameId = gameId;
        this.playerColors = playerColors;
        this.players = new HashMap<>();
        this.players.put(first.getPlayerId(), first);
        this.players.put(second.getPlayerId(), second);
        this.grid = grid;
        this.theme = theme;
        this.playerScores = playerScores;
    }

    public Map<String, String> getPlayerColors() {
        return playerColors;
    }

    public int getGameId() {
        return gameId;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }


    public Cell[][] getGrid() {
        return grid;
    }

    public String getTheme() {
        return theme;
    }

    public Map<String, Integer> getPlayerScores() {
        return playerScores;
    }

}
