package Vues;

import Modele.Joueurs;
import Modele.Music;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.awt.GridBagConstraints.*;

import Modele.TypePion;
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

    private JPanel mainPanel, topPanel, endGamePanel;
    private JDialog endGameDialog;
    Image background;

    VueJeu(CollecteurEvenements c) {
        controleur = c;

        j1 = new InfoJoueur(true);
        j2 = new InfoJoueur(false);

        setLayout(new OverlayLayout(this));

        // Chargement des assets
        background = Imager.getImageBuffer("assets/Mur.png");
        // --

        JPanel contenu = new JPanel(new GridBagLayout());
        contenu.setOpaque(false);

        addTop(contenu);
        addMain(contenu);
        addBottom(contenu);

        add(contenu);
    }

    private JDialog EndGameDialog() {
        JDialog dialog = new JDialog(JOptionPane.getRootFrame(), "Fin de partie", true);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(800, 200);
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
        JButton retry = new CButton("Rejouer?").blanc();
        endButtons.add(menu);
        endButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        endButtons.add(retry);

        banner.add(endButtons, gbc2);
        endGamePanel.add(banner, gbc);

        endGamePanel.setSize(1000, 600);

        endGameDialog.add(endGamePanel);

        menu.addActionListener((e) -> {
            endGameDialog.setVisible(false);
            controleur.jeu().reset();
            //controleur.fin();
            controleur.afficherMenuPrincipal();
        });
        retry.addActionListener((e) -> {
            endGameDialog.setVisible(false);
            controleur.partieSuivante();
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

        // Fin partie
        if(endGameDialog == null) {
            endGameDialog = EndGameDialog();
        }

        endGamePanel = new JPanel();

        if (vainqueur.estHumain()) {
            endGamePanel.setBackground(new Color(100, 183, 68));
            endGameDialog.getComponent(0).setBackground(new Color(100, 183, 68));
            endGameDialog.setTitle("Victoire !");
            if (!perdant.estHumain()) {
                switch (perdant.type()) {
                    case IA_FACILE:
                        endGameText.setText("Tu as gagné contre l'IA facile ! C'était \"facile\"..");
                        break;
                    case IA_MOYEN:
                        endGameText.setText("Tu as gagné contre l'IA moyenne ! Essaye l'IA difficile!");
                        break;
                    case IA_DIFFICILE:
                        endGameText.setText("Tu as gagné contre l'IA difficile, t'es un roi !");
                        break;
                    default:
                        endGameText.setText("Tu as gagné contre.. un alien ?");
                        break;
                }
            } else {
                endGameText.setText(vainqueur.nom() + " a gagné !\n" + perdant.nom() + " a perdu..");
            }
        } else {
            endGameDialog.getComponent(0).setBackground(new Color(201, 67, 67));
            endGamePanel.setBackground(new Color(201, 67, 67));
            if (perdant.estHumain()) {
                endGameDialog.setTitle("Défaite !");
                endGameText.setText("Dommage! Tu as perdu contre l'IA.. une prochaine fois!");
            } else {
                endGameDialog.getComponent(0).setBackground(new Color(120, 70, 50));
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
                if (vainqueur.aPionsBlancs())
                    endGameText.setText("Le défenseur, IA " + typeIA + " " + vainqueur.nom() + " a gagné !");
                else
                    endGameText.setText("L'attaquant, IA " + typeIA + " " + vainqueur.nom() + " a gagné !");
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
                new JMenuItem("Menu principal"),
                new JMenuItem("Quitter"),
        };

        menu_items[0].addActionListener((e) -> {
            Joueurs[] joueurs = new Joueurs[2];
            joueurs[0] = controleur.jeu().getJoueur1();
            joueurs[1] = controleur.jeu().getJoueur2();

            controleur.jeu().reset();
            controleur.nouvellePartie(joueurs[0].nom(), joueurs[0].type(), TypePion.ATTAQUANT, joueurs[1].nom(), joueurs[1].type(), TypePion.DEFENSEUR);
            controleur.jeu().setCoordooneJouerIA(null,null);
            texteJeu = new TexteJeu(0, 0);
            controleur.afficherJeu();
            controleur.jeu().metAJour();
        });
        menu_items[1].addActionListener((e) -> {
            controleur.jeu().reset();
            controleur.jeu().setCoordooneJouerIA(null,null);
            controleur.afficherMenuPrincipal();
        });
        menu_items[2].addActionListener(e -> controleur.toClose());

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
        topPanel.add(pionsPanel, c);
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

        // - J1
        c.insets = new Insets(10, 10, 0, 30);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 1;
        c.anchor = FIRST_LINE_START;
        mainPanel.add(j1, c);

        // - J2
        c.insets = new Insets(10, 30, 0, 10);
        c.anchor = FIRST_LINE_END;
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

        JButton[] controls = {
                new CButton(new ImageIcon(Imager.getScaledImage("assets/undo.png", 18, 18))).blanc(),
                new CButton(new ImageIcon(Imager.getScaledImage("assets/solution.png", 40, 40))).solution(),
                new CButton(new ImageIcon(Imager.getScaledImage("assets/redo.png", 18, 18))).blanc(),
        };

        controls[0].addActionListener(e -> controleur.jeu().annuler());
        controls[1].addActionListener(e -> controleur.jeu().solution());
        controls[2].addActionListener(e -> controleur.jeu().refaire());

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

    private JPanel addUserActions() {
        JPanel userActions = new JPanel();
        userActions.setOpaque(false);
        return userActions;
    }

    void nouvellePartie() {
        vueNiveau = new VueNiveau(controleur, this, j1, j2, texteJeu);
        controleur.jeu().ajouteObservateur(vueNiveau);

        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        System.out.println(topFrame);

        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        c.insets = new Insets(5, 28, 5, 28);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        System.out.println(j1);
        // Initialisation du niveau
        String s1 = "";
        if(!controleur.jeu().getJoueurCourant().estHumain()) {
            s1 = "IA";
            switch(controleur.jeu().getJoueurCourant().type()) {
                case IA_FACILE:
                    s1 += " Facile";
                    break;
                case IA_MOYEN:
                    s1 = " Moyen";
                    break;
                case IA_DIFFICILE:
                    s1 = " Difficile";
                    break;
            }
        } else {
            s1 = controleur.jeu().getJoueurCourant().nom();
        }
        j1.setName(s1);
        j1.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur1())[0]);

        String s2 = "";
        if(!controleur.jeu().getJoueurSuivant().estHumain()) {
            s2 = "IA";
            switch(controleur.jeu().getJoueurSuivant().type()) {
                case IA_FACILE:
                    s2 += " Facile";
                    break;
                case IA_MOYEN:
                    s2 = " Moyen";
                    break;
                case IA_DIFFICILE:
                    s2 = " Difficile";
                    break;
            }
        } else {
            s2 = controleur.jeu().getJoueurSuivant().nom();
        }
        j2.setName(s2);
        j2.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur2())[0]);

        vueNiveau.miseAJour();
    }

    void restaurePartie() {
        vueNiveau = new VueNiveau(controleur, this, j1, j2, texteJeu);
        controleur.jeu().ajouteObservateur(vueNiveau);

        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        System.out.println(topFrame);

        topFrame.addKeyListener(new AdaptateurClavier(controleur));
        topFrame.setFocusable(true);
        topFrame.requestFocus();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // MARK: ESPACEMENT PLATEAU GAUCHE ET DROITE
        c.insets = new Insets(5, 28, 5, 28);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vueNiveau, c);

        // Initialisation du niveau
        j1.setName((!controleur.jeu().getJoueurCourant().estHumain() ? "(IA) " : "") + controleur.jeu().getJoueurCourant().nom());
        j1.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur1())[0]);

        j2.setName((!controleur.jeu().getJoueurSuivant().estHumain() ? "(IA) " : "") + controleur.jeu().getJoueurSuivant().nom());
        j2.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur2())[0]);

        vueNiveau.miseAJour();
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

            if (controleur.sauvegarderPartie(fileName)) {
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
