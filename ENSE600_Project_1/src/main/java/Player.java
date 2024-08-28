
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
    private int playerLevel;

    private int baseHP;
    private int playerHP;

    private int baseATK;
    private int playerATK;

    private Weapon weapon;

    private int bonusATK;

    public Player(String playerName, int playerLevel, int baseHealth, int baseATK, Weapon weapon) {
        this.playerName = playerName;
        this.playerLevel = playerLevel;
        this.baseHP = baseHP;
        this.baseATK = baseATK;
        this.weapon = weapon;
        this.bonusATK = 0;
        updateStats();
    }

    public void applyBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
        updateStats();
    }

    private void updateStats() {
        this.playerHP = baseHP * (1 + (playerLevel / 100));
        this.playerATK = baseATK + weapon.getWeaponATK() + bonusATK;
    }

    public void takeDamage(int damage) {
        playerHP -= damage;
        if (getPlayerHP() < 0) {
            playerHP = 0;
        }
        System.out.println("player dead...");
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
    public int getPlayerLevel() { return playerLevel; }
    public int getPlayerHP() { return playerHP; }
    public int getPlayerATK() { return playerATK; }
    public Weapon getWeapon() { return weapon; }
    public int getBonusATK() { return bonusATK; }
    
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; updateStats(); }
    public void setBaseHealth(int baseHP) { this.baseHP = baseHP; updateStats(); }
    public void setBaseATK(int baseATK) { this.baseATK = baseATK; updateStats(); }
    public void setWeapon(Weapon weapon) { this.weapon = weapon; updateStats(); }
    public void setBonusATK(int bonusATK) { this.bonusATK = bonusATK; updateStats(); }
}
