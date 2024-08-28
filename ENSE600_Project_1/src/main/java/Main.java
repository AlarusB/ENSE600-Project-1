/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class Main {
    public static void main(String[] args) {
        Weapon startingWeapon = new Weapon("Bannana", 10);
        Player player = new Player("Guy", 1, 1000, 20, startingWeapon); //playerName, playerLevel, baseHealth, baseATK, weapon
        Game game = new Game(player);
        game.startGame();
    }
}

