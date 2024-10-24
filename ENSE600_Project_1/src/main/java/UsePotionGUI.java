
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class UsePotionGUI extends JFrame {
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
        updatePotionBag(); // Initialize potion bag
        initializeUI();     // Set up the graphical interface
    }

    // Method to update the player's potion bag and sync the potionBag list
    private void updatePotionBag() {
        this.potionBag = new ArrayList<>(Arrays.asList(player.getPotionBag())); // Convert Potion[] to List<Potion>

        // Check if the potionBag is null or empty
        potionBag.removeIf(potion -> potion == null);
        if (potionBag.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No more potions available!", "No Potions", JOptionPane.WARNING_MESSAGE);
            dispose(); // Close the window if there are no potions
        }
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

        // Ensure potionBag is not null or empty before populating the combo box
        populatePotionSelection();

        mainPanel.add(potionLabel, BorderLayout.NORTH);
        mainPanel.add(potionSelection, BorderLayout.CENTER);

        // Panel for buttons (Use and Cancel)
        JPanel buttonPanel = new JPanel();
        usePotionButton = new JButton("Use Potion");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(usePotionButton);
        buttonPanel.add(cancelButton);

        // Add action listeners for buttons
        usePotionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                useSelectedPotion();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window without using a potion
            }
        });

        // Add components to the frame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to populate the potion selection dropdown
    private void populatePotionSelection() {
        potionSelection.removeAllItems();
        if (potionBag != null && !potionBag.isEmpty()) {
            for (int i = 0; i < potionBag.size(); i++) {
                Potion potion = potionBag.get(i);
                if (potion != null) {
                    potionSelection.addItem((i + 1) + ". " + potion.getName());
                }
            }
        }
    }

    // Use the selected potion from the dropdown
    private void useSelectedPotion() {
        int selectedIndex = potionSelection.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < potionBag.size()) {
            Potion selectedPotion = potionBag.get(selectedIndex);

            // Apply the potion effect depending on its type
            if (selectedPotion instanceof AttackPotion || selectedPotion instanceof HealingPotion) {
                selectedPotion.use(player, player);
            } else if (selectedPotion instanceof WeakenPotion) {
                selectedPotion.use(player, enemy);
            }

            // Remove the used potion from the player's potion bag
            player.removePotion(selectedPotion);

            // Resync the potion bag after using a potion
            updatePotionBag();
            populatePotionSelection();

            // After using a potion, the enemy may attack the player
            if (enemy.getHP() > 0) {
                Battle battle = new Battle(player, enemy);
                battle.attackPlayer();
            }

            JOptionPane.showMessageDialog(this, "You used " + selectedPotion.getName() + "!");
            dispose(); // Close the potion window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid potion selection!");
        }
    }

    // Launch the UsePotionGUI (can be called from GameGUI)
    public static void showPotionGUI(Player player, Enemy enemy) {
        SwingUtilities.invokeLater(() -> {
            UsePotionGUI potionGUI = new UsePotionGUI(player, enemy);
            potionGUI.setVisible(true);
        });
    }
}
