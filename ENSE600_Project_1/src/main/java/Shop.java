/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexs
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Shop {
    private List<Item> itemsForSale;
    private Scanner scanner;

    public Shop(Scanner scanner) {
        itemsForSale = new ArrayList<>();
        generateItemsForSale();
        scanner = scanner;
    }

    private void generateItemsForSale() {
        itemsForSale.add(new Weapon("Wooden Club", 30, "A tough Wooden Club."));
        itemsForSale.add(new Weapon("Steel Sword", 60, "A sturdy Steel Sword."));
        itemsForSale.add(new Weapon("Laser Sword", 120, "A pristine Laser Sword, can cut anything in half."));
        itemsForSale.add(new WeakenPotion("Prime Energy", 10));
        itemsForSale.add(new AttackPotion("Mountain Dew", 50));
        itemsForSale.add(new HealingPotion("Water", 100));
    }
    
    public void buyItem(Player player, Item item) {
        player.setGold(player.getGold() - item.getCost());
        System.out.println("You bought " + item.getName() + "!");
    }
    // Display items in the shop.
    public void displayItems(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nWhoa its a shop! You have " + player.getGold() + " gold.");
        System.out.println("Items for Sale:");
        
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                System.out.println((i + 1) + ". " + weapon.getName() +
                        " - Description: " + weapon.getDescription() +
                        " ATK: " + weapon.getATK() + " (Cost: " + weapon.getCost() + " gold)");
            } else {
                System.out.println((i + 1) + ". " + item.getName() +
                        " - Description: " + item.getDescription() +
                        " (Cost: " + item.getCost() + " gold)");
            }
        }

        System.out.println("Enter the number of the item you want to buy, or 0 to exit:");
        int choice = InputHandler.chooseAction(0, itemsForSale.size());
        if (choice > 0 && choice <= itemsForSale.size()) {
            Item selectedItem = itemsForSale.get(choice - 1);
            if (player.getGold() >= selectedItem.getCost()) {
                if (selectedItem instanceof Weapon) {
                    player.setWeapon((Weapon) selectedItem);
                } else if (selectedItem instanceof Potion) {
                    player.addToPotionBag((Potion) selectedItem);
                } else {
                    System.out.println("Invalid item!");
                    return;
                }
                buyItem(player, selectedItem);
            } else {
                System.out.println("Not enough gold!");
            } 
        } else if (choice != 0) {
            System.out.println("Invalid choice!");
        }
    }

    public static boolean encounterShop() {
        Random random = new Random();
        return random.nextInt(100) < 30; // 30% chance to encounter a shop
    }
}

