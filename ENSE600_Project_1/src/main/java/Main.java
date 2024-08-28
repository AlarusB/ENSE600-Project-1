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
        Player player = new Player("Guy", 2, 1000, 20, startingWeapon); //playerName, playerLevel, baseHealth, baseATK, weapon
        Enemy enemy = new Enemy("Goblin", 5, 500, 10);  // enemyName, enemyLevel, baseHP, baseATK
        Game game = new Game(player, enemy);
        game.startGame();
    }
}

