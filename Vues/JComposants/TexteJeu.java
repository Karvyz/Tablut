package Vues.JComposants;

import Vues.Imager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TexteJeu extends JPanel {

    JLabel nombre1;
    JLabel nombre2;

    public TexteJeu(int nb1, int nb2) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        nombre1 = new JLabel(String.valueOf(nb1));
        nombre1.setFont(new Font("Arial", Font.BOLD, 14));

        nombre2 = new JLabel(String.valueOf(nb2));
        nombre2.setFont(new Font("Arial", Font.BOLD, 14));

        setBorder(new EmptyBorder(7, 25, 7, 25));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));

        // - Chargements des images
        ImageIcon fois = new ImageIcon(Imager.getScaledImage("fois.png", 25, 30));
        ImageIcon PB = new ImageIcon(Imager.getScaledImage("PB_barre.png", 25, 30));
        ImageIcon PN = new ImageIcon(Imager.getScaledImage("PN_barre.png", 25, 30));


        add(new JLabel(PB));
        add(new JLabel(fois));
        add(new JLabel(String.valueOf(nb1)));

        add(Box.createRigidArea(new Dimension(10, 0)));

        add(new JLabel(PN));
        add(new JLabel(fois));
        add(new JLabel(String.valueOf(nb2)));
    }

    public TexteJeu(String s) {
        add(new JLabel(s));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.setColor(getBackground());
        int radius = 35;
        g2.fillRoundRect(0, 0, radius > 0 ? getWidth()-1 : getWidth(), radius > 0 ? getHeight()-1 : getHeight(), radius, radius);

    }
}

