
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Enemy extends Entity implements Serializable {

    private double defenseReduction; // The amount by which the enemy's defense is reduced

    // Constructor for initializing the enemy's attributes
    public Enemy(String name, int level, int baseHP, int baseATK) {
        super(name, level, baseHP, baseATK);
        this.defenseReduction = 0;
        updateStats();
    }

    // Method to apply a defense reduction to the enemy
    public void applyDefenseReduction(int reduction) {
        this.defenseReduction = reduction;
    }

    @Override // Override the takeDamage method from the Entity class
    public void takeDamage(double damage) {
        // Calculate the total damage including defense reduction
        double totaldmg = (damage + (damage * (defenseReduction / 100)));
        setHP(getHP() - totaldmg); // Reduce the enemy's HP by the calculated damage
        System.out.println("Player attacks " + getName() + " for " + totaldmg + " damage!");

        // Check if the enemy is still alive; if not, announce the player as the winner
        if (!isAlive()) {
            System.out.println("winner!!!");
        }
    }

    // Method to calculate and return the experience points the player will receive after defeating the enemy
    public double dropXP() {
        return getLevel() * 10;
    }

    // Method to calculate and return the amount of gold the player will receive after defeating the enemy
    public double dropGold() {
        return getLevel() * 5;
    }

    // Getter for defenseReduction
    public double getDefenseReduction() {
        return defenseReduction;
    }

    // Setter for defenseReduction
    public void setDefenseReduction(double defenseReduction) {
        this.defenseReduction = defenseReduction;
    }
}
