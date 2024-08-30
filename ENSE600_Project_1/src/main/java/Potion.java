import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
public abstract class Potion implements Consumable {
    private static final long serialVersionUID = 1L;
    private final String name;

    public Potion(String name) {
        this.name = name;
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
    @Override public String getName() { return name; }
}
