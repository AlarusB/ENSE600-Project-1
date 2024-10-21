
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class GameGUI extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;

    private Player player;
    private Enemy enemy;
    private boolean inBattle;
    private JLabel statusLabel;
    private JTextArea gameOutput;
    private JButton fightButton;
    private JButton potionButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;

    public GameGUI(Player player) {
        this.player = player;
        this.inBattle = false;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the game output area (replaces console output)
        gameOutput = new JTextArea();
        gameOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameOutput);

        // Create status label
        statusLabel = new JLabel("Welcome to the game!");

        // Create buttons
        fightButton = new JButton("Fight Enemy");
        potionButton = new JButton("Use Potion");
        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");
        exitButton = new JButton("Exit Game");

        // Add action listeners to buttons
        fightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fightEnemy();
            }
        });

        potionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usePotion();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the game
            }
        });

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(fightButton);
        buttonPanel.add(potionButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void startGame() {
        inBattle = false; // Reset battle status
        enemy = RandomEnemy(); // Create an enemy instance
        gameOutput.append("A wild " + enemy.getName() + " appears!\n");
        updateStatus();
    }

    private void updateStatus() {
        gameOutput.append("Player Level: " + player.getLevel() + " Player HP: " + player.getHP() + "\n");
        gameOutput.append(enemy.getName() + " Level: " + enemy.getLevel() + " HP: " + enemy.getHP() + "\n");
    }

    // Save the game to a file
    private void saveGame() {
        String fileName = "player_data.dat";
        player.saveToFile(fileName);
        gameOutput.append("Game saved.\n");
    }

    // Load the game from a file
    private void loadGame() {
        String fileName = "player_data.dat";
        Player loadedPlayer = Player.loadFromFile(fileName);
        if (loadedPlayer != null) {
            player = loadedPlayer;
            gameOutput.append("Game loaded successfully.\n");
        } else {
            gameOutput.append("Failed to load game.\n");
        }
    }

    // Handle the battle with the enemy
    private void fightEnemy() {
        Battle battle = new Battle(player, enemy);

        if (enemy.isAlive() && player.isAlive()) {
            battle.attackEnemy();
            gameOutput.append("You attacked the enemy!\n");
            if (enemy.isAlive()) {
                battle.attackPlayer();
                gameOutput.append("The enemy attacked you!\n");
            }
        }

        if (!enemy.isAlive() && player.isAlive()) {
            gameOutput.append("You defeated " + enemy.getName() + "!\n");
            double xp = enemy.dropXP();
            double gold = enemy.dropGold();
            player.addXP((int) xp);
            player.addGold((int) gold);
            gameOutput.append("You gained " + xp + " XP and " + gold + " gold.\n");
            inBattle = false;
            enemy = RandomEnemy(); // Spawn a new enemy for the next battle
        } else if (!player.isAlive()) {
            gameOutput.append("You died.\n");
            System.exit(0); // Exit game
        }
        updateStatus();
    }

    // Handle the use of potions
    private void usePotion() {
        // List available potions (you can replace this with a more advanced GUI for potion selection)
        player.listPotionBag();
        // For simplicity, we'll assume potion at index 0 is used
        Potion potion = player.getPotion(0);
        if (potion != null) {
            if (potion instanceof AttackPotion || potion instanceof HealingPotion) {
                potion.use(player, player);
                gameOutput.append("You used a potion on yourself.\n");
            } else if (potion instanceof WeakenPotion) {
                potion.use(player, enemy);
                gameOutput.append("You used a potion on the enemy.\n");
            }
            player.removePotion(potion); // Remove the used potion from the bag
        }

        // Enemy attacks the player after potion use
        if (enemy.getHP() > 0) {
            Battle battle = new Battle(player, enemy);
            battle.attackPlayer();
            gameOutput.append("The enemy attacked you after you used a potion!\n");
        }
        updateStatus();
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

}
