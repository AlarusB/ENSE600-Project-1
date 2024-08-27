/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Potion {
    public enum PotionType { BUFF, DEBUFF }

    private String potionName;
    private PotionType potionType;
    private int potionEffectValue;

    public Potion(String potionName, PotionType potionType, int potionEffectValue) {
        this.potionName = potionName;
        this.potionType = potionType;
        this.potionEffectValue = potionEffectValue;
    }

    public String getPotionName() {
        return potionName;
    }

    public PotionType getPotionType() {
        return potionType;
    }

    public int getPotionEffectValue() {
        return potionEffectValue;
    }
}


