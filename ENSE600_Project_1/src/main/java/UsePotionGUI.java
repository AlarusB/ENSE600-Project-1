
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */

public class UsePotionGUI extends JFrame implements Serializable{

    private Player player;
    private Enemy enemy;
    private JComboBox<String> potionSelection;
    private JButton usePotionButton;
    private JButton cancelButton;
    private List<Potion> potionBag;

    // Constructor to initialize the potion GUI
    public UsePotionGUI(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.potionBag = Arrays.asList(player.getPotionBag()); // Convert array to list
        initializeUI();
    }

    // Initialize the user interface components
    private void initializeUI() {
        setTitle("Use Potion");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel to display potion selection
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Label and drop-down for potion selection
        JLabel potionLabel = new JLabel("Select a potion to use:");
        potionSelection = new JComboBox<>();
        for (int i = 0; i < potionBag.size(); i++) {
            Potion potion = potionBag.get(i);
            potionSelection.addItem((i + 1) + ". " + potion.getName());
        }

        mainPanel.add(potionLabel, BorderLayout.NORTH);
        mainPanel.add(potionSelection, BorderLayout.CENTER);

        // Panel for buttons (Use and Cancel)
        JPanel buttonPanel = new JPanel();
        usePotionButton = new JButton("Use Potion");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(usePotionButton);
        buttonPanel.add(cancelButton);

        usePotionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                useSelectedPotion();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add components to the frame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Use the selected potion from the dropdown
    private void useSelectedPotion() {
        int selectedIndex = potionSelection.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < potionBag.size()) {
            Potion selectedPotion = potionBag.get(selectedIndex);

            if (selectedPotion instanceof AttackPotion || selectedPotion instanceof HealingPotion) {
                selectedPotion.use(player, player); // Use the potion on the player
            } else if (selectedPotion instanceof WeakenPotion) {
                selectedPotion.use(player, enemy); // Use the potion on the enemy
            }

            player.removePotion(selectedPotion);

            // After using a potion, the enemy may attack the player
            if (enemy.getHP() > 0) {
                Battle battle = new Battle(player, enemy);
                battle.attackPlayer(); // Enemy attacks player
            }

            JOptionPane.showMessageDialog(this, "You used " + selectedPotion.getName() + "!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid potion selection!");
        }
    }

    // Launch the UsePotionGUI
    public static void showPotionGUI(Player player, Enemy enemy) {
        SwingUtilities.invokeLater(() -> {
            UsePotionGUI potionGUI = new UsePotionGUI(player, enemy);
            potionGUI.setVisible(true);
        });
    }
}
