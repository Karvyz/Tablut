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

        // Affichage de la taille de la frame
        // On récupère des informations sur l'écran
        GraphicsEnvironment env;
        GraphicsDevice device;
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();
        DisplayMode dm = device.getDisplayMode();

        System.out.println("Taille de la fenêtre : " + dm.getWidth() + "x" + dm.getHeight());

        // Ajout des composants
        Component[] components = {
                nouvellePartie,
                chargerPartie,
                QuickPlay,
                Box.createRigidArea(new Dimension((dm.getWidth() / 192), (dm.getHeight() / 36))),
                regles,
                Box.createRigidArea(new Dimension((dm.getWidth() / 192), (dm.getHeight() / 36))),
                quitter
        };

        // Changement de la police des boutons
        for (Component component : components) {
            component.setFont(new Font("Arial", Font.BOLD, 16));
        }

        // Création des panels
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.add(new JLabel(new ImageIcon(Imager.getScaledImage(logo, (dm.getWidth() / 284 * 100), (dm.getWidth() / 64 * 10)))));
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
        gbc.insets = new Insets(0, (dm.getWidth() / 384 * 10), (dm.getHeight() / 27 * 2), 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centerPanel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, dm.getHeight() / 108, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(leftPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        buttonsPanel.setBounds(buttonsPanel.getX() - (dm.getWidth() / 64 * 10), buttonsPanel.getY(), buttonsPanel.getWidth(), buttonsPanel.getHeight());
        //buttonsPanel.setBounds(buttonsPanel.getX(), buttonsPanel.getY(), buttonsPanel.getWidth(), buttonsPanel.getHeight());
        centerPanel.add(buttonsPanel, gbc);
        centerPanel.setBounds(centerPanel.getX() - (dm.getWidth() / 128 * 10), centerPanel.getY(), centerPanel.getWidth(), centerPanel.getHeight());
        //centerPanel.setBounds(centerPanel.getX(), centerPanel.getY(), centerPanel.getWidth(), centerPanel.getHeight());
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
