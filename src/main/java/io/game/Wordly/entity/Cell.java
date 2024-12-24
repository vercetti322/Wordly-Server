package io.game.Wordly.entity;

public class Cell {

    private final char letter;

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public Cell(char letter) {
        this.letter = letter;
        this.status = "#333";
    }

    public String getStatus() {
        return status;
    }

    public char getLetter() {
        return letter;
    }
}
