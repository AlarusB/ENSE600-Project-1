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
    }

    private static Player createNewPlayer() {
        Weapon startingWeapon = new Weapon("bannana", 10);
        return new Player("Guy", 2, 1000, 20, startingWeapon); //playerName, playerLevel, baseHP, baseATK, weapon
    }
}
