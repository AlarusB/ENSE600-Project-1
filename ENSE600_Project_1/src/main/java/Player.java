
import java.io.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Player {

    private String playerName;
    private double playerLevel;

    private double baseHP;
    private double maxPlayerHP;
    private double currentPlayerHP;

    private int baseATK;
    private int playerATK;

    private Weapon weapon;

    private int bonusATK;

    public Player(String playerName, int playerLevel, int baseHP, int baseATK, Weapon weapon) {
        this.playerName = playerName;
        this.playerLevel = playerLevel;
        this.baseHP = baseHP;
        this.baseATK = baseATK;
        this.weapon = weapon;
        this.bonusATK = 0;
        updateStats();
        currentPlayerHP = maxPlayerHP;
    }

    public void applyBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
        updateStats();
    }

    private void updateStats() {
        this.playerATK = baseATK + weapon.getWeaponATK() + bonusATK;
        this.maxPlayerHP = baseHP * (1 + (playerLevel / 100));

    }

    public void takeDamage(int damage) {
        currentPlayerHP -= damage;
        if (currentPlayerHP < 0) {
            currentPlayerHP = 0;
            System.out.println("player dead...");
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

    public String getPlayerName() { return playerName; }
    public double getPlayerLevel() { return playerLevel; }
    public double getMaxPlayerHP() { return maxPlayerHP; }
    public double getCurrentPlayerHP() { return currentPlayerHP; }
    public int getPlayerATK() { return playerATK; }
    public Weapon getWeapon() { return weapon; }
    public int getBonusATK() { return bonusATK; }
    
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public void setPlayerLevel(double playerLevel) { this.playerLevel = playerLevel; updateStats(); }
    public void setBaseHealth(int baseHP) { this.baseHP = baseHP; updateStats(); }
    public void setBaseATK(int baseATK) { this.baseATK = baseATK; updateStats(); }
    public void setWeapon(Weapon weapon) { this.weapon = weapon; updateStats(); }
    public void setBonusATK(int bonusATK) { this.bonusATK = bonusATK; updateStats(); }
}
