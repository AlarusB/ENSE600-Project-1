/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Player {

    private String name;
    private int level;
    private int health;
    private int attackCharacter;
    private Weapon weapon;
    private double attackBonus;
    private double defenseIgnore;

    public Player(String name, int level, int health, int attackCharacter, Weapon weapon, double defenseIgnore) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.attackCharacter = attackCharacter;
        this.weapon = weapon;
        this.defenseIgnore = defenseIgnore;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return  (int) (health * (1 + (level / 100)));
    }

    public int getAttack() {
        return (int) ((attackCharacter + weapon.getAttackPower()) * (1 + weapon.getAttackBonus()));
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }
    
    public void changeWeapon(Weapon newWeapon) {
        this.weapon = newWeapon;
        System.out.println("Equipped " + newWeapon.getName());
    }
}
