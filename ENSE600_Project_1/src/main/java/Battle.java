/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Battle {

    private Player player;
    private Enemy enemy;

    // Constructor that initializes the player and enemy involved in the battle
    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    // Method for the player to attack the enemy
    public double attackEnemy() {
        double damagePlayer = player.getATK();
        enemy.takeDamage(damagePlayer);
        return damagePlayer;
    }

    // Method for the enemy to attack the player
    public double attackPlayer() {
        double damageEnemy = enemy.getATK();
        player.takeDamage(damageEnemy);
        

        // Print a message showing the enemy's attack on the player
        System.out.println(enemy.getName() + " attacks " + player.getName()
                + " for " + damageEnemy + " damage!\n");
        return damageEnemy;
    }
}
