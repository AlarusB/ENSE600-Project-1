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

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void attackEnemy() {
        int damage = player.getPlayerATK();
        enemy.takeDamage(damage);
        System.out.println(player.getPlayerName() + " attacks " + enemy.getEnemyName() + " for " + damage + " damage!");
    }

    public void attackPlayer() {
        int damage = enemy.getEnemyATK();
        player.takeDamage(damage);
        System.out.println(enemy.getEnemyName() + " attacks " + player.getPlayerName() + " for " + damage + " damage!");
    }

    // more battle logic...
}

