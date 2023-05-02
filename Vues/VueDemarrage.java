package Vues;

import javax.swing.*;
import java.awt.*;

class VueDemarrage extends JPanel {
    Image logo;
    int logoHeight;

    VueDemarrage() {
        //setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Chargement des assets
        logo = Imager.getImageBuffer("logo.png");

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setForeground(new Color(165, 42, 0));
        progressBar.setIndeterminate(true);

        add(progressBar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int h = getHeight();
        int logoWidth = getWidth();

        logoHeight = (logo.getHeight(null) * logoWidth) / logo.getWidth(null);

        int x = 0;

        g.drawImage(logo, x, ((h-logoHeight)/2), logoWidth, logoHeight, null);
    }
}
