package io.game.Wordly.entity;

import io.game.Wordly.utils.Generator;
import io.game.Wordly.utils.PyGrid;

import java.util.*;

public class Game {

    private final int gameId;

    private final Player firstPlayer;

    private final Player secondPlayer;

    private final Map<String, Integer> playerScores;

    private final Map<String, String> playerColors;

    private final List<String> acceptedWords;

    private final Cell[][] grid;

    private final String theme;

    private final String[] hiddenWords;

    public String[] getHiddenWords() {
        return hiddenWords;
    }

    public Map<String, Integer> getPlayerScores() {
        return playerScores;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Map<String, String> getPlayerColors() {
        return playerColors;
    }

    public String getTheme() {
        return theme;
    }

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.playerColors = new HashMap<>();
        this.playerColors.put(firstPlayer.getPlayerId(), "pink");
        this.playerColors.put(secondPlayer.getPlayerId(), "skyblue");
        this.playerScores = new HashMap<>();
        this.playerScores.put(firstPlayer.getPlayerId(), 0);
        this.playerScores.put(secondPlayer.getPlayerId(), 0);
        this.theme = PyGrid.randomTheme();
        this.hiddenWords = PyGrid.getWords(theme);
        this.grid = PyGrid.getGrid(hiddenWords);
        this.gameId = Generator.gameId();
        this.acceptedWords = new ArrayList<>();
    }

    public Player getPlayer(String playerId) {
        if (this.firstPlayer.getPlayerId().equals(playerId))
            return this.firstPlayer;

        else if (this.secondPlayer.getPlayerId().equals(playerId))
            return this.secondPlayer;

        return null;
    }

    public int getScore(Player player) {
        return this.playerScores.get(player.getPlayerId());
    }

    public boolean contains(Player player) {
        return this.firstPlayer.equals(player) || this.secondPlayer.equals(player);
    }

    public int getGameId() {
        return gameId;
    }

    public void setScore(Player player, int score) {
        this.playerScores.put(player.getPlayerId(), score);
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

    public void updateGrid(Word word) {
        for (Box box : word.getSelection()) {
            int x = box.x(); int y = box.y();
            this.getGrid()[y][x].setStatus(this.playerColors
                    .get(word.getPlayerId()));
        }
    }

    public List<String> getAcceptedWords() {
        return acceptedWords;
    }

    public String winningCondition() {
        if (this.getAcceptedWords().size() == this.hiddenWords.length) {
            if (Objects.equals(this.playerScores.get(firstPlayer.getPlayerId()),
                    this.playerScores.get(secondPlayer.getPlayerId()))) {
                return "tie";
            }

            Player winner = null;
            if (this.playerScores.get(firstPlayer.getPlayerId())
                    > this.playerScores.get(secondPlayer.getPlayerId()))
                winner = firstPlayer;
            else
                winner = secondPlayer;

            return winner.getPlayerId();
        }

        return "none";
    }
}
