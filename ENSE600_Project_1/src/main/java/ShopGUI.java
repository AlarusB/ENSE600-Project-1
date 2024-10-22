
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */

public class ShopGUI extends JFrame {

    private Player player;
    private List<Item> itemsForSale;
    private JLabel goldLabel;
    private JTextArea itemsDisplay;
    private JButton buyButton;
    private JButton exitButton;
    private JComboBox<String> itemSelection;

    // Constructor to initialize the shop GUI
    public ShopGUI(Player player) {
        this.player = player;
        this.itemsForSale = generateItemsForSale(); // Generate items for sale
        initializeUI();
    }

    // Initialize the user interface components
    private void initializeUI() {
        setTitle("Shop");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        goldLabel = new JLabel("Gold: " + player.getGold() + "g");
        topPanel.add(goldLabel, BorderLayout.NORTH);

        // TextArea for displaying items
        itemsDisplay = new JTextArea();
        itemsDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(itemsDisplay);

        updateItemsDisplay();

        // Drop-down list to select items
        itemSelection = new JComboBox<>();
        for (int i = 0; i < itemsForSale.size(); i++) {
            Item item = itemsForSale.get(i);
            itemSelection.addItem((i + 1) + ". " + item.getName() + " (Cost: " + item.getCost() + "g)");
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));

        // Add ComboBox and buttons
        bottomPanel.add(itemSelection);

        buyButton = new JButton("Buy Item");
        exitButton = new JButton("Exit Shop");

        bottomPanel.add(buyButton);
        bottomPanel.add(exitButton);

        // Add action listeners for buttons
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buySelectedItem();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the shop window
            }
        });

        // Add components to the frame
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Update the items display area
    private void updateItemsDisplay() {
        itemsDisplay.setText("Items for Sale:\n");
        for (Item item : itemsForSale) {
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                itemsDisplay.append(item.getName() + " - " + weapon.getDescription() + " ATK: " + weapon.getATK()
                        + " (Cost: " + weapon.getCost() + " gold)\n");
            } else {
                itemsDisplay.append(item.getName() + " - " + item.getDescription()
                        + " (Cost: " + item.getCost() + " gold)\n");
            }
        }
    }

    // Handle buying an item
    private void buySelectedItem() {
        int selectedIndex = itemSelection.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < itemsForSale.size()) {
            Item selectedItem = itemsForSale.get(selectedIndex);
            if (player.getGold() >= selectedItem.getCost()) {
                player.setGold(player.getGold() - selectedItem.getCost());
                if (selectedItem instanceof Weapon) {
                    player.setWeapon((Weapon) selectedItem);
                } else if (selectedItem instanceof Potion) {
                    player.addToPotionBag((Potion) selectedItem);
                }
                JOptionPane.showMessageDialog(this, "You bought " + selectedItem.getName() + "!");
                goldLabel.setText("Gold: " + player.getGold() + "g");
            } else {
                JOptionPane.showMessageDialog(this, "Not enough gold!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid item selection!");
        }
    }

    // Generate a list of items for sale in the shop
    private List<Item> generateItemsForSale() {
        List<Item> items = new ArrayList<>();
        items.add(new Weapon("Wooden Club", 30, "A tough Wooden Club."));
        items.add(new Weapon("Steel Sword", 60, "A sturdy Steel Sword."));
        items.add(new Weapon("Laser Sword", 120, "A pristine Laser Sword, can cut anything in half."));
        items.add(new WeakenPotion("Prime Energy", 10));
        items.add(new AttackPotion("Mountain Dew", 50));
        items.add(new HealingPotion("Water", 100));
        return items;
    }

    // Display the Shop GUI
    public static void showShop(Player player) {
        SwingUtilities.invokeLater(() -> {
            ShopGUI shopGUI = new ShopGUI(player);
            shopGUI.setVisible(true);
        });
    }

    // Check if the player encounters a shop (30% chance)
    public static boolean encounterShop() {
        Random random = new Random();
        return random.nextInt(100) < 30;
    }
}
