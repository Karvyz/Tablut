package Vues.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CListCellRenderer extends DefaultListCellRenderer {
    private static final int TEXT_PADDING = 10;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (renderer instanceof JLabel) {
            ((JLabel) renderer).setBorder(new EmptyBorder(2, TEXT_PADDING, 2, 0));
            String text = ((JLabel) renderer).getText();
            String name = text.substring(0, text.lastIndexOf("-") - 1);
            System.out.println(name);
            String date = text.substring(text.lastIndexOf("-") + 2);
            System.out.println(date);
            int nameWidth = name.length();
            System.out.println(nameWidth);
            ((JLabel) renderer).setText(String.format("%s%s", name, padRight(date, 25 - nameWidth)));
        }

        //renderer.setFont(new Font("Arial", Font.PLAIN, 18));

        // Modif couleur de sélection
        if (isSelected) {
            renderer.setBackground(new Color(205, 190, 53, 192));
            renderer.setForeground(Color.BLACK);
        } else {
            renderer.setForeground(Color.WHITE);
        }

        return renderer;
    }

    private String padRight(String s, int n) {
        return String.format("%" + n + "s", "") + s;
    }
}