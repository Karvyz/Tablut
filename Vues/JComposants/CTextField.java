package Vues.JComposants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static Vues.Imager.getImageBuffer;
import static Vues.Imager.getImageIcon;

public class CTextField extends JTextField {

    private int radius = 0;
    boolean background = false;
    ImageIcon backgroundimage;
    private Shape shape;

    public CTextField(String path) {
        backgroundimage = getImageIcon(path);
        // On réinitialise tous les paramètres par défaut
        setOpaque(false);
        // Customise le style du bouton
        //setForeground(Color.white);
        setForeground(new Color(173, 216, 230));
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }

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
        g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, radius, radius);

        this.backgroundimage.paintIcon(null, g, 0, 0);

        super.paintComponent(g);
    }

}
