
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Enemy {
    private  String enemyName;
    private  int enemyLevel;
    
    private  int baseHP;
    private  double enemyHP;
    
    private  int baseATK;
    private  int enemyATK;
    
    private  double enemyDefenseReduction;

    public Enemy(String enemyName, int enemyLevel, int baseHP, int baseATK) {
        this.enemyName = enemyName;
        this.enemyLevel = enemyLevel;
        this.baseHP = baseHP;
        this.baseATK = baseATK;
        this.enemyDefenseReduction = 0;
        this.enemyHP = baseHP * (1 + (enemyLevel / 100));
        updateStats();
    }

    private void updateStats() {
        this.enemyATK = baseATK * (1 + (enemyLevel / 100));
    }

    public void applyDefenseReduction(int reduction) {
        this.enemyDefenseReduction = reduction;
    }
    
    public void takeDamage(int damage) {
        double totaldmg = (damage + (damage * (enemyDefenseReduction/100)));
        enemyHP -= totaldmg;
        System.out.println("Player attacks " + enemyName + " for " + totaldmg + " damage!");

        if (getEnemyHP() < 0) {
            enemyHP = 0;
            System.out.println("winner!!!");
        }
    }

public String getEnemyName() { return enemyName; }
    public int getEnemyLevel() { return enemyLevel; }
    public double getEnemyHP() { return enemyHP; }
    public int getEnemyATK() { return enemyATK; }
    public double getEnemyDefenseReduction() { return enemyDefenseReduction; }

    public void setEnemyName(String enemyName) { this.enemyName = enemyName; }
    public void setEnemyLevel(int enemyLevel) { this.enemyLevel = enemyLevel; updateStats(); }
    public void setBaseHP(int baseHP) { this.baseHP = baseHP; updateStats(); }
    public void setBaseATK(int baseATK) { this.baseATK = baseATK; updateStats(); }
    public void setEnemyDefenseReduction(double enemyDefenseReduction) { this.enemyDefenseReduction = enemyDefenseReduction; }

}
