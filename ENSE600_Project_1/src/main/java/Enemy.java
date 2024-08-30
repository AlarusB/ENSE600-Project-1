
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Enemy extends Entity implements Serializable{
    
    private double defenseReduction;

    public Enemy(String name, int level, int baseHP, int baseATK) {
        super(name, level, baseHP, baseATK);
        this.defenseReduction = 0;
        updateStats();
    }

    @Override
    protected void updateStats() {
        this.setMaxHP(getBaseHP() * (1 + (getLevel() / 10.0)));
        this.setATK(getBaseATK() * (1 + (getLevel() / 40)));
    }
    
    public void applyDefenseReduction(int reduction) {
        this.defenseReduction = reduction;
    }
    
    @Override
    public void takeDamage(double damage) {
        double totaldmg = (damage + (damage * (defenseReduction/100)));
        setHP(getHP() - totaldmg);
        System.out.println("Player attacks " + getName() + " for " + totaldmg + " damage!");

        if (getHP() == 0) {
            System.out.println("winner!!!");
        }
    }

    public double getDefenseReduction() { return defenseReduction; }
    public void setDefenseReduction(double defenseReduction) { this.defenseReduction = defenseReduction; }

}
