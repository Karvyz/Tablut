package Vues;

import Modele.TypeJoueur;
import Modele.TypePion;
import Vues.JComposants.CButton;
import Vues.JComposants.CComboxBox;
import Vues.JComposants.CTextField;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Type;

import static Vues.Imager.getImageIcon;

public class VueMenuSaisies extends JPanel {

    CollecteurEvenements controleur;
    private JPanel MenuSaisies;
    private JTextField nomJ1;
    private JComboBox typeJ1;
    private JButton jouerButton;
    private JButton menuPrincipalButton;
    private JButton quitter;
    private JPanel Boutons;
    private JPanel BoutonsQuitter;
    private JPanel Joueur2;
    private JPanel Joueur1;
    private JTextField nomJ2;
    private JComboBox typeJ2;
    private JComboBox comboBox1;
    int logoHeight;
    Image background;
    Image title;
    int called = 0;

    public VueMenuSaisies(CollecteurEvenements c) {
        // Chargement des assets
        background = Imager.getImageBuffer("assets/logo.png");
        title = Imager.getImageBuffer("tablut.png");

        controleur = c;

        typeJ1 = new CComboxBox();
        typeJ2 = new CComboxBox();
        menuPrincipalButton = new CButton("Menu Principal");
        jouerButton = new CButton("Jouer").blanc();
        comboBox1 = new CComboxBox();
        nomJ1 = new CTextField("Mur.png");
        //nomJ1 = new CTextField();
        nomJ2 = new CTextField("Mur.png");
        //nomJ2 = new CTextField();

        Boutons = new JPanel();
        Boutons.setOpaque(false);
        BoutonsQuitter = new JPanel();
        BoutonsQuitter.setOpaque(false);

        setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        MenuSaisies = this;
        setupUI();

        // Modifie la couleur du fond et la police
        for (Component co : MenuSaisies.getComponents()) {
            if (co instanceof JPanel) {
                for (Component coo : ((JPanel) co).getComponents()) {
                    if (coo instanceof CComboxBox) ((CComboxBox) coo).setEditable(true);
                    else if (coo instanceof JLabel) {
                        coo.setFont(new Font("Arial", Font.PLAIN, 14));
                        coo.setForeground(Color.WHITE);
                        ((JLabel) coo).setHorizontalAlignment(SwingConstants.CENTER);
                    }
                }
            }
        }

        menuPrincipalButton.addActionListener((e) -> controleur.afficherMenuPrincipal());

        //Action lors du clic sur le bouton jouer
        jouerButton.addActionListener((e) -> actionBoutonJouer(controleur));

        nomJ1.addFocusListener(new FocusAdapter() {
            final String s = nomJ1.getText();

            @Override
            public void focusGained(FocusEvent e) {
                if (nomJ1.getText().equals(s)) {
                    nomJ1.setText("");
                    nomJ1.setForeground(new Color(173, 216, 230));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomJ1.getText().isEmpty()) {
                    nomJ1.setForeground(new Color(173, 216, 230));
                    nomJ1.setText(s);
                }
            }
        });
        nomJ2.addFocusListener(new FocusAdapter() {
            final String s = nomJ2.getText();

            @Override
            public void focusGained(FocusEvent e) {
                if (nomJ2.getText().equals(s)) {
                    nomJ2.setText("");
                    nomJ2.setForeground(new Color(173, 216, 230));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomJ2.getText().isEmpty()) {
                    nomJ2.setForeground(new Color(173, 216, 230));
                    nomJ2.setText(s);
                }
            }
        });
    }

    private void setupUI() {
        MenuSaisies.setLayout(new BoxLayout(MenuSaisies, BoxLayout.Y_AXIS)); // Utilisation du BoxLayout avec orientation verticale
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 30, 0, 30); // Espacement des composants

        // Joueur 1
        constraints.gridx = 0;
        constraints.gridy = 0;

        nomJ1.setDropMode(DropMode.USE_SELECTION);
        nomJ1.setFocusCycleRoot(false);
        nomJ1.setFocusTraversalPolicyProvider(false);
        nomJ1.setFocusable(true);
        nomJ1.setText("Nom de l'attaquant");
        nomJ1.setVisible(true);
        nomJ1.setColumns(14);
        nomJ1.setMaximumSize(new Dimension(-1, 20));

        DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("Humain");
        defaultComboBoxModel1.addElement("IA Facile");
        defaultComboBoxModel1.addElement("IA Moyenne");
        defaultComboBoxModel1.addElement("IA Difficile");
        typeJ1.setModel(defaultComboBoxModel1);

        Joueur1 = createJoueurPanel(true, "Attaquant", nomJ1, typeJ1);

        add(Joueur1, constraints);

        add(Box.createVerticalStrut(5)); // Espace vertical

        // Joueur 2
        constraints.gridy = 1;

        nomJ2.setDropMode(DropMode.USE_SELECTION);
        nomJ2.setFocusCycleRoot(false);
        nomJ2.setFocusTraversalPolicyProvider(false);
        nomJ2.setFocusable(true);
        nomJ2.setText("Nom du défenseur");
        nomJ2.setVisible(true);
        nomJ2.setColumns(14);
        nomJ2.setMaximumSize(new Dimension(-1, 20));

