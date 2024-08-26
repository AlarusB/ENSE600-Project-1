/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Weapon {
    private String name;
    private int attackPower;
    private double attackBonus;

    public Weapon(String name, int attackPower, double attackBonus) {
        this.name = name;
        this.attackPower = attackPower;
        this.attackBonus = attackBonus;
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public double getAttackBonus() {
        return attackBonus;
    }
}

