package Vues.JComposants;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CJScrollBar extends JScrollBar {

    public CJScrollBar() {
        setUI(new CJScrollBarUI());
        setPreferredSize(new Dimension(20, 0)); // Ajustez la taille verticale de la barre de défilement ici
    }

    static class CJScrollBarUI extends BasicScrollBarUI {
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void configureScrollBarColors() {
            // Définissez ici les couleurs personnalisées pour la barre de défilement
            LookAndFeel.installColors(scrollbar, "ScrollBar.background", "ScrollBar.foreground");
            LookAndFeel.installProperty(scrollbar, "opaque", Boolean.FALSE);
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // Dessinez ici l'arrière-plan de la piste de la barre de défilement
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            // Dessinez ici le pouce de la barre de défilement
            g.setColor(Color.GRAY);
            g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
        }
    }
}