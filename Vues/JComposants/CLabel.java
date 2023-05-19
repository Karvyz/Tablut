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
    }
}
