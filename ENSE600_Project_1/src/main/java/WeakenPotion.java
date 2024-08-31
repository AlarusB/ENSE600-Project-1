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
    
    public WeakenPotion(String name, int defenseReduction) {
        super(name, "Reduces enemy's defense by " + defenseReduction + " points.", defenseReduction*5);
        this.defenseReduction = defenseReduction;
    }
    
    @Override
    public boolean use(Entity user, Entity target) {
        if (!(target instanceof Enemy)) {
            System.out.println("Cannot use attack potion on non-player target.");
            return false;
        }

        Enemy enemy = (Enemy) target;
        enemy.applyDefenseReduction(defenseReduction);
        System.out.println("Used " + getName() + "! Enemy weakened by " +
                defenseReduction + ".");
        
        return true;
    }
}
