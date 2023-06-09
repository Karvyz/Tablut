package Vues.JComposants;

import javax.swing.*;
import java.awt.*;

public class CLabel extends JLabel {
    public CLabel(String text) {
        super(text);
        setOpaque(false);
        Font label1Font = new Font("Courier", Font.PLAIN, 15);
        setFont(label1Font);
        setForeground(Color.WHITE);
        setBackground(new Color(0, 0, 0, 0));
    }

    public CLabel jaune() {
        setBackground(new Color(0, 0, 0, 0));
        setForeground(new Color(0xE5E21A));
        Font label1Font = new Font("Courier", Font.BOLD, 16);
        setFont(label1Font);
        return this;
    }
}
