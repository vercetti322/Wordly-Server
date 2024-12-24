package io.game.Wordly.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Word {

    private final String text;

    private final List<Box> selection;

    private final String playerId;

    public boolean contains(int x, int y) {
        for (Box temp : this.selection)
            if (temp.x() == x && temp.y() == y)
                return true;

        return false;
    }


    @JsonCreator
    public Word(@JsonProperty("text") String text,
                @JsonProperty("selection") List<Box> selection,
                @JsonProperty("playerId") String playerId) {
        this.text = text;
        this.selection = selection;
        this.playerId = playerId;
    }

    public int getLength() {
        return this.text.length();
    }

    public String getText() {
        return text;
    }


    public List<Box> getSelection() {
        return selection;
    }

    public String getPlayerId() {
        return playerId;
    }
}

record Box(int x, int y) {

    @JsonCreator
    Box(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }
}
