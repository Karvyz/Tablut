package Vues.JComposants;

import Vues.Imager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static Vues.Imager.getImageIcon;

public class TexteJeu extends JPanel {

    JLabel nombre1;
    JLabel nombre2;
    ImageIcon backgroundimage;
    private Shape shape;

    public TexteJeu(int nb1, int nb2) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        nombre1 = new JLabel(String.valueOf(nb1));
        nombre1.setFont(new Font("Arial", Font.BOLD, 20));
        nombre1.setForeground(Color.BLACK);

        nombre2 = new JLabel(String.valueOf(nb2));
        nombre2.setFont(new Font("Arial", Font.BOLD, 20));
        nombre2.setForeground(Color.BLACK);

        setBorder(new EmptyBorder(7, 25, 7, 25));
        setOpaque(false);

        backgroundimage = getImageIcon("fond_pierre.png");

        // - Chargements des images
        ImageIcon fois = new ImageIcon(Imager.getScaledImage("fois.png", 20, 24));
        ImageIcon PB = new ImageIcon(Imager.getScaledImage("PB_barre.png", 25, 30));
        ImageIcon PN = new ImageIcon(Imager.getScaledImage("PN_barre.png", 25, 30));

        add(new JLabel(PN));
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(new JLabel(fois));
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(nombre1);

        add(Box.createRigidArea(new Dimension(20, 0)));

        add(new JLabel(PB));
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(new JLabel(fois));
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(nombre2);

    }

    public void set(int nb1, int nb2) {
        nombre1.setText(String.valueOf(nb1));
        nombre2.setText(String.valueOf(nb2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
        g2.setClip(shape);

        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.setColor(getBackground());
        int radius = 35;
        g2.fillRoundRect(0, 0, radius > 0 ? getWidth() - 1 : getWidth(), radius > 0 ? getHeight() - 1 : getHeight(), radius, radius);

        this.backgroundimage.paintIcon(null, g, 0, 0);

        super.paintComponent(g);
    }
}

