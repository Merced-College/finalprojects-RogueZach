import javax.swing.*;
import java.awt.*;

public class Main {
    private static final Pantry pantry = new Pantry();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Dog Food - Pantry Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("Add Ingredient");
        JButton viewButton = new JButton("View Pantry");
        JButton quitButton = new JButton("Quit");

        addButton.addActionListener(e -> addIngredient(frame));
        viewButton.addActionListener(e -> viewPantry(frame));
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(quitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void addIngredient(JFrame frame) {
        String name = JOptionPane.showInputDialog(frame, "Ingredient name:");
        if (name == null || name.isBlank()) return;

        String qtyStr = JOptionPane.showInputDialog(frame, "Quantity:");
        if (qtyStr == null || qtyStr.isBlank()) return;

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity.");
            return;
        }

        String unit = JOptionPane.showInputDialog(frame, "Unit (e.g., cups, grams):");
        if (unit == null || unit.isBlank()) return;

        pantry.put(name, new Ingredient(name, qty, unit));
        JOptionPane.showMessageDialog(frame, "Added: " + name);
    }

    private static void viewPantry(JFrame frame) {
        JTextArea area = new JTextArea(pantry.displayAll());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(350, 250));
        JOptionPane.showMessageDialog(
            frame, scroll,
            "Pantry (" + pantry.size() + " items)",
            JOptionPane.PLAIN_MESSAGE
        );
    }
}