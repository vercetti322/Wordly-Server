package io.game.Wordly.entity;

import io.game.Wordly.utils.Generator;

public class Game {

    private int gameId;

    private Player firstPlayer;

    private Player secondPlayer;

    private WordGrid grid;

    private int firstPlayerScore;

    private int secondPlayerScore;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.grid = new WordGrid();
        this.firstPlayerScore = 0;
        this.secondPlayerScore = 0;
        this.gameId = Generator.gameId();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public void setGrid(WordGrid grid) {
        this.grid = grid;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public WordGrid getGrid() {
        return grid;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

}
