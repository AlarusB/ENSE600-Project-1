
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

    private Weapon weapon;

    private int bonusATK;

    public Player(String name, int level, int baseHP, int baseATK, Weapon weapon) {
        super(name, level, baseHP, baseATK);
        this.weapon = weapon;
        this.bonusATK = 0;
        this.attackPotion = new AttackPotion("Attack Potion", 50);
        this.weakenPotion = new WeakenPotion("weaken or somthing idk", 10);
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
            this.setATK(getBaseATK() + weapon.getWeaponATK() + bonusATK);
        } else {
            // Fallback in case weapon is not set
            this.setMaxHP(getBaseHP() * (1 + (getLevel() / 100.0)));
            this.setATK(getBaseATK() + bonusATK);
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
}
