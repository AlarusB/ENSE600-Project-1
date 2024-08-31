
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

    // Constructor for initializing the weapon's attributes
    public Weapon(String name, int ATK, String description) {
        this.name = name;
        this.ATK = ATK; 
        this.cost = ATK * 10;
        this.description = description; 
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
