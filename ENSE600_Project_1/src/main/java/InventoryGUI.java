
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alexs
 */
public class InventoryGUI extends JFrame {

    private Player player;
    private JTable itemTable;
    private JButton useButton;
    private JButton cancelButton;

    public InventoryGUI(Player player) {
        this.player = player;
        initializeUI(player.getInventoryItemMap());
    }

    private void initializeUI(Map<Item, Integer> itemMap) {
        setTitle("Inventory");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Item", "Quantity"};
        Object[][] data = new Object[itemMap.size()][2];
        int i = 0;
        for (Map.Entry<Item, Integer> entry : itemMap.entrySet()) {
            data[i][0] = entry.getKey().getName();
            data[i][1] = entry.getValue();
            i++;
        }

        itemTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(itemTable);

        useButton = new JButton("Use/Equip Item");
        cancelButton = new JButton("Cancel");

        useButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow != -1) {
                    String itemName = (String) itemTable.getValueAt(selectedRow, 0);
                    Item selectedItem = getItemByName(itemName, itemMap);
                    if (selectedItem != null) {
                        useItem(selectedItem);
                    }
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(useButton);
        buttonPanel.add(cancelButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private Item getItemByName(String itemName, Map<Item, Integer> itemMap) {
        for (Item item : itemMap.keySet()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    private void useItem(Item item) {
        if (item instanceof Potion) {
            ((Potion) item).use(player, player); // Use the potion on the player
            player.removeItemFromInventory(item.getId());
            JOptionPane.showMessageDialog(this, "Used " + item.getName());
        } else if (item instanceof Weapon) {
            player.setWeapon((Weapon) item);
            JOptionPane.showMessageDialog(this, "Equipped " + item.getName());
        } else {
            JOptionPane.showMessageDialog(this, "This item cannot be used.");
        }
        dispose();
    }
}
