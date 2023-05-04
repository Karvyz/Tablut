package Vues;

import Modele.Joueurs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static java.awt.GridBagConstraints.*;

import Vues.JComposants.*;

class VueJeu extends JPanel {

    CollecteurEvenements controleur;
    VueNiveau vueNiveau;
    private InfoJoueur j1;
    private InfoJoueur j2;

    private JLabel endGameText;
    private TexteJeu texteJeu;
    //private final JPanel backgroundTop, backgroundBottom;
    private JFrame topFrame;

    private JPanel mainPanel, endGamePanel;
    Image background;

    VueJeu(CollecteurEvenements c) {
        controleur = c;

        j1 = new InfoJoueur(true);
        j2 = new InfoJoueur(false);

        setLayout(new OverlayLayout(this));

        // Fin partie
        JPanel endGamePanel = new JPanel();

        // Chargement des assets
        background = Imager.getImageBuffer("assets/logo.png");
        // --

        JPanel contenu = new JPanel(new GridBagLayout());
        contenu.setOpaque(false);

        addTop(contenu);
        addMain(contenu);
        addBottom(contenu);

        addEndGame();
        add(contenu);
        //add(background);
    }

    private void addEndGame() {
        endGamePanel = new JPanel(new GridBagLayout());
        endGamePanel.setOpaque(true);
        endGamePanel.setVisible(false);
        endGamePanel.setBackground(new Color(23, 23, 23, 163));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = BOTH;
        gbc.anchor = CENTER;

        JPanel banner = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = VERTICAL;
        gbc2.anchor = PAGE_START;

        banner.setBorder(new EmptyBorder(30, 0, 30, 0));
        banner.setBackground(new Color(100, 183, 68));
        endGameText = new JLabel("game over t tro nul");
        endGameText.setFont(new Font("Arial", Font.BOLD, 30));
        endGameText.setForeground(Color.white);
        banner.add(endGameText, gbc2);

        gbc2.gridy = 1;
        gbc2.anchor = CENTER;
        gbc2.insets = new Insets(20, 0, 0, 0);
        JPanel endButtons = new JPanel();
        endButtons.setOpaque(false);
        JButton menu = new CButton("Menu principal");
        JButton retry = new CButton("Rejouer?").blanc();
        endButtons.add(menu);
        endButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        endButtons.add(retry);
        menu.addActionListener((e) -> controleur.afficherMenuPrincipal());
        retry.addActionListener((e) -> {
            endGamePanel.setVisible(false);
            controleur.partieSuivante();
        });

        banner.add(endButtons, gbc2);
        endGamePanel.add(banner, gbc);

        add(endGamePanel);
    }

    void showEnd() {
        // TODO : Ajout du point du vainqueur
        Joueurs vainqueur = controleur.jeu().vainqueur();
        if (vainqueur == null){return;}
        Joueurs perdant;
        if (vainqueur == controleur.jeu().getJoueur1()) {
            perdant = controleur.jeu().getJoueur2();
        } else {
            perdant = controleur.jeu().getJoueur1();
        }


        if (vainqueur.estHumain()) {
            endGamePanel.getComponent(0).setBackground(new Color(100, 183, 68));
            if (!perdant.estHumain()) {
                switch (perdant.type()) {
                    case IA_FACILE:
                        endGameText.setText("Tu as gagné contre l'IA facile! C'était \"facile\"..");
                        break;
                    case IA_MOYEN:
                        endGameText.setText("Tu as gagné contre l'IA moyenne! Essaye l'IA difficile!");
                        break;
                    case IA_DIFFICILE:
                        endGameText.setText("Tu as gagné contre l'IA difficile, t'es un roi!");
                        break;
                    default:
                        endGameText.setText("Tu as gagné contre.. un alien?");
                        break;
                }
            } else {
                endGameText.setText(vainqueur.nom() + " a gagné !\n" + perdant.nom() + " a perdu..");
            }
        } else {
            endGamePanel.getComponent(0).setBackground(new Color(201, 67, 67));
            if (perdant.estHumain()) {
                endGameText.setText("Dommage! Tu as perdu contre l'IA.. une prochaine fois!");
            } else {
                endGameText.setText("L'IA " + vainqueur.nom() + " a gagné !");
            }
        }
        endGamePanel.setVisible(true);
    }

    private void addTop(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5, 5, 5, 5);

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new GridBagLayout());
        contenu.add(topPanel, c);
        // -----------

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(false);
        menuBar.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        menuBar.setMargin(new Insets(10, 10, 2, 14));
        buttonsPanel.add(menuBar);

        JMenu menu = new JMenu();
        menu.setBorderPainted(false);
        menu.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
        menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu.setUI(new CMenuUI());
        menu.setIcon(new ImageIcon(Imager.getScaledImage("assets/white_burger.png", 32, 32)));
        menuBar.add(menu);

        JMenuItem[] menu_items = {
                new JMenuItem("Nouvelle Partie"),
                new JMenuItem("Menu principal"),
                new JMenuItem("Quitter")
        };

        menu_items[0].addActionListener(e -> controleur.afficherMenuNouvellePartie());
        menu_items[1].addActionListener(e -> controleur.afficherMenuPrincipal());
        menu_items[2].addActionListener(e -> controleur.toClose());

        for (JMenuItem menu_item: menu_items) {
            menu_item.setFont(new Font("Arial", Font.PLAIN, 14));
            menu_item.setBorderPainted(false);
            menu_item.setUI(new CMenuItemUI(true));
            menu.add(menu_item);
        }

        JButton sauvegarder = new CButton(new ImageIcon(Imager.getScaledImage("assets/Disquette.png", 20, 20))).blanc();
        sauvegarder.addActionListener(e -> ActionBoutonSauvegarder(e));

        JButton regles = new CButton("? Règles").blanc();
        regles.addActionListener(e -> controleur.afficherRegles());

        menuBar.add(Box.createRigidArea(new Dimension(10, 0)));
        menuBar.add(sauvegarder);


        menuBar.add(Box.createRigidArea(new Dimension(10, 0)));
        menuBar.add(regles);


        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        topPanel.add(buttonsPanel, c);

        // --
        c.insets = new Insets(8, 0, 0, 0);
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        texteJeu = new TexteJeu();
        texteJeu.setFont(new Font("Arial", Font.PLAIN, 18));
        texteJeu.setForeground(Color.white);
        textPanel.add(texteJeu);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(textPanel, c);
    }

    private void addMain(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;

        c.anchor = GridBagConstraints.CENTER;

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        contenu.add(mainPanel, c);
        // -----------

        c.fill = GridBagConstraints.NONE;
        // MARK: ESPACEMENT POUR LE RESTE (hors plateau)
        c.insets = new Insets(10, 60, 0, 60);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = FIRST_LINE_END;
        mainPanel.add(j1, c);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = FIRST_LINE_START;
        mainPanel.add(j2, c);

        // --
        c.insets = new Insets(10, 60, 0, 60);
        c.fill = NONE;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = PAGE_END;

        mainPanel.add(addUserActions(), c);
    }

    private void addBottom(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.PAGE_END;

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new GridBagLayout());
        contenu.add(bottomPanel, c);
        // -----------

        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);

        JButton[] controls = {
                new CButton(new ImageIcon(Imager.getScaledImage("assets/undo.png", 18, 18))).blanc(),
                new CButton(new ImageIcon(Imager.getScaledImage("assets/redo.png", 18, 18))).blanc(),
        };

        controls[0].addActionListener(e -> controleur.jeu().annuler());
        controls[1].addActionListener(e -> controleur.jeu().refaire());

        for (JButton button: controls) {
            button.setFocusable(false);
            controlsPanel.add(button);
        }

        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = new Insets(10,10,10,10);
        bottomPanel.add(controlsPanel, c);
    }

    private JPanel addUserActions() {
        JPanel userActions = new JPanel();
        userActions.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;

        c.gridy = 1;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        return userActions;
    }

    void nouvellePartie() {
        endGamePanel.setVisible(false);
        vueNiveau = new VueNiveau(controleur, this, j1, j2, texteJeu);
        controleur.jeu().ajouteObservateur(vueNiveau);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        c.insets = new Insets(5,28,5,28);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        // Initialisation du niveau
        j1.setName((!controleur.jeu().getJoueurCourant().estHumain() ? "IA : " : "") + controleur.jeu().getJoueurCourant().nom());
        j1.setPions(controleur.jeu().getJoueurCourant().nombrePionsManges());

        j2.setName((!controleur.jeu().getJoueurSuivant().estHumain() ? "IA : " : "") + controleur.jeu().getJoueurSuivant().nom());
        j2.setPions(controleur.jeu().getJoueurSuivant().nombrePionsManges());

        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        vueNiveau.miseAJour();

        /*TODO POP UP début de parti quand on clique sur OK
        JButton button = new CButton("OK");
        button.addActionListener(e -> JOptionPane.getRootFrame().dispose());

        if (controleur.jeu().getJoueurCourant().estHumain() && controleur.jeu().getJoueurSuivant().estHumain()) {
            JOptionPane.showOptionDialog(null,
                    (controleur.jeu().getJoueurCourant().estHumain() ? controleur.jeu().getJoueurCourant().nom() : "L'IA " + controleur.jeu().getJoueurCourant().nom()) + " (attaquant) débute la partie avec les pions " + (controleur.jeu().getJoueurCourant().aPionsBlancs() ? "blancs" : "noirs\n") + (controleur.jeu().getJoueurSuivant().estHumain() ? "Le joueur " + controleur.jeu().getJoueurSuivant().nom() : "L'IA " + controleur.jeu().getJoueurSuivant().nom()) + " a les pions " + (controleur.jeu().getJoueurSuivant().aPionsBlancs() ? "blancs" : "noirs" + " il est le défenseur\n"),
                    " Début de la partie",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(Imager.getScaledImage("info.png", 24, 24)),
                    new JButton[]{button}, button);
        }*/
    }


    private void ActionBoutonSauvegarder(ActionEvent e) {
        controleur.jeu().setEnCours(false);
        saveGame();
        controleur.jeu().setEnCours(true);
    }

    private void saveGame() {
        String fileName = JOptionPane.showInputDialog(null, "Entrez le nom du fichier de sauvegarde:", "Sauvegarde", JOptionPane.PLAIN_MESSAGE);

        if (fileName != null && !fileName.trim().isEmpty()) { //Verifie le nom
            String directoryPath = "Resources/save/";
            File directory = new File(directoryPath);
            if (!directory.exists()) { //Verifie si le dossier existe ou le crée
                if (!directory.mkdirs()) {
                    handleSaveError("Échec de la création du dossier de sauvegarde");
                    return;
                }
            } else if (!directory.isDirectory() || !directory.canWrite()) {
                handleSaveError("Impossible d'écrire dans le dossier de sauvegarde");
                return;
            }
            fileName = directoryPath + fileName + ".save";

            if (controleur.jeu().sauvegarderPartie(fileName)) {
                JOptionPane.showMessageDialog(null, "Sauvegarde réussie", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            } else {
                handleSaveError("Échec de la sauvegarde");
            }
        } else if (fileName != null) {
            handleSaveError("Le nom de fichier ne peut pas être vide");
        }
    }

    private void handleSaveError(String msg) {
        JButton retryButton = new JButton("Recommencer");
        JButton cancelButton = new JButton("Annuler");


        retryButton.addActionListener(e -> {
            JOptionPane.getRootFrame().dispose(); // Ferme la boîte de dialogue d'erreur
            saveGame();
        });

        cancelButton.addActionListener(e -> {
            JOptionPane.getRootFrame().dispose(); // Ferme la boîte de dialogue d'erreur
        });

        int option = JOptionPane.showOptionDialog(null,
                msg,
                "Erreur",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new Object[]{retryButton, cancelButton},
                retryButton);

        if (option == JOptionPane.YES_OPTION) {
            saveGame();
        }
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
