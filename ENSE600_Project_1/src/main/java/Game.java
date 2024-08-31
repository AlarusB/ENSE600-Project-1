
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

    Enemy enemy = RandomEnemy(); // Create an enemy instance

    // Start the main game loop
    public void startGame() {
        while (true) {
            while (!inBattle) { // Loop until the player decides to enter a battle
                System.out.println("Enter battle? - yes(y)/no(n)");
                String input = scanner.nextLine().trim(); 
                if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                    enemy = RandomEnemy();
                    System.out.println("A wild " + enemy.getName() + " appears!");
                    inBattle = true;
                } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                    System.exit(0); // Exit the game if the player chooses not to battle
                } else {
                    System.out.println("Invalid choice!");
                }
            }

            // Display player's and enemy's current status
            System.out.println("Player Level: " + player.getLevel() + " Player HP: " + player.getHP());
            System.out.println(enemy.getName() + " Level: " + enemy.getLevel() + " HP: " + enemy.getHP() + "\n");

            // Display action menu
            System.out.println("1. Fight enemy");
            System.out.println("2. Use potion");
            System.out.println("3. Save game");
            System.out.println("4. Load game");
            System.out.println("5. Exit");

            int choice = chooseAction(1,5);

            switch (choice) {
                case 1:
                    fightEnemy(); // Engage in battle with the enemy
                    break;
                case 2:
                    usePotion(); // Use a potion
                    break;
                case 3:
                    saveGame(); // Save the game
                    break;
                case 4:
                    loadGame(); // Load a saved game
                    break;
                case 5:
                    System.exit(0); // Exit the game
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private int chooseAction(int min, int max) {
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.print("Choose an action: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.println("Invalid input. Enter an action between range: (" + min + "-" + max + ")");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid action.");
            }
        } while (!isValid);
        return choice;
    }

    // Save the game to a file
    private void saveGame() {
        String fileName = "player_data.dat";
        player.saveToFile(fileName);
    }

    // Load the game from a file
    private void loadGame() {
        String fileName = "player_data.dat";
        Player loadedPlayer = Player.loadFromFile(fileName);
        if (loadedPlayer != null) {
            player = loadedPlayer;
            System.out.println("Game loaded successfully.");
        }
    }

    // Handle the battle with the enemy
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

            // Check if the enemy is defeated
            if (!enemy.isAlive() && player.isAlive()) {
                System.out.println("You defeated " + enemy.getName() + "!");
                double xp = enemy.dropXP();
                double gold = enemy.dropGold();
                player.addXP((int) xp);
                player.addGold((int) gold);
                System.out.println("You gained " + xp + " XP and " + gold + " gold.");
                if (Shop.encounterShop()) {
                    Shop shop = new Shop(scanner);
                    shop.displayItems(player);
                }
                inBattle = false;
                enemy = RandomEnemy();
                break;
            } else if (!player.isAlive()) {
                System.out.println("You died.");
                System.exit(0);
            }
        }
    }

    // Spawn a random enemy to fight
    private Enemy RandomEnemy() {
        int enemyType = (int) (Math.random() * 3); // Randomly select an enemy type
        int randomLevel = 1 + (int) (Math.random() * 50); // Randomly select an enemy level
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

    // Handle the use of potions
    private void usePotion() {
        player.listPotionBag(); // List available potions
        int choice = chooseAction(1, player.getBagSize());
        Potion potion = player.getPotion(choice);
        if (potion != null) {
            if (potion instanceof AttackPotion || potion instanceof HealingPotion) {
                // Use the potion on the player
                potion.use(player, player);
            } else if (potion instanceof WeakenPotion) {
                // Use the potion on the enemy
                potion.use(player, enemy);
            }
            player.removePotion(potion); // Remove the used potion from the bag
        }

        // Enemy attacks the player after potion use
        if (choice != player.getBagSize() + 1) {
            Battle battle = new Battle(player, enemy);
            if (enemy.getHP() > 0) {
                battle.attackPlayer();
            }
        }
    }
}
