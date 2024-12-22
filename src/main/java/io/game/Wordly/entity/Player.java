package io.game.Wordly.entity;

public class Player {

    private final String playerId;

    private final String name;

    private boolean inGame;

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public Player(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.inGame = false;
    }

    @Override
    public String toString() {
        return "Player{name='" + this.name + "', " +
                "sessionId='" + this.playerId + "'}";
    }

    public boolean isInGame() {
        return inGame;
    }
}
