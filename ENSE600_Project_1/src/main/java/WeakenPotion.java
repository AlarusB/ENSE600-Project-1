/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class WeakenPotion extends Potion {
    private final int defenseReduction;
    
    public WeakenPotion(int itemId, String name, int baseCost, String description, int defenseReduction, double costFactor) {
        super(itemId, name, description+" Reduces enemy's defense by " + defenseReduction + " points.", baseCost + (int) (defenseReduction*costFactor));
        this.defenseReduction = defenseReduction;
    }
    
    @Override // Use on enemy
    public Entity getTarget(Entity user, Entity enemy) {
        return enemy;
    }
    
    @Override // Reduces an enemies defense
    public boolean use(Entity user, Entity target) {
        if (!(target instanceof Enemy)) {
            System.out.println("Cannot use weaken potion on non-player target.");
            return false;
        }

        Enemy enemy = (Enemy) target;
        enemy.applyDefenseReduction(defenseReduction);
        System.out.println("Used " + getName() + "! Enemy weakened by " +
                defenseReduction + ".");
        
        return true;
    }
}
