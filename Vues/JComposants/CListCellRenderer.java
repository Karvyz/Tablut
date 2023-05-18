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
}