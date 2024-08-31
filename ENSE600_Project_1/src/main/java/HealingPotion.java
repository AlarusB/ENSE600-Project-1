/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class HealingPotion extends Potion {
    private final double healAmount;
    
    public HealingPotion(String name, int healAmount) {
        super(name, "Heals the user's HP by " + healAmount + " points.", healAmount/2);
        this.healAmount = healAmount;
    }
    
    @Override
    public boolean use(Entity user, Entity target) {
        
        boolean success = target.heal(healAmount);
        if (success) {
            System.out.println("Used " + getName() + " and was healed by " + healAmount + "!");
        }

        return success;
    }
}
