
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public abstract class Potion implements Consumable, Item {
    private final String name;
    private final String description;
    private final int cost;

    public Potion(String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    
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
    @Override public int getCost() { return cost; }
    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
}
