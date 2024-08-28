
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
    private Enemy enemy;
    private Scanner scanner;

    public Game(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        while (true) {
            System.out.println(enemy.getEnemyHP());
            System.out.println(player.getPlayerHP());

            System.out.println("1. Fight enemy");
            System.out.println("2. Use potion");
            System.out.println("3. Save game");
            System.out.println("4. Load game");
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
        Battle battle = new Battle(player, enemy);
        battle.attackEnemy();
        battle.attackPlayer();
        // more battle handling...
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
