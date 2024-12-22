package io.game.Wordly.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Generator {

    private static final Set<Integer> generatedIds = new HashSet<>();

    private static final Random random = new Random();

    public static int gameId() {
        int id;
        do {
            id = 1000 + random.nextInt(9000);
        } while (generatedIds.contains(id));

        generatedIds.add(id);
        return id;
    }
}
