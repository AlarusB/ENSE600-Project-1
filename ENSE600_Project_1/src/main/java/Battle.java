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
        int damage = player.getATK();
        enemy.takeDamage(damage);
    }

    public void attackPlayer() {
        double damage = enemy.getATK();
        player.takeDamage(damage);
        System.out.println(enemy.getName() + " attacks " + player.getName()+ " for " + damage + " damage!\n");
    }

    // more battle logic...
}

