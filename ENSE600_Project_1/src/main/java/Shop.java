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
    private List<Weapon> weaponsForSale;
    private List<Potion> potionsForSale;

    public Shop() {
        weaponsForSale = new ArrayList<>();
        potionsForSale = new ArrayList<>();
        generateItemsForSale();
    }

    private void generateItemsForSale() {
        weaponsForSale.add(new Weapon("Wooden Club", 30));
        weaponsForSale.add(new Weapon("Steel Sword", 60));
        weaponsForSale.add(new Weapon("Laser Sword", 120));
        potionsForSale.add(new WeakenPotion("Prime Energy", 10));
        potionsForSale.add(new AttackPotion("Mountain Dew", 50));
    }

    public void displayItems(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nWhoa its a shop! You have " + player.getGold() + " gold.");
        System.out.println("Weapons for Sale:");
        for (int i = 0; i < weaponsForSale.size(); i++) {
            Weapon weapon = weaponsForSale.get(i);
            System.out.println((i + 1) + ". " + weapon.getWeaponName() + " - " + weapon.getWeaponATK() + " ATK (Cost: " + (weapon.getWeaponATK() * 10) + " gold)");
        }
        
        for (int i = 0; i < potionsForSale.size(); i++) {
            Potion potion = potionsForSale.get(i);
            System.out.println((weaponsForSale.size() + i + 1) + ". " + potion.getName() + " - Description:" + potion.getDescription() + " (Cost: " + potion.getCost() + " gold)");
        }
        

        System.out.println("Enter the number of the item you want to buy, or 0 to exit:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= weaponsForSale.size()) {
            Weapon selectedWeapon = weaponsForSale.get(choice - 1);
            if (player.getGold() >= selectedWeapon.getWeaponATK() * 10) {
                player.setWeapon(selectedWeapon);
                player.setGold(player.getGold() - selectedWeapon.getWeaponATK() * 10);
                System.out.println("You bought " + selectedWeapon.getWeaponName() + "!");
            } else {
                System.out.println("Not enough gold!");
            } 
        } else if (choice > weaponsForSale.size() && choice <= (weaponsForSale.size() + potionsForSale.size())) {
            Potion selectedPotion = potionsForSale.get(choice - weaponsForSale.size() - 1);
            if (player.getGold() >= selectedPotion.getCost()) {
                if (selectedPotion instanceof WeakenPotion) {
                    player.setWeakenPotion(selectedPotion);
                } else if (selectedPotion instanceof AttackPotion) {
                    player.setAttackPotion(selectedPotion);
                } else {
                    System.out.println("Invalid potion!");
                    return;
                }
                player.setGold(player.getGold() - selectedPotion.getCost());
                System.out.println("You bought " + selectedPotion.getName() + "!");
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

