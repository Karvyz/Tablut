package Vues.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static Vues.Imager.getImageIcon;

public class CPions extends JPanel {
    private final int hgap = 5;
    private boolean reverse = false;
    ImageIcon backgroundimage;
    private Shape shape;

    public CPions(boolean reverse) {
        this.reverse = reverse;
        setOpaque(false);
        //setBackground(null);
        setLayout(new GridLayout(1, 0, hgap, 0));
        //setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backgroundimage = getImageIcon("fond_pierre.png");
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
        g2.setClip(shape);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 45, 45);

        this.backgroundimage.paintIcon(null, g, 0, 0);

        super.paintComponent(g);
    }
}