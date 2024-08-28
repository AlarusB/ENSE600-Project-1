
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

    public Game(Player player) {
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        while (true) {
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
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void fightEnemy() {
        Enemy enemy = new Enemy("Goblin", 5, 500, 10);  // enemyName, enemyLevel, baseHP, baseATK
        Battle battle = new Battle(player, enemy);
        battle.attackEnemy();
        battle.attackPlayer();
        // more battle handling...
    }

    private void usePotion() {
        Potion attackPotion = new Potion("Attack Potion", Potion.PotionType.BUFF, 50);
        player.applyBonusATK(attackPotion.getPotionEffectValue());
        System.out.println("Used " + attackPotion.getPotionName() + "! Attack increased by " + attackPotion.getPotionEffectValue() + ".");
    }

    // more game logic...
}
