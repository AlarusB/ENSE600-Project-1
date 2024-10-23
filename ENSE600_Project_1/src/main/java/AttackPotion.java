/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class AttackPotion extends Potion {
    private final int attackBoost;
    
    public AttackPotion(int itemId, String name, int baseCost, String description, int attackBoost, double costFactor) {
        super(itemId, name, description+ " Increases user's attack by " + attackBoost + " points.", (int) (attackBoost*costFactor));
        this.attackBoost = attackBoost;
    }
    
    @Override
    public boolean use(Entity user, Entity target) {
        if (!user.equals(target)) {
            System.out.println("Attack potion must be used on oneself!");
            return false;
        }
        
        if (!(target instanceof Player)) {
            System.out.println("Cannot use attack potion on non-player target.");
            return false;
        }
        
        Player player = (Player) target;
        player.applyBonusATK(attackBoost);
        System.out.println("Used " + getName() + "! Attack increased by "
                + attackBoost + ".");

        return true;
    }
}
