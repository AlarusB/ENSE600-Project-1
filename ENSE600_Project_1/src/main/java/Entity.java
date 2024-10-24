
import java.io.Serializable;
import java.util.Objects;

/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */
/**
 *
 * @author abdul
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private double level;

    private double baseHP;
    private double maxHP;
    private double HP;

    private int baseATK;
    private double ATK;

    public Entity(String name, double level, int baseHP, int baseATK) {
        this.name = name;
        this.level = level;
        this.baseHP = baseHP;
        this.baseATK = baseATK;
        updateStats();
        this.HP = this.maxHP;
    }

    // Damages Entity
    public void takeDamage(double damage) {
        if (!isAlive()) {
            System.out.println(getName() + " is already dead...");
            return;
        }

        this.setHP(this.HP - damage);
    }

    // Heals Entity
    public boolean heal(double amount) {
        if (!isAlive()) {
            System.out.println(getName() + " cannot be healed because they are dead.");
            return false;
        }

        this.setHP(this.getHP() + amount);
        return true;
    }

    // Generated with ChatGPT
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check for reference equality
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false; // Check for null and type compatibility
        }

        Entity other = (Entity) obj; // Cast to Entity

        // Check for attribute equality
        return name.equals(other.name)
                && level == other.level
                && baseHP == other.baseHP
                && baseATK == other.baseATK
                && HP == other.HP
                && maxHP == other.maxHP
                && ATK == other.ATK;
    }

    // Generated with ChatGPT
    @Override
    public int hashCode() {
        return Objects.hash(name, level, baseHP, baseATK, HP, maxHP, ATK);
    }

    protected void updateStats() {
        this.setMaxHP(getBaseHP() * (1 + (getLevel() / 10.0)));
        this.setATK(getBaseATK() * (1 + (getLevel() / 40)));
    }

    public boolean isAlive() {
        return this.HP > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
        updateStats();
    }

    public double getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(double baseHealth) {
        this.baseHP = baseHealth;
        updateStats();
    }

    public double getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP = maxHP;
    }

    public double getHP() {
        return this.HP;
    }

    public void setHP(double HP) {
        this.HP = Math.max(Math.min(HP, maxHP), 0);
    }

    public int getBaseATK() {
        return baseATK;
    }

    public void setBaseATK(int baseATK) {
        this.baseATK = baseATK;
        updateStats();
    }

    public double getATK() {
        return ATK;
    }

    public void setATK(double ATK) {
        this.ATK = ATK;
    }
}
