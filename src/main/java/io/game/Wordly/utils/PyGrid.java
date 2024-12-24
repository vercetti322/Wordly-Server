package io.game.Wordly.utils;

import io.game.Wordly.entity.Cell;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PyGrid {

    public static final Map<String, String[]> themes = new HashMap<>(7);

    static {
        themes.put("Nature", new String[] {
                "TREE", "RIVER", "MOUNTAIN", "ANIMAL", "FOREST", "FLOWER", "DESERT",
                "SKY", "CLOUD", "WIND", "OCEAN", "SUN", "MOON", "GRASS", "VALLEY"
        });

        themes.put("Landmarks", new String[] {
                "EIFFEL", "PYRAMID", "LIBERTY", "TAJ", "WALL", "MACHU", "COLISEUM",
                "BIGBEN", "CHRIST", "TOWER", "BRIDGE", "SPHINX", "OPERA", "CASTLE", "GIZA"
        });

        themes.put("Food", new String[] {
                "APPLE", "BURGER", "COFFEE", "CAKE", "PIZZA", "CARROT", "BREAD",
                "MILK", "TEA", "FRIES", "SALAD", "CHOCOLATE", "JUICE", "CHEESE", "ICECREAM"
        });

        themes.put("Sports", new String[] {
                "SOCCER", "TENNIS", "CRICKET", "CHESS", "BASKETBALL", "BASEBALL", "RUGBY",
                "GOLF", "BOXING", "SWIMMING", "CYCLING", "FOOTBALL", "SKATING", "WRESTLING", "HOCKEY"
        });

        themes.put("Movies", new String[] {
                "TITANIC", "OPPENHEIMER", "AVENGERS", "INCEPTION", "MATRIX", "FROZEN", "MOANA",
                "MINIONS", "SHERLOCK", "LOST", "STREE", "GODFATHER", "BATMAN", "SUPERMAN", "CARS"
        });

        themes.put("Wildlife", new String[] {
                "DOG", "CAT", "ELEPHANT", "LION", "TIGER", "EAGLE", "PARROT",
                "DOLPHIN", "SHARK", "CROCODILE", "HORSE", "ZEBRA", "OWL", "MONKEY", "SNAKE"
        });

        themes.put("Space", new String[] {
                "EARTH", "MARS", "VENUS", "SATURN", "JUPITER", "URANUS", "NEPTUNE",
                "PLUTO", "MOON", "SUN", "COMET", "ASTEROID", "GALAXY", "ORBIT", "STAR"
        });
    }

    public static String randomTheme() {
        Random random = new Random();
        Object[] keys = themes.keySet().toArray();
        return (String) keys[random.nextInt(keys.length)];
    }

    public static String[] getWords(String theme) {
        return themes.get(theme);
    }

    public static Cell[][] getGrid(String[] words) {
        Cell[][] grid = null;

        try {
            // Path to the Python script
            String scriptPath = "C:\\Users\\HP\\Desktop\\Dev\\word.py";

            // Create a comma-separated string from the words array
            String argument = String.join(",", words);

            // Path to Python executable
            String pyPath = "C:\\Users\\HP\\AppData\\Local\\Programs\\Python\\Python313\\python.exe";

            // ProcessBuilder to run the Python script with arguments
            ProcessBuilder processBuilder = new ProcessBuilder(pyPath, scriptPath, argument);
            processBuilder.redirectErrorStream(true);

            // Start the process and capture output
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line); // Collect output
            }

            process.waitFor();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(output.toString());
            JSONArray puzzleArray = jsonResponse.getJSONArray("puzzle");

            // Convert the puzzle array into a Cell[][] grid
            int rows = puzzleArray.length();
            int cols = puzzleArray.getJSONArray(0).length();

            grid = new Cell[rows][cols];

            // Populate the grid with Cell objects
            for (int i = 0; i < rows; i++) {
                JSONArray row = puzzleArray.getJSONArray(i);
                for (int j = 0; j < cols; j++) {
                    char letter = row.getString(j).charAt(0); // Extract the letter from the puzzle
                    grid[i][j] = new Cell(letter); // Create new Cell with status set to 1 by default
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return grid;
    }
}
