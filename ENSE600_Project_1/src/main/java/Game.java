
import java.io.Serializable;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    private Player player;
    private Scanner scanner;
    private boolean inBattle;

    public Game(Player player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    Enemy enemy = RandomEnemy();
    // Start the game loop
    public void startGame() {
        while (true) {
            while (inBattle == false) {
                System.out.println("enter battle? - yes(y)/no(n)");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                    enemy = RandomEnemy();
                    System.out.println("A wild " + enemy.getName() + " appears!");
                    inBattle = true;
                } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice!");
                }
            }

            System.out.println("playerLevel: " + player.getLevel() + " playerHP: " + player.getHP());
            System.out.println(enemy.getName() + "Level: " + enemy.getLevel() + " " + enemy.getName() + " HP: " + enemy.getHP() + "\n");

            System.out.println("1. Fight enemy");
            System.out.println("2. Use potion");
            System.out.println("3. Save game");
            System.out.println("4. Load game");
            System.out.println("5. exit");
            System.out.print("Choose an action: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    fightEnemy();
                    break;
                case 2:
                    usePotion();
                    break;
                case 3:
                    saveGame();
                    break;
                case 4:
                    loadGame();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // Save the game to file
    private void saveGame() {
        String fileName = "player_data.dat";
        player.saveToFile(fileName);
    }
    // Load the game from file
    private void loadGame() {
        String fileName = "player_data.dat";
        Player loadedPlayer = Player.loadFromFile(fileName);
        if (loadedPlayer != null) {
            player = loadedPlayer;
            System.out.println("Game loaded successfully.");
        }
    }
    
    // Fight enemy
    private void fightEnemy() {
        boolean continueFighting = true;

        while (continueFighting && player.isAlive()) {
            Battle battle = new Battle(player, enemy);

            if (enemy.isAlive() && player.isAlive()) {
                battle.attackEnemy();
                if (enemy.isAlive()) {
                    battle.attackPlayer();
                    break;
                }
            }

            if (!enemy.isAlive() && player.isAlive()) {
                System.out.println("You defeated " + enemy.getName() + "!");
                double xp = enemy.dropXP();
                double gold = enemy.dropGold();
                player.addXP((int) xp);
                player.addGold((int) gold);
                System.out.println("You gained " + xp + " XP and " + gold + " gold.");
                if (Shop.encounterShop()) {
                    Shop shop = new Shop();
                    shop.displayItems(player);

                }
                inBattle = false;
                enemy = RandomEnemy();
                break;
            } else if (!player.isAlive()) {
                System.out.println("damn bro.");
                System.exit(0);
            } else {

            }
        }
    }
    
    // Spawn in a random enemy to fight
    private Enemy RandomEnemy() {
        int enemyType = (int) (Math.random() * 3);
        int randomLevel = 1 + (int) (Math.random() * 50);
        switch (enemyType) {
            case 0:
                return new Enemy("Goblin", randomLevel, 50, 10);
            case 1:
                return new Enemy("Orc", randomLevel, 80, 15);
            case 2:
                return new Enemy("Dragon", randomLevel, 120, 25);
            default:
                return new Enemy("Goblin", randomLevel, 50, 10);

        }
    }
    
    // Player selects a potion then uses it
    private void usePotion() {
        player.listPotionBag();
        int choice = scanner.nextInt();
        scanner.nextLine();
        Potion potion = player.getPotion(choice);
        if (potion != null)
        {
            if (potion instanceof AttackPotion || potion instanceof HealingPotion) {
                // Use on self
                potion.use(player, player);
            } else if (potion instanceof WeakenPotion) {
                // Use on enemy
                potion.use(player, enemy);
            }
            player.removePotion(potion);
        }
        // Damage player after using potion
        if (choice != player.getBagSize()) {
            Battle battle = new Battle(player, enemy);
            if (enemy.getHP() > 0) {
                battle.attackPlayer();
            }
        }
    }
}
