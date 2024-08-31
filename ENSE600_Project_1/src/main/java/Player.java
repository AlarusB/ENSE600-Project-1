
import java.io.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Player extends Entity {

    private static final long serialVersionUID = 1L;
    private Consumable attackPotion;
    private Consumable weakenPotion;

    private int gold;
    private double xp;

    private Weapon weapon;

    private int bonusATK;

    public Player(String name, int level, int baseHP, int baseATK, Weapon weapon, int gold, double xp) {
        super(name, level, baseHP, baseATK);
        this.weapon = weapon;
        this.bonusATK = 0;
        this.attackPotion = new AttackPotion("Attack Potion", 50);
        this.weakenPotion = new WeakenPotion("weaken or somthing idk", 10);
        this.gold = 0;
        this.xp = 0;
        updateStats();
    }

    public void applyBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
        updateStats();
    }

    @Override
    protected void updateStats() {
        if (weapon != null) {
            this.setMaxHP(getBaseHP() * (1 + (getLevel() / 100.0)));
            this.setATK(getBaseATK() + weapon.getWeaponATK() + bonusATK + (getBaseATK() * (1 + (getLevel() / 10))));
        } else {
            // Fallback in case weapon is not set
            this.setMaxHP(getBaseHP() * (1 + (getLevel() / 100.0)));
            this.setATK(getBaseATK() + bonusATK + (getBaseATK() * (1 + (getLevel() / 10))));
        }
    }

    public int getXPForNextLevel() {
        return (int) Math.pow(getLevel(), 2) * 100; // XP required for the next level
    }

    private void checkLevelUp() {
        while (this.xp >= getXPForNextLevel()) {
            this.xp -= getXPForNextLevel();
            setLevel(getLevel() + 1);
            System.out.println("Congratulations! You've leveled up to Level " + getLevel() + "!");
        }
    }

    public void saveToFile(String fileName) {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Player data saved to " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Player loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Save file not found! A new game will start.");
            return null; // or handle this by returning a new player or a specific state
        }

        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Player) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getBonusATK() {
        return bonusATK;
    }

    public Consumable getAttackPotion() {
        return attackPotion;
    }

    public Consumable getWeakenPotion() {
        return weakenPotion;
    }

    public boolean hasAttackPotion() {
        return attackPotion != null;
    }

    public boolean hasWeakenPotion() {
        return weakenPotion != null;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        updateStats();
    }

    public void setBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
        updateStats();
    }

    public void setAttackPotion(Potion attackPotion) {
        this.attackPotion = attackPotion;
        updateStats();
    }

    public void setWeakenPotion(Potion weakenPotion) {
        this.weakenPotion = weakenPotion;
        updateStats();
    }

    public int getGold() {
        return gold;
    }

    public double getXp() {
        return xp;
    }

    public void addXP(int amount) {
        this.xp += amount;
        checkLevelUp();
    }

    public void addGold(int amount) {
        this.setGold(this.gold + amount);
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
