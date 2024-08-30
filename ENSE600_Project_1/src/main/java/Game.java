
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
public class Game implements Serializable{
    private static final long serialVersionUID = 1L;

    private Player player;
    private Scanner scanner;
    private boolean inBattle;

    public Game(Player player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    Enemy enemy = RandomEnemy();

    public void startGame() {
        while (true) {
            while (inBattle == false) {
                System.out.println("enter battle? - yes(y)/no(n)");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                    enemy = RandomEnemy();
                    player.setAttackPotion(new AttackPotion("Attack Potion", 50));
                    player.setWeakenPotion(new WeakenPotion("weaken or somthing idk", 10));
                    System.out.println("A wild " + enemy.getName()+ " appears!");
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

    private void saveGame() {
        String fileName = "player_data.dat";
        player.saveToFile(fileName);
    }

    private void loadGame() {
        String fileName = "player_data.dat";
        Player loadedPlayer = Player.loadFromFile(fileName);
        if (loadedPlayer != null) {
            player = loadedPlayer;
            System.out.println("Game loaded successfully.");
        }
    }

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

    private Enemy RandomEnemy() {
        int enemyType = (int) (Math.random() * 3);
        int randomLevel = 1 + (int) (Math.random() * 50);
        switch (enemyType) {
            case 0:
                return new Enemy("Goblin", randomLevel, 50, 10);// enemyName, enemyLevel, baseHP, baseATK
            case 1:
                return new Enemy("Orc", randomLevel, 80, 15);
            case 2:
                return new Enemy("Dragon", randomLevel, 120, 25);
            default:
                return new Enemy("Goblin", randomLevel, 50, 10);

        }
    }

    private void usePotion() {
        System.out.println("1. buff atk");
        System.out.println("2. weaken enemy");
        System.out.println("3. back");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                // Only use potion if player has one
                if (!player.hasAttackPotion()) {
                    System.out.println("Already used attack potion!");
                    break;
                }
                player.getAttackPotion().use(player, player);
                player.setAttackPotion(null);
                break;
            case 2:
                // Only use potion if player has one
                if (!player.hasWeakenPotion()) {
                    System.out.println("Already used weaken potion!");
                    break;
                }
                player.getWeakenPotion().use(player, enemy);
                player.setWeakenPotion(null);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid choice!");
        }
        // Damage player after using potion
        if (choice != 3) {
            Battle battle = new Battle(player, enemy);
            if (enemy.getHP() > 0) {
                battle.attackPlayer();
            }
        }

        // more game logic...
    }
}
