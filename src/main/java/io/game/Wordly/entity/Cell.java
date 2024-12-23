package io.game.Wordly.entity;

public class Cell {

    private char letter;

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Cell(char letter) {
        this.letter = letter;
        this.status = 1;
    }

    public int getStatus() {
        return status;
    }

    public char getLetter() {
        return letter;
    }
}
