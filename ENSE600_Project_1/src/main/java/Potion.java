/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Potion {
    private String name;
    private double effectValue;
    private PotionType type;

    public enum PotionType {
        DEFENSE_REDUCTION,
        ATTACK_BONUS
    }

    public Potion(String name, double effectValue, PotionType type) {
        this.name = name;
        this.effectValue = effectValue;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getEffectValue() {
        return effectValue;
    }

    public PotionType getType() {
        return type;
    }
}

