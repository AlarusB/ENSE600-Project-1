
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
    private boolean inBattle;

    public Game(Player player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    Enemy enemy = RandomEnemy();

    public void startGame() {
        while (true) {
            while (inBattle == false) {
                System.out.println("ready to start? (yes/no)");
                if (scanner.next().equalsIgnoreCase("yes")) {
                    enemy = RandomEnemy();
                    System.out.println("A wild " + enemy.getEnemyName() + " appears!");
                    inBattle = true;
                } else {
                    System.exit(0);
                }
            }
                
            System.out.println("playerHP: " + player.getCurrentPlayerHP());
            System.out.println(enemy.getEnemyName() + " HP: " + enemy.getEnemyHP() + "\n");
            
            System.out.println("1. Fight enemy");
            System.out.println("2. Use potion");
            System.out.println("3. Save game");
            System.out.println("4. Load game");
            System.out.println("5. exit");
            System.out.print("Choose an action: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    fightEnemy();
                    break;
                case 2:
                    usePotion();
                    break;
                case 3:
                    player.saveToFile("player_data.dat");
                    break;
                case 4:
                    Player loadedPlayer = Player.loadFromFile("player_data.dat");
                    if (loadedPlayer != null) {
                        player = loadedPlayer;
                    }
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void fightEnemy() {
        boolean continueFighting = true;

        while (continueFighting && player.getCurrentPlayerHP() > 0) {
            Battle battle = new Battle(player, enemy);

            if (enemy.getEnemyHP() > 0 && player.getCurrentPlayerHP() > 0) {
                battle.attackEnemy();
                if (enemy.getEnemyHP() > 0) {
                    battle.attackPlayer();
                    break;
                }
            }

            if (enemy.getEnemyHP() <= 0 && player.getCurrentPlayerHP() != 0) {
                System.out.println("You defeated " + enemy.getEnemyName() + "!");
                System.out.println("Do you want to fight another enemy? (yes/no)");
                if (scanner.next().equalsIgnoreCase("yes")) {
                    inBattle = false;
                    break;
                } else {
                    System.exit(0);
                }

            } else if (player.getCurrentPlayerHP() <= 0) {
                System.out.println("damn bro.");
                System.exit(0);
            } else {

            }
        }
    }

    private Enemy RandomEnemy() {
        int enemyType = (int) (Math.random() * 3);
        switch (enemyType) {
            case 0:
                return new Enemy("Goblin", 5, 50, 10);// enemyName, enemyLevel, baseHP, baseATK
            case 1:
                return new Enemy("Orc", 8, 80, 15);
            case 2:
                return new Enemy("Dragon", 12, 120, 25);
            default:
                return new Enemy("Goblin", 5, 50, 10);

        }
    }

    private void usePotion() {
        System.out.println("1. buff atk");
        System.out.println("2. weaken enemy");
        System.out.println("3. back");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Potion attackPotion = new Potion("Attack Potion", Potion.PotionType.BUFF, 50);
                player.applyBonusATK(attackPotion.getPotionEffectValue());
                System.out.println("Used " + attackPotion.getPotionName() + "! Attack increased by " + attackPotion.getPotionEffectValue() + ".");
                break;
            case 2:
                Potion weakenPotion = new Potion("weaken or somthing idk", Potion.PotionType.DEBUFF, 10);
                enemy.applyDefenseReduction(weakenPotion.getPotionEffectValue());
                System.out.println("Used " + weakenPotion.getPotionName() + "! Enemy weakened by " + weakenPotion.getPotionEffectValue() + ".");
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid choice!");

        }

        // more game logic...
    }
}
