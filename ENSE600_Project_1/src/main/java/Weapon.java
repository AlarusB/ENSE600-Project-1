
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Weapon implements Serializable, Item {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final String description;
    private final int cost;
    private final int ATK;
    private final int itemId;

    // Constructor for initializing the weapon's attributes
    public Weapon(int itemId, String name, int baseCost, String description, int ATK, int costFactor) {
        this.itemId = itemId;
        this.name = name;
        this.ATK = ATK; 
        this.cost = baseCost + ATK * costFactor;
        this.description = description; 
    }
    
    // Implemented method from the Item interface to get the weapon's name
    @Override
    public int getId() {
        return itemId;
    }

    // Implemented method from the Item interface to get the weapon's name
    @Override
    public String getName() {
        return name;
    }

    // Implemented method from the Item interface to get the weapon's cost
    @Override
    public int getCost() {
        return cost;
    }

    // Implemented method from the Item interface to get the weapon's description
    @Override
    public String getDescription() {
        return description;
    }

    // Method to get the weapon's attack power
    public int getATK() {
        return ATK;
    }
}
