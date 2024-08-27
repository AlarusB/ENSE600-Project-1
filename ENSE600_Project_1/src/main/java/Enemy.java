
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Enemy implements Serializable {

    private String name;
    private int level;
    private int baseHP;
    private int baseAttack;
    private double currentDefenseReduction;

    public Enemy(String name, int level, int baseHP, double currentDefenseReduction) {
        this.name = name;
        this.level = level;
        this.baseHP = baseHP;
        this.baseAttack = baseAttack;
        this.currentDefenseReduction = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int EHP() {
        return (baseHP * (1 + (level / 100)));
    }
    
    public int EATK() {
        return (baseAttack * (1 + (level / 100)));
    }

    public double getCurrentDefenseReduction() {
        return currentDefenseReduction;
    }

    public void attack(Player player) {
        int damage = EATK();
        System.out.println(name + " attacks " + player.getName() + " for " + damage + " damage!");
        player.takeDamage(damage);
    }

    public boolean isDefeated() {
        return EHP() <= 0;
    }

}
