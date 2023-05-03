package Vues;

import Vues.JComposants.CButton;

import javax.swing.*;
import java.awt.*;

class VueMenuPrincipal extends JPanel {
    Image background;
    Image logo;

    VueMenuPrincipal(CollecteurEvenements c) {
        // Chargement des assets
        logo = Imager.getImageBuffer("assets/tablut.png");
        background = Imager.getImageBuffer("assets/logo.png");

        //setBackground(Color.PINK);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));


        JButton nouvellePartie = new CButton("Nouvelle Partie");
        nouvellePartie.addActionListener((e) -> {
            c.afficherMenuNouvellePartie();
        });

        JButton chargerPartie = new CButton("Charger Partie");
        chargerPartie.addActionListener((e) -> {
            c.afficherMenuChargerPartie();
        });

        JButton regles = new CButton("Règles");
        regles.addActionListener((e) -> {
            c.afficherRegles();
        });
        //JButton didacticiel = new CButton("Didacticiel");

        JButton quitter = new CButton("Quitter");
        quitter.addActionListener((e) -> {
            c.toClose();
        });

        Component[] components = {
                nouvellePartie,
                chargerPartie,
                regles,
                Box.createRigidArea(new Dimension(10, 30)),
                quitter
        };

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.add(new JLabel(new ImageIcon(Imager.getScaledImage(logo, 500, 400))));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        for (Component component : components) {
            if (component.getClass().getName().contains("Button")) {
                ((JButton) component).setAlignmentX(CENTER_ALIGNMENT);
                ((JButton) component).setAlignmentY(CENTER_ALIGNMENT);
            }
            buttonsPanel.add(component);
            if (!component.getClass().getName().contains("Box"))
                buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JPanel centerPanel = new JPanel(new GridBagLayout());

        //        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 110));
        //        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centerPanel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 110);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(leftPanel, gbc);
        gbc.insets = new Insets(0, 80, 0, 80);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(buttonsPanel, gbc);
        centerPanel.setBounds(centerPanel.getX() - 150, centerPanel.getY(), centerPanel.getWidth(), centerPanel.getHeight());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Affichage de l'image d'arrière plan
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //Color color2 = new Color(255, 140, 85);
        //Color color1 = new Color(255, 120, 105);
        //GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        //g2d.setPaint(gp);
        //g2d.fillRect(0, 0, w, h);
    }
}