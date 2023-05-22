package Vues.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CListCellRenderer extends DefaultListCellRenderer {
    private static final int TEXT_PADDING = 10;
    private static final int VERTICAL_PADDING = 5; // Espacement vertical entre les éléments


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (renderer instanceof JLabel) {
            ((JLabel) renderer).setBorder(new EmptyBorder(VERTICAL_PADDING, TEXT_PADDING, VERTICAL_PADDING, 0));
            renderer.setBackground(new Color(0, 0, 0, 0));
            String text = ((JLabel) renderer).getText();
            String name = text.substring(0, text.lastIndexOf("-") - 1);
            String date = text.substring(text.lastIndexOf("-") + 2);
            int nameWidth = name.length();
            ((JLabel) renderer).setText(String.format("%s%s", name, padRight(date, 25 - nameWidth)));
        }

        // Modif couleur de sélection
        if (isSelected) {
            renderer.setBackground(new Color(205, 190, 53, 192));
            renderer.setForeground(Color.BLACK);
        } else {
            renderer.setBackground(new Color(0, 0, 0, 0));
            renderer.setForeground(Color.WHITE);
        }

        return renderer;
    }

    private String padRight(String s, int n) {
        return String.format("%" + n + "s", "") + s;
    }
}