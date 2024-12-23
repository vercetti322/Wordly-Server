package io.game.Wordly.entity;

import io.game.Wordly.utils.Generator;
import io.game.Wordly.utils.PyGrid;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private final int gameId;

    private final Player firstPlayer;

    private final Player secondPlayer;

    private final Map<Player, Integer> playerScores;

    private final Cell[][] grid;

    private final String theme;

    private final String[] hiddenWords;

    public String[] getHiddenWords() {
        return hiddenWords;
    }

    public Map<Player, Integer> getPlayerScores() {
        return playerScores;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public String getTheme() {
        return theme;
    }

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.playerScores = new HashMap<>();
        this.playerScores.put(firstPlayer, 0);
        this.playerScores.put(secondPlayer, 0);
        this.theme = PyGrid.randomTheme();
        this.hiddenWords = PyGrid.getWords(theme);
        this.grid = PyGrid.getGrid(hiddenWords);
        this.gameId = Generator.gameId();
    }

    public Player getPlayer(String playerId) {
        if (this.firstPlayer.getPlayerId().equals(playerId))
            return this.firstPlayer;

        else if (this.secondPlayer.getPlayerId().equals(playerId))
            return this.secondPlayer;

        return null;
    }

    public int getScore(Player player) {
        return this.playerScores.get(player);
    }

    public boolean contains(Player player) {
        return this.firstPlayer.equals(player) || this.secondPlayer.equals(player);
    }

    public int getGameId() {
        return gameId;
    }

    public void setScore(Player player, int score) {
        this.playerScores.put(player, score);
    }

    public Cell[][] getGrid() {
        return this.grid;
    }

    public Player getOtherPlayer(Player player) {
        if (this.contains(player))
            return player.equals(this.firstPlayer)
                    ? this.secondPlayer : this.firstPlayer;

        return null;
    }

    public Player[] getPlayers() {
        return new Player[]{this.firstPlayer, this.secondPlayer};
    }
}
