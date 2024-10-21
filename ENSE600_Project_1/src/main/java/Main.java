/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */

import java.io.File;
import java.io.Serializable;

public class Main implements Serializable{
    public static void main(String[] args) {
        String fileName = "player_data.dat";
        Player player;
        
        // Check if save file exists
        File file = new File(fileName);
        if (file.exists()) {
            player = Player.loadFromFile(fileName);
            if (player == null) {
                player = createNewPlayer();
            }
        } else {
            player = createNewPlayer();
        }

        Game game = new Game(player);
        game.startGame();
        
        //GameGUI game = new GameGUI(player);
        //game.setVisible(true);
        //game.startGame();
    }
    // Creates a new Player with default stats
    private static Player createNewPlayer() {
        Weapon startingWeapon = new Weapon("Bannana", 10, "A banana.");
        //playerName, playerLevel, baseHP, baseATK, weapon, gold, xp
        return new Player("Guy", 2, 1000, 20, startingWeapon, 0, 0.0); 
    }
}
