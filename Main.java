import javax.swing.*;
import java.awt.*;

public class Main {
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

        addButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Not yet implemented"));
        viewButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Not yet implemented"));
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(quitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}