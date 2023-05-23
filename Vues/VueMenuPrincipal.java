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

        // Bouton nouvelle partie
        JButton nouvellePartie = new CButton("Nouvelle Partie").big();
        nouvellePartie.addActionListener((e) -> c.afficherMenuNouvellePartie());

        // Bouton charger partie
        JButton chargerPartie = new CButton("Charger Partie").big();
        chargerPartie.addActionListener((e) -> c.afficherMenuChargerPartie());

        // Bouton partie rapide
        JButton QuickPlay = new CButton("Partie Rapide").big();
        QuickPlay.addActionListener((e) -> {
            c.nouvelleQuickPartie();
            c.afficherQuickPartie();
        });

        // Bouton règles
        JButton regles = new CButton("Règles").big();
        regles.addActionListener((e) -> c.afficherRegles());

        // Bouton quitter
        JButton quitter = new CButton("Quitter").big();
        quitter.addActionListener((e) -> c.toClose());

        // Ajout des composants
        Component[] components = {
                nouvellePartie,
                chargerPartie,
                QuickPlay,
                regles,
                Box.createRigidArea(new Dimension(10, 30)),
                quitter
        };

        // Changement de la police des boutons
        for (Component component : components) {
            component.setFont(new Font("Arial", Font.BOLD, 16));
        }

        // Création des panels
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.add(new JLabel(new ImageIcon(Imager.getScaledImage(logo, 675, 300))));
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

        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 80, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centerPanel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 100, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(leftPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        buttonsPanel.setBounds(buttonsPanel.getX() - 300, buttonsPanel.getY(), buttonsPanel.getWidth(), buttonsPanel.getHeight());
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
    }
}
