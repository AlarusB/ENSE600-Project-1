
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
    private  int enemyHP;
    
    private  int baseATK;
    private  int enemyATK;
    
    private  int enemyDefenseReduction;

    public Enemy(String enemyName, int enemyLevel, int baseHP, int baseATK) {
        this.enemyName = enemyName;
        this.enemyLevel = enemyLevel;
        this.baseHP = baseHP;
        this.baseATK = baseATK;
        this.enemyDefenseReduction = 0;
        updateStats();
    }

    private void updateStats() {
        this.enemyHP = baseHP * (1 + (enemyLevel / 100));
        this.enemyATK = baseATK * (1 + (enemyLevel / 100));
    }

    public void applyDefenseReduction(int reduction) {
        this.enemyDefenseReduction = reduction;
    }
    
    public void takeDamage(int damage) {
        enemyHP = enemyHP - (damage * (1 * (enemyDefenseReduction / 100)));
        if (enemyHP < 0) enemyHP = 0;
        System.out.println("winner!!!");
    }
}
