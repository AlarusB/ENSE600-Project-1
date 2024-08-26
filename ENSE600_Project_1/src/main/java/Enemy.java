
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
    private double levelMultiplier;

    public Enemy(String name, int level, int baseHP, double levelMultiplier, int challengeRating) {
        this.name = name;
        this.level = level;
        this.baseHP = baseHP;
        this.levelMultiplier = levelMultiplier;
    }

    public int getLevel() {
        return level;
    }

    public int calculateMaxHP() {
        return (int) (baseHP * levelMultiplier);
    }


}
