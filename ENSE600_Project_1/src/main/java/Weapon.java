
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public class Weapon implements Serializable, Item{
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String description;
    private final int cost;
    private final int ATK;

    public Weapon(String name, int ATK, String description) {
        this.name = name;
        this.ATK = ATK;
        this.cost = ATK * 10;
        this.description = description;
    }

    @Override public String getName() { return name; }
    @Override public int getCost() { return cost; }
    @Override public String getDescription() { return description; }
    public int getATK() { return ATK; }
}


