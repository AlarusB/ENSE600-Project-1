
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Game {

    private Player player;
    private Scanner scanner;
    private Enemy[] enemies;

    public Game() {
        // weapon creation
        Weapon sword = new Weapon("Sword", 10, 0.2); //name, attack power, attackBonus.
        Weapon axe = new Weapon("Axe", 15, 0.1);
        Weapon bow = new Weapon("Bow", 8, 0.3);

        // player creation
        player = new Player("Hero", 10, 100, 20, sword, 0.2); //name, level, health, attackCharacter, Weapon, defenseIgnore
        scanner = new Scanner(System.in);

        Potion attackPotion = new Potion("Attack Potion", 50, Potion.PotionType.ATTACK_BONUS);
        Potion highAttackPotion = new Potion("High Attack Potion", 150, Potion.PotionType.ATTACK_BONUS);
        Potion vulnerablePotion = new Potion("Vulnerable Potion", 0.05, Potion.PotionType.DEFENSE_REDUCTION);
        Potion highVulnerablePotion = new Potion("High Vulnerable Potion", 0.15, Potion.PotionType.DEFENSE_REDUCTION);
        
        Enemy Slime = new Slime(5);

        
                start(attackPotion, highAttackPotion, vulnerablePotion, highVulnerablePotion);
    }

    private void start(Potion attackPotion, Potion highAttackPotion, Potion vulnerablePotion, Potion highVulnerablePotion) {
        while (true) {
            System.out.println("1. Fight");
            System.out.println("2. Change Weapon");
            System.out.println("3. Use Potion");
            System.out.print("Choose an action: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    fightMenu();
                    break;
                case 2:
                    changeWeaponMenu();
                    break;
                case 3:
                    usePotionMenu(attackPotion, highAttackPotion, vulnerablePotion, highVulnerablePotion);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void fightMenu() {
        System.out.println("Choose an enemy to fight:");
        for (int i = 0; i < enemies.length; i++) {
            System.out.println((i + 1) + ". " + enemies[i].getName() + " (Level " + enemies[i].getLevel() + ")");
        }
        System.out.print("Choose: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > enemies.length) {
            System.out.println("Invalid choice!");
        } else {
            Enemy enemy = enemies[choice - 1];
            fight(enemy);
        }
    }

    private void fight(Enemy enemy) {
        System.out.println("Fighting " + enemy.getName() + "!");
        while (!enemy.isDefeated() && !player.isDefeated()) {
            enemy.attack(player);
            if (!player.isDefeated()) {
                player.attack(enemy);
            }
        }

        if (player.isDefeated()) {
            System.out.println("You were defeated by " + enemy.getName() + "...");
        } else {
            System.out.println("You defeated " + enemy.getName() + "!");
        }

    }

    private void usePotionMenu(Potion attackPotion, Potion highAttackPotion, Potion vulnerablePotion, Potion highVulnerablePotion) {
        System.out.println("Choose a potion:");
        System.out.println("1. " + attackPotion.getName());
        System.out.println("2. " + highAttackPotion.getName());
        System.out.println("3. " + vulnerablePotion.getName());
        System.out.println("4. " + highVulnerablePotion.getName());
        System.out.print("Choose: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                player.usePotion(attackPotion);
                break;
            case 2:
                player.usePotion(highAttackPotion);
                break;
            case 3:
                player.usePotion(vulnerablePotion);
                break;
            case 4:
                player.usePotion(highVulnerablePotion);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private void changeWeaponMenu() {
        System.out.println("Choose a weapon:");
        System.out.println("1. Sword");
        System.out.println("2. Axe");
        System.out.println("3. Bow");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                player.changeWeapon(new Weapon("Sword", 10, 0.2));
                break;
            case 2:
                player.changeWeapon(new Weapon("Axe", 15, 0.1));
                break;
            case 3:
                player.changeWeapon(new Weapon("Bow", 8, 0.3));
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }
}
