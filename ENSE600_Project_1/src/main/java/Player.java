
import java.io.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Player extends Entity{
    
    private Potion attackPotion;
    private Potion weakenPotion;

    private Weapon weapon;

    private int bonusATK;

    public Player(String name, int level, int baseHP, int baseATK, Weapon weapon) {
        super(name, level, baseHP, baseATK);
        this.weapon = weapon;
        this.bonusATK = 0;
        this.attackPotion = new Potion("Attack Potion", Potion.PotionType.BUFF, 50);
        this.weakenPotion = new Potion("weaken or somthing idk", Potion.PotionType.DEBUFF, 10);
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
    
    public void saveToFile(String filename) {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Failed to save player data: " + e.getMessage());
        }
    }

    public static Player loadFromFile(String filename) {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Player) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load player data: " + e.getMessage());
            return null;
        }
    }


    public Weapon getWeapon() { return weapon; }
    public int getBonusATK() { return bonusATK; }
    public Potion getAttackPotion() { return attackPotion; }
    public Potion getWeakenPotion() { return weakenPotion; }
    public boolean hasAttackPotion() { return attackPotion != null; }
    public boolean hasWeakenPotion() { return weakenPotion != null; }

   
    public void setWeapon(Weapon weapon) { this.weapon = weapon; updateStats(); }
    public void setBonusATK(int bonusATK) { this.bonusATK = bonusATK; updateStats(); }
    public void setAttackPotion(Potion attackPotion) { this.attackPotion = attackPotion; updateStats(); }
    public void setWeakenPotion(Potion weakenPotion) { this.weakenPotion = weakenPotion; updateStats(); }
}
