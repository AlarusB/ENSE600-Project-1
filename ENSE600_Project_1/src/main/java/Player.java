
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Map;

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

    private final int maxPotions = 3;
    private Potion[] potionBag;

    private int gold;
    private double xp;

    private Weapon weapon;

    private int bonusATK;
    private Inventory inventory;

    // Constructor for initializing the player's attributes
    public Player(String name, int level, int baseHP, int baseATK, Weapon weapon, int gold, double xp) {
        super(name, level, baseHP, baseATK); // Call the superclass (Entity) constructor
        this.weapon = weapon;
        this.bonusATK = 0;
        this.potionBag = new Potion[maxPotions];
        this.gold = 0;
        this.xp = 0;
        this.inventory = new Inventory();
        updateStats();
    }

    // Method to apply a bonus attack to the player
    public void applyBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
        updateStats();
    }

    @Override // Update MaxHP and ATK based on the player's level, weapon, and bonuses
    protected void updateStats() {
        if (weapon != null) {
            this.setMaxHP(getBaseHP() * (1 + (getLevel() / 100.0)));
            this.setATK(getBaseATK() + weapon.getATK() + bonusATK + (getBaseATK() * (1 + (getLevel() / 10))));
        } else {
            // Fallback in case weapon is not set
            this.setMaxHP(getBaseHP() * (1 + (getLevel() / 100.0)));
            this.setATK(getBaseATK() + bonusATK + (getBaseATK() * (1 + (getLevel() / 10))));
        }
    }

    // Method to calculate the XP required for the next level
    public int getXPForNextLevel() {
        return (int) Math.pow(getLevel(), 2) * 100;
    }

// Method to check if the player has enough XP to level up
    private void checkLevelUp() {
        boolean leveledUp = false;
        while (this.xp >= getXPForNextLevel()) {
            this.xp -= getXPForNextLevel(); // Deduct XP required for level up
            setLevel(getLevel() + 1);
            leveledUp = true;
        }

        // Display a graphical message if the player leveled up
        if (leveledUp) {
            JOptionPane.showMessageDialog(null,
                    "Congratulations! You've leveled up to Level " + getLevel() + "!",
                    "Level Up",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method to save the player's data to a file
    public void saveToFile(String fileName) {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Player data saved to " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to load the player's data from a file
    public static Player loadFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Save file not found! A new game will start.");
            return null; // Return null if save file is not found
        }

        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Player) ois.readObject(); // Return the player object from the file
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs during loading
        }
    }

    // Getter for the player's weapon
    public Weapon getWeapon() {
        return weapon;
    }

    // Getter for the player's bonus attack
    public int getBonusATK() {
        return bonusATK;
    }

    // Retrieves a potion from the potion bag by index
    public Potion getPotion(int index) {
        if (index < 1 || index > maxPotions || potionBag[index - 1] == null) {
            System.out.println("Failed to find a potion!");
            return null;
        }
        return potionBag[index - 1];
    }

    // Getter for the potion bag's size
    public int getBagSize() {
        return maxPotions;
    }

    // Adds a potion to the potion bag
    public void addToPotionBag(Potion potion) {
        for (int i = 0; i < maxPotions; i++) {
            if (potionBag[i] == null) {
                potionBag[i] = potion;
                System.out.println(potion.getName() + " was added to the potion bag.");
                return;
            }
        }
        System.out.println("Potion bag was full! "+potion.getName() +" has been added to inventory!");
        addItemToInventory(potion.getId());
    }

    // Removes a potion from the potion bag
    public void removePotion(Potion potion) {
        for (int i = 0; i < maxPotions; i++) {
            if (potionBag[i] == potion) {
                potionBag[i] = null;
                return;
            }
        }
        System.out.println("Potion not found in the bag.");
    }

    // Lists out the potions in the potion bag
    public void listPotionBag() {
        for (int i = 0; i < maxPotions; i++) {
            if (potionBag[i] != null) {
                System.out.println((i + 1) + ". " + potionBag[i].getName());
            } else {
                System.out.println((i + 1) + ". Empty");
            }
        }
        System.out.println("4. Back");
    }

    // Setter for the player's weapon
    public void setWeapon(Weapon weapon) {
        if (this.weapon != null) {
            // Add the old weapon to the inventory
            System.out.println(this.weapon.getName() + " was unequipped and added to inventory.");
            addItemToInventory(this.weapon.getId());
        }
        this.weapon = weapon;
        updateStats(); // Update stats after changing the weapon
    }

    // Setter for the player's bonus attack
    public void setBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
        updateStats(); // Update stats after applying the bonus attack
    }

    // Getter for the player's current gold
    public int getGold() {
        return gold;
    }

    // Getter for the player's current experience points
    public double getXp() {
        return xp;
    }

    // Method to add experience points to the player and check for level up
    public void addXP(int amount) {
        this.xp += amount;
        checkLevelUp(); // Check if the player has enough XP to level up
    }

    // Method to add gold to the player's total
    public void addGold(int amount) {
        this.setGold(this.gold + amount);
    }

    // Setter for the player's gold
    public void setGold(int gold) {
        this.gold = gold;
    }

    public Potion[] getPotionBag() {
        return potionBag;
    }
    
    // Inventory Methods
    public void addItemToInventory(int itemId, int amount) {
        inventory.addItem(itemId, amount);
        viewInventory();
    }
    
    public void addItemToInventory(int itemId) {
        addItemToInventory(itemId, 1);
    }
    
    public void addItemToInventory(Item item, int amount) {
        addItemToInventory(item.getId(), amount);
    }

    public void addItemToInventory(Item item) {
        addItemToInventory(item.getId(), 1);
    }


    public void viewInventory() {
        inventory.viewInventory();
    }

    public void removeItemFromInventory(int itemId) {
        inventory.removeItem(itemId);
        viewInventory();
    }

    public boolean itemExistsInInventory(int itemId) {
        return inventory.itemExists(itemId);
    }
    
    public Map<Item, Integer> getInventoryItemMap() {
        return inventory.getItemMap();
    }
}
