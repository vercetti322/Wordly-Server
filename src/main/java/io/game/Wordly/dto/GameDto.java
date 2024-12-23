package io.game.Wordly.dto;

import io.game.Wordly.entity.Cell;
import io.game.Wordly.entity.Player;

import java.util.Map;

public class GameDto {
    private final int gameId;

    private final Player otherPlayer;

    private final Cell[][] grid;

    private final String theme;

    private final Map<Player, Integer> playerScores;

    public GameDto(int gameId, Player otherPlayer, Cell[][] grid, String theme, Map<Player, Integer> playerScores) {
        this.gameId = gameId;
        this.otherPlayer = otherPlayer;
        this.grid = grid;
        this.theme = theme;
        this.playerScores = playerScores;
    }

    public int getGameId() {
        return gameId;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public String getTheme() {
        return theme;
    }

    public Map<Player, Integer> getPlayerScores() {
        return playerScores;
    }
}
