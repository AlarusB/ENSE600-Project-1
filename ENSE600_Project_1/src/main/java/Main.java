
import javax.swing.*;
import java.io.File;
import java.io.Serializable;

/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */
/**
 *
 * @author abdul
 */
public class Main implements Serializable {

    public static void main(String[] args) {

        String fileName = "player_data.dat";
        Player player;

        // Display dialog asking the user whether they want to load a game or start a new one
        int response = JOptionPane.showOptionDialog(null,
                "Would you like to load the saved game or start a new one?",
                "Game Start",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Load Game", "New Game"}, // Button options
                "Load Game"); // Default option

        if (response == JOptionPane.YES_OPTION) { // User chose to load the game
            // Check if save file exists
            File file = new File(fileName);
            if (file.exists()) {
                player = Player.loadFromFile(fileName);
                if (player == null) {
                    JOptionPane.showMessageDialog(null,
                            "Error loading game. Starting a new game instead.",
                            "Load Error",
                            JOptionPane.ERROR_MESSAGE);
                    player = createNewPlayer();
                } else {
                    DBInitializer dbInitializer = new DBInitializer(false);
                    dbInitializer.setupDatabase();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "No saved game found. Starting a new game.",
                        "No Saved Game",
                        JOptionPane.INFORMATION_MESSAGE);
                player = createNewPlayer();
            }
        } else { // User chose to start a new game
            player = createNewPlayer();
        }

        GameGUI game = new GameGUI(player);
        game.setVisible(true);
        game.startGame();
    }

    // Creates a new Player with default stats
    public static Player createNewPlayer() {
        Weapon startingWeapon = ItemFactory.createWeapon(1);
        // Clear inventory table in database
        DBInitializer dbInitializer = new DBInitializer(true);
        dbInitializer.setupDatabase();

        // playerName, playerLevel, baseHP, baseATK, weapon, gold, xp
        return new Player("Guy", 2, 1000, 20, startingWeapon, 0, 0.0);
    }
}
