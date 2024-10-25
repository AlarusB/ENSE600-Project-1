
import java.io.Serializable;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */

public abstract class Potion implements Consumable, Item, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String description;
    private final int cost;
    private final int itemId;

    public Potion(int itemId, String name, String description, int cost) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    
    public abstract Entity getTarget(Entity user, Entity enemy);
    
    @Override
    public boolean use(Entity user, Entity target)
    {
        if (user.equals(target)) {
            System.out.println(user.getName() + "used " + getName() + "!");
        } else {
            System.out.println(user.getName() + "used " + getName() + " on " + target.getName());
        }
        return true;
    }
    @Override public int getId() { return itemId; }
    @Override public int getCost() { return cost; }
    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
}
