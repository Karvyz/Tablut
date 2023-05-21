package Vues;

import Modele.Joueurs;
import Modele.Music;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static java.awt.GridBagConstraints.*;

import Modele.TypeJoueur;
import Modele.TypePion;
import Vues.JComposants.*;

class VueJeu extends JPanel {

    CollecteurEvenements controleur;
    VueNiveau vueNiveau;
    private InfoJoueur j1;
    private InfoJoueur j2;

    private JLabel endGameText;
    private TexteJeu texteJeu;
    private JFrame topFrame;

    private JPanel mainPanel, topPanel, endGamePanel;
    private JDialog endGameDialog;
    Image background;

    JButton[] controls = new JButton[3];

    JButton sauvegarder = new CButton(new ImageIcon(Imager.getScaledImage("assets/Disquette.png", 20, 20))).blanc();
    ;

    VueJeu(CollecteurEvenements c) {
        controleur = c;

        j1 = new InfoJoueur(true);
        j2 = new InfoJoueur(false);

        setLayout(new OverlayLayout(this));

        // Chargement des assets
        background = Imager.getImageBuffer("assets/logo.png");
        // --

        JPanel contenu = new JPanel(new GridBagLayout());
        contenu.setOpaque(false);

        addTop(contenu);
        addMain(contenu);
        addBottom(contenu);

        add(contenu);

        Action saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Traitement à effectuer lorsque Ctrl + S est enfoncé
                ActionBoutonSauvegarder();
            }
        };

        // Liaison de l'action à l'InputMap et au KeyStroke correspondant
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveAction");
        getActionMap().put("saveAction", saveAction);
    }

    private JDialog EndGameDialog() {
        JDialog dialog = new JDialog(JOptionPane.getRootFrame(), "Fin de partie", true);
        dialog.setResizable(false);
        dialog.setMinimumSize(new Dimension(1000, -1));
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setLocationRelativeTo(null);
        endGameText = new JLabel("");
        return dialog;
    }

    private void addEndGame() {
        if (endGameDialog == null) {
            endGameDialog = EndGameDialog();
            endGameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            endGameDialog.setLocationRelativeTo(null);
        }

        Container contentPane = endGameDialog.getContentPane();

        contentPane.removeAll();

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
        banner.setOpaque(false);
        endGameText.setFont(new Font("Arial", Font.BOLD, 30));
        endGameText.setForeground(Color.white);
        banner.add(endGameText, gbc2);

        gbc2.gridy = 1;
        gbc2.anchor = CENTER;
        gbc2.insets = new Insets(20, 0, 0, 0);
        JPanel endButtons = new JPanel();
        endButtons.setOpaque(false);
        JButton menu = new CButton("Menu principal");
        JButton retry = new CButton("Rejouer ?").blanc();
        JButton consulter = new CButton("Consulter");
        endButtons.add(menu);
        endButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        endButtons.add(retry);
        endButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        endButtons.add(consulter);

        banner.add(endButtons, gbc2);

        endGamePanel.setSize(1000, 600);
        endGamePanel.setAlignmentX(CENTER_ALIGNMENT);

        endGamePanel.add(banner, gbc);

        endGameDialog.add(endGamePanel);

        endGameDialog.pack();

        endGameDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Action personnalisée
                controleur.jeu().setVainqueur(null); //permet de ne plus rouvrir apres avoir fais la croix, au moins on peut consulter
                controleur.jeu().setConsulter(true);
                // Disposer le JDialog
                endGameDialog.dispose();
            }
        });

        menu.addActionListener((e) -> {
            endGameDialog.setVisible(false);
            controls[0].setEnabled(false);
            controls[1].setEnabled(true);
            controls[2].setEnabled(false);
            controleur.jeu().reset();
            controleur.jeu().setCoordooneJouerIA(null, null);
            controleur.afficherMenuPrincipal();
        });
        retry.addActionListener((e) -> {
            endGameDialog.setVisible(false);
            controls[0].setEnabled(false);
            controls[1].setEnabled(true);
            controls[2].setEnabled(false);
            controleur.jeu().setCoordooneJouerIA(null, null);
            controleur.partieSuivante();
        });
        consulter.addActionListener((e) -> {
            controleur.jeu().setVainqueur(null); //permet de ne plus rouvrir apres avoir fais la croix, au moins on peut consulter
            controleur.jeu().setConsulter(true);
            sauvegarder.setEnabled(false);
            // Disposer le JDialog
            endGameDialog.dispose();
        });
    }

    void showEnd() {
        // TODO : Ajout du point du vainqueur
        Joueurs vainqueur = controleur.jeu().vainqueur();
        if (vainqueur == null) {
            return;
        }
        Joueurs perdant;
        if (vainqueur == controleur.jeu().getJoueur1()) {
            perdant = controleur.jeu().getJoueur2();
        } else {
            perdant = controleur.jeu().getJoueur1();
        }

        if (controleur.jeu().partieTerminee()) {
            controls[1].setEnabled(false);
        }

        // Fin partie
        if (endGameDialog == null) {

            endGameDialog = EndGameDialog();
        }

        endGamePanel = new JPanel();

        if (vainqueur.estHumain()) {
            endGamePanel.setBackground(new Color(100, 183, 68));
            endGameDialog.setTitle("Victoire !");
            if (!perdant.estHumain()) {
                switch (perdant.type()) {
                    case IA_FACILE:
                        endGameText.setText("<html>Tu as gagné contre l'IA facile !<br>Essaye l'IA moyenne.</html>");
                        break;
                    case IA_MOYEN:
                        endGameText.setText("<html>Tu as gagné contre l'IA moyenne !<br>Essaye l'IA difficile !</html>");
                        break;
                    case IA_DIFFICILE:
                        endGameText.setText("Tu as gagné contre l'IA difficile, bravo !!");
                        break;
                    default:
                        endGameText.setText("Tu as gagné contre... un alien ?");
                        break;
                }
            } else {
                endGamePanel.setBackground(new Color(85, 91, 97));
                String svainqueur = "";
                if (vainqueur.nom().equals("Attaquant"))
                    svainqueur = "L'attaquant";
                else if (vainqueur.nom().equals("Défenseur"))
                    svainqueur = "Le défenseur";
                else
                    svainqueur = vainqueur.nom();
                String sperdant = "";
                if (perdant.nom().equals("Attaquant"))
                    sperdant = "L'attaquant";
                else if (perdant.nom().equals("Défenseur"))
                    sperdant = "Le défenseur";
                else
                    sperdant = perdant.nom();
                endGameText.setText("<html>" + svainqueur + " a gagné !<br>" + sperdant + " a perdu.</html>");
            }
        } else {
            endGameDialog.getComponent(0).setBackground(new Color(201, 67, 67));
            endGamePanel.setBackground(new Color(85, 91, 97));
            String typeIA = "";
            switch (vainqueur.type()) {
                case IA_FACILE:
                    typeIA = "facile";
                    break;
                case IA_MOYEN:
                    typeIA = "moyenne";
                    break;
                case IA_DIFFICILE:
                    typeIA = "difficile";
                    break;
                default:
                    typeIA = "alien";
                    break;
            }
            if (perdant.estHumain()) {
                endGamePanel.setBackground(new Color(201, 67, 67));
                endGameDialog.setTitle("Défaite !");
                endGameText.setText("Dommage ! Tu as perdu contre l'IA " + typeIA + ".");
            } else {
                endGameDialog.getComponent(0).setBackground(new Color(85, 91, 97));
                if (vainqueur.aPionsBlancs()) {
                    if (vainqueur.nom().equals("Défenseur"))
                        endGameText.setText("Le défenseur, IA " + typeIA + " a gagné !");
                    else
                        endGameText.setText("Le défenseur, IA " + typeIA + " " + vainqueur.nom() + " a gagné !");
                } else {
                    endGameDialog.getComponent(0).setBackground(new Color(85, 91, 97));
                    if (vainqueur.nom().equals("Attaquant"))
                        endGameText.setText("L'attaquant, IA " + typeIA + " a gagné !");
                    else
                        endGameText.setText("L'attaquant, IA " + typeIA + " " + vainqueur.nom() + " a gagné !");
                }
            }
        }
        addEndGame();
        endGameDialog.setVisible(true);
    }

    private void addTop(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(5, 5, 5, 5);

        topPanel = new JPanel();
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
                new JMenuItem("Charger Partie"),
                new JMenuItem("Menu principal"),
                new JMenuItem("Quitter"),
        };

        menu_items[0].addActionListener((e) -> {
            Joueurs[] joueurs = new Joueurs[2];
            joueurs[0] = controleur.jeu().getJoueur1();
            joueurs[1] = controleur.jeu().getJoueur2();

            controleur.jeu().reset();
            controleur.nouvellePartie(joueurs[0].nom(), joueurs[0].type(), TypePion.ATTAQUANT, joueurs[1].nom(), joueurs[1].type(), TypePion.DEFENSEUR);
            texteJeu = new TexteJeu(0, 0);
            controleur.afficherJeu();
            controls[0].setEnabled(false);
            controls[1].setEnabled(true);
            controls[2].setEnabled(false);
            sauvegarder.setEnabled(true);
        });
        menu_items[1].addActionListener((e) -> {
            sauvegarder.setEnabled(true);
            controleur.jeu().reset();
            controleur.afficherMenuChargerPartie();
        });
        menu_items[2].addActionListener((e) -> {
            sauvegarder.setEnabled(true);
            controleur.jeu().reset();
            controleur.afficherMenuPrincipal();
        });
        menu_items[3].addActionListener(e -> controleur.toClose());

        JCheckBoxMenuItem checkBoxMenuItemMusic = new JCheckBoxMenuItem("Musique");
        checkBoxMenuItemMusic.setSelected(false);
        checkBoxMenuItemMusic.addActionListener(new Music());

        for (JMenuItem menu_item : menu_items) {
            menu_item.setFont(new Font("Arial", Font.PLAIN, 14));
            menu_item.setBorderPainted(false);
            menu_item.setUI(new CMenuItemUI(true));
            menu.add(menu_item);
        }

        checkBoxMenuItemMusic.setFont(new Font("Arial", Font.PLAIN, 14));
        checkBoxMenuItemMusic.setBorderPainted(false);
        menu.add(checkBoxMenuItemMusic);

        sauvegarder.addActionListener(e -> ActionBoutonSauvegarder());

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

        // -- Réserve pions mangés
        c.insets = new Insets(8, 0, 0, 0);
        JPanel pionsPanel = new JPanel();
        pionsPanel.setOpaque(false);
        texteJeu = new TexteJeu(0, 0);
        pionsPanel.add(texteJeu);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
    }

    private void addMain(JPanel contenu) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        c.anchor = GridBagConstraints.CENTER;

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        contenu.add(mainPanel, c);
        // -----------

        c.fill = VERTICAL;

        // - J1
        //c.insets = new Insets(10, 50, 0, 50);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        c.weighty = 1;
        c.anchor = CENTER;
        mainPanel.add(j1, c);

        // - J2
        //c.insets = new Insets(10, 50, 0, 50);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = CENTER;
        mainPanel.add(j2, c);
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

        controls = new JButton[]{
                new CButton(new ImageIcon(Imager.getScaledImage("assets/undo.png", 18, 18))).blanc(),
                new CButton(new ImageIcon(Imager.getScaledImage("assets/solution.png", 40, 40))).solution(),
                new CButton(new ImageIcon(Imager.getScaledImage("assets/redo.png", 18, 18))).blanc(),
        };

        controls[0].setEnabled(false);
        controls[2].setEnabled(false);

        controls[0].addActionListener((e) -> {
            controleur.jeu().annuler();
            ModifBoutonUndo();
            ModifBoutonRedo();

        });

        controls[1].addActionListener((e) -> {
            if (!controleur.jeu().partieTerminee()) {
                controleur.jeu().solution();
            }
        });
        controls[2].addActionListener((e) -> {
            controleur.jeu().refaire();
            ModifBoutonUndo();
            ModifBoutonRedo();
        });

        for (JButton button : controls) {
            button.setFocusable(false);
            controlsPanel.add(button);
        }

        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = new Insets(10, 10, 10, 10);
        bottomPanel.add(controlsPanel, c);
    }

    void ModifBoutonUndo() {
        controleur.jeu().setAideIA(null);
        if (controleur.jeu().peutAnnuler()) {
            if (!controleur.jeu().getJoueur1().estHumain() && !controleur.jeu().getJoueur2().estHumain())
                controls[0].setEnabled(false);
            else
                controls[0].setEnabled(true);
        } else {
            controls[0].setEnabled(false);
        }
        if (controleur.jeu().partieTerminee() && !controleur.jeu().coup_annule.estVide()) {
            controls[0].setEnabled(true);
        } else if (!controleur.jeu().getJoueur1().estHumain() && !controleur.jeu().getJoueur2().estHumain())
            controls[0].setEnabled(false);
    }

    void ModifBoutonRedo() {
        controleur.jeu().setAideIA(null);
        if (controleur.jeu().peutRefaire()) {
            if (!controleur.jeu().getJoueur1().estHumain() && !controleur.jeu().getJoueur2().estHumain())
                controls[2].setEnabled(false);
            else
                controls[2].setEnabled(true);
        } else {
            controls[2].setEnabled(false);
        }
        if (controleur.jeu().partieTerminee() && !controleur.jeu().coup_a_refaire.estVide()) {
            controls[2].setEnabled(true);
        } else if (!controleur.jeu().getJoueur1().estHumain() && !controleur.jeu().getJoueur2().estHumain())
            controls[2].setEnabled(false);
    }

    private JPanel addUserActions() {
        JPanel userActions = new JPanel();
        userActions.setOpaque(false);
        return userActions;
    }

    void nouvellePartie() {
        vueNiveau = new VueNiveau(controleur, this, j1, j2, texteJeu);
        controleur.jeu().ajouteObservateur(vueNiveau);

        controls[1].setEnabled(true);

        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        System.out.println(topFrame);

        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        c.insets = new Insets(5, 28, 5, 28);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.6;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        System.out.println(j1);
        // Initialisation du niveau
        String s1 = "";
        if (!controleur.jeu().getJoueur1().estHumain()) {
            s1 = "IA";
            switch (controleur.jeu().getJoueur1().type()) {
                case IA_FACILE:
                    s1 += " Facile";
                    break;
                case IA_MOYEN:
                    s1 += " Moyen";
                    break;
                case IA_DIFFICILE:
                    s1 += " Difficile";
                    break;
            }
        } else {
            s1 = controleur.jeu().getJoueur1().nom();
        }
        j1.setName(s1);
        j1.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur1())[0], controleur.jeu().info_pion(controleur.jeu().getJoueur1())[1]);

        String s2 = "";
        if (!controleur.jeu().getJoueur2().estHumain()) {
            s2 = "IA";
            switch (controleur.jeu().getJoueur2().type()) {
                case IA_FACILE:
                    s2 += " Facile";
                    break;
                case IA_MOYEN:
                    s2 += " Moyen";
                    break;
                case IA_DIFFICILE:
                    s2 += " Difficile";
                    break;
            }
        } else {
            s2 = controleur.jeu().getJoueur2().nom();
        }
        j2.setName(s2);
        j2.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur2())[0], controleur.jeu().info_pion(controleur.jeu().getJoueur2())[1]);

        vueNiveau.miseAJour();
    }

    void restaurePartie() {
        vueNiveau = new VueNiveau(controleur, this, j1, j2, texteJeu);
        controleur.jeu().ajouteObservateur(vueNiveau);

        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        c.insets = new Insets(5, 28, 5, 28);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.6;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        // Initialisation du niveau
        j1.setName((!controleur.jeu().getJoueurCourant().estHumain() ? "(IA) " : "") + controleur.jeu().getJoueur1().nom());
        j1.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur1())[0], controleur.jeu().info_pion(controleur.jeu().getJoueur1())[1]);

        j2.setName((!controleur.jeu().getJoueurSuivant().estHumain() ? "(IA) " : "") + controleur.jeu().getJoueur2().nom());
        j2.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur2())[0], controleur.jeu().info_pion(controleur.jeu().getJoueur2())[1]);

        vueNiveau.miseAJour();
    }


    private void ActionBoutonSauvegarder() {
        controleur.jeu().setEnCours(false);
        controleur.saveGame();
        controleur.jeu().setEnCours(true);
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
