
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
        if (playerHP < 0) {
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

    // more methods like takeDamage(), attack(), etc.
}
