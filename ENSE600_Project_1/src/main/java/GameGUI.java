
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

    private JLabel playerHPLabel;
    private JLabel enemyHPLabel;

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

        // Create health status panel
        JPanel healthStatusPanel = new JPanel();
        healthStatusPanel.setLayout(new GridLayout(1, 2));

        // Player and Enemy HP labels
        playerHPLabel = new JLabel("Player HP: " + player.getHP());
        enemyHPLabel = new JLabel("Enemy HP: ");

        healthStatusPanel.add(playerHPLabel);
        healthStatusPanel.add(enemyHPLabel);

        // Create the game output area
        gameOutput = new JTextArea();
        gameOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameOutput);

        // Auto-scroll to the bottom when new text is added
        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());

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
        add(healthStatusPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void startGame() {
        inBattle = false; // Reset battle
        enemy = RandomEnemy();
        gameOutput.append("A wild " + enemy.getName() + " appears!\n");
        updateStatus();
    }

    private void updateStatus() {
        // Update HP labels
        playerHPLabel.setText("Player HP: " + player.getHP());
        enemyHPLabel.setText("Enemy HP: " + enemy.getHP());

        gameOutput.append("Player Level: " + player.getLevel() + " Player HP: " + player.getHP() + "\n");
        gameOutput.append(enemy.getName() + " Level: " + enemy.getLevel() + " HP: " + enemy.getHP() + "\n\n");

        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
    }

    // Save the game to a file
    private void saveGame() {
        String fileName = "player_data.dat";
        player.saveToFile(fileName);
        gameOutput.append("Game saved.\n\n");
        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
    }

    // Load the game from a file
    private void loadGame() {
        String fileName = "player_data.dat";
        Player loadedPlayer = Player.loadFromFile(fileName);
        if (loadedPlayer != null) {
            player = loadedPlayer;
            gameOutput.append("Game loaded successfully.\n\n");
        } else {
            gameOutput.append("Failed to load game.\n\n");
        }
        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
        updateStatus();
    }

    // Handle the battle with the enemy
    private void fightEnemy() {
        Battle battle = new Battle(player, enemy);

        if (enemy.isAlive() && player.isAlive()) {
            battle.attackEnemy();
            gameOutput.append("You attacked the enemy!\n");
            if (enemy.isAlive()) {
                battle.attackPlayer();
                gameOutput.append("The enemy attacked you!\n\n");
            }
        }

        if (!enemy.isAlive() && player.isAlive()) {
            gameOutput.append("You defeated " + enemy.getName() + "!\n");
            double xp = enemy.dropXP();
            double gold = enemy.dropGold();
            player.addXP((int) xp);
            player.addGold((int) gold);
            gameOutput.append("You gained " + xp + " XP and " + gold + " gold.\n\n");
            inBattle = false;
            if (ShopGUI.encounterShop()) {
                ShopGUI.showShop(player); // Open the Shop GUI directly
            }

            enemy = RandomEnemy(); // Spawn a new enemy for the next battle
        } else if (!player.isAlive()) {
            gameOutput.append("You died. :( \n");
            System.exit(0); // Exit game
        }

        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
        updateStatus();
    }

    // Handle the use of potions
    private void usePotion() {
        // Open the UsePotionGUI to select and use a potion
        UsePotionGUI.showPotionGUI(player, enemy);
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
