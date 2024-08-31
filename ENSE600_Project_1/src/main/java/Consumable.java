/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public interface Consumable {
    String getName();
    String getDescription();
    int getCost();
    public boolean use(Entity user, Entity target);
}