        DefaultComboBoxModel<String> defaultComboBoxModel3 = new DefaultComboBoxModel<>();
        defaultComboBoxModel3.addElement("Humain");
        defaultComboBoxModel3.addElement("IA Facile");
        defaultComboBoxModel3.addElement("IA Moyenne");
        defaultComboBoxModel3.addElement("IA Difficile");
        defaultComboBoxModel3.setSelectedItem("IA Facile");// Sélectionner "IA Facile" comme valeur par défaut
        typeJ2.setModel(defaultComboBoxModel3);

        Joueur2 = createJoueurPanel(false, "Défenseur", nomJ2, typeJ2);

        add(Joueur2, constraints);

        add(Box.createVerticalStrut(5)); // Espace vertical

        Boutons.add(jouerButton);
        Boutons.add(menuPrincipalButton);

        Boutons.setMaximumSize(new Dimension(441, 30));

        // Boutons
        constraints.gridy = 2;
        add(Boutons, constraints);

        add(Box.createVerticalStrut(80)); // Espace vertical

        // Quitter
        quitter = new CButton("Quitter");
        quitter.addActionListener((e) -> {
            controleur.toClose();
            // Créer une nouvelle fenêtre pour tester le texte d'un jlabel
            /*JFrame frame = new JFrame("JLabel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 300);
            // Ajout d'un jpanel avec deux jlabel
            JPanel panel = new JPanel();
            // GridLayout
            panel.setLayout(new GridLayout(2, 1));
            panel.setOpaque(false);
            // Ajout des deux jlabel
            JLabel label = new JLabel("Hello World");
            label.setFont(new Font("Serif", Font.BOLD, 48));
            JLabel label22 = new JLabel("Goodbye World !");
            label22.setFont(new Font("Serif", Font.BOLD, 48));
            panel.add(label);
            panel.add(label22);
            // Ajout du jpanel à la fenêtre
            frame.getContentPane().add(panel);
            frame.setVisible(true);*/
        });

        BoutonsQuitter.add(quitter);
        BoutonsQuitter.setMaximumSize(new Dimension(441, 30));


        // Bouton Quitter
        constraints.gridy = 3;
        add(BoutonsQuitter, constraints);

        add(Box.createVerticalStrut(20)); // Espace vertical
    }

    private JPanel createJoueurPanel(boolean reverse, String label, JTextField nomJoueur, JComboBox typeJoueur) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 0, 10); // Espacement des composants

        // Ajout d'un bord à gauche
        panel.add(Box.createRigidArea(new Dimension(30, 0)), constraints);

        // Label
        // Chargement de l'image à partir d'un fichier
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(reverse ? "/Resources/assets/PN.png" : "/Resources/assets/PB.png"));

        Image image = imageIcon.getImage();

        // Redimensionner l'image en utilisant les dimensions souhaitées
        Image resizedImage = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // Créer un nouvel ImageIcon avec l'image redimensionnée
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

        constraints.gridx = 1;
        JLabel labelJoueur = new JLabel(resizedImageIcon);
        panel.add(labelJoueur, constraints);

        // Espacement horizontal
        constraints.gridx = 2;
        panel.add(Box.createRigidArea(new Dimension(10, 0)), constraints);

        // Nom du joueur
        constraints.gridx = 3;

        nomJoueur.setMaximumSize(new Dimension(100, 20)); // Bloquer la hauteur
        panel.add(nomJoueur, constraints);

        // Espacement horizontal
        constraints.gridx = 4;
        panel.add(Box.createRigidArea(new Dimension(10, 0)), constraints);

        // Type de joueur
        constraints.gridx = 5;
        panel.add(typeJoueur, constraints);

        // Espacement à droite
        constraints.gridx = 6;
        panel.add(Box.createRigidArea(new Dimension(30, 0)), constraints);

        return panel;
    }

    private void actionBoutonJouer(CollecteurEvenements c) {
        TypeJoueur[] types = new TypeJoueur[2];
        switch (typeJ1.getSelectedIndex()) {
            case 0:
                types[0] = TypeJoueur.HUMAIN;
                break;
            case 1:
                types[0] = TypeJoueur.IA_FACILE;
                break;
            case 2:
                types[0] = TypeJoueur.IA_MOYEN;
                break;
            case 3:
                types[0] = TypeJoueur.IA_DIFFICILE;
                break;
            default:
                //Par défault on fera jouer HUMAIN contre IA mais gérer dans le controleurMédiateur
                break;
        }
        switch (typeJ2.getSelectedIndex()) {
            case 0:
                types[1] = TypeJoueur.HUMAIN;
                break;
            case 1:
                types[1] = TypeJoueur.IA_FACILE;
                break;
            case 2:
                types[1] = TypeJoueur.IA_MOYEN;
                break;
            case 3:
                types[1] = TypeJoueur.IA_DIFFICILE;
                break;
            default:
                break;
        }
        c.nouvellePartie(nomJ1.getText(), types[0], TypePion.ATTAQUANT, nomJ2.getText(), types[1], TypePion.DEFENSEUR);
        c.afficherJeu();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Affichage de l'image d'arrière plan
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        int width = (int) (getWidth() * 0.25);
        logoHeight = (title.getHeight(null) * width) / title.getWidth(null);

        int x = ((getWidth() - width) / 2) - 5;

        g.drawImage(title, x, 35, width, logoHeight, null);
        if (called == 0) {
            setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            called = 1;
        }
    }

    // Méthode pour réinitialiser les champs de texte
    /*
    public void resetTexte() {
        nomJ1.setText("Joueur 1");
        nomJ2.setText("Joueur 2");
    }

     */
}
