package Vues;

import Modele.TypeJoueur;
import Modele.TypePion;
import Vues.JComposants.CButton;
import Vues.JComposants.CComboxBox;
import Vues.JComposants.CTextField;

import javax.swing.*;
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void setupUI() {
        createUIComponents();
        MenuSaisies.setLayout(new GridLayout(4, 1, 0, 120));
        MenuSaisies.setMaximumSize(new Dimension(441, 110));
        MenuSaisies.setMinimumSize(new Dimension(441, 60));
        MenuSaisies.setPreferredSize(new Dimension(441, 110));

        Joueur1 = new JPanel();
        Joueur1.setLayout(new GridLayout(1, 5, 50, 0));
        Joueur1.setOpaque(false);
        Joueur1.setMinimumSize(new Dimension(441, -1));

        //Joueur1.setMaximumSize(new Dimension(441, 110/3));

        // Ajouter un bord à gauche
        Joueur1.add(Box.createRigidArea(new Dimension(30, 0)));

        JLabel label1 = new JLabel(" Attaquant");
        System.out.println(label1.getFont());
        label1.setFont(new Font(label1.getFont().getName(), Font.BOLD, 36));
        System.out.println(label1.getFont());
        Joueur1.add(label1);

        nomJ1.setDropMode(DropMode.USE_SELECTION);
        nomJ1.setFocusCycleRoot(false);
        nomJ1.setFocusTraversalPolicyProvider(false);
        nomJ1.setFocusable(true);
        nomJ1.setText("Joueur 1");
        nomJ1.setVisible(true);
        //nomJ1.setOpaque(false);
        Joueur1.add(nomJ1);
        DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("Humain");
        defaultComboBoxModel1.addElement("IA Facile");
        defaultComboBoxModel1.addElement("IA Moyenne");
        defaultComboBoxModel1.addElement("IA Difficile");
        typeJ1.setModel(defaultComboBoxModel1);
        Joueur1.add(typeJ1);

        Joueur1.add(Box.createRigidArea(new Dimension(30, 0)));

        MenuSaisies.add(Joueur1);

        Joueur2 = new JPanel();
        Joueur2.setLayout(new GridLayout(1, 5, 50, 0));
        Joueur2.setOpaque(false);

        //Joueur2.setMaximumSize(new Dimension(441, 110/3));

        Joueur2.add(Box.createRigidArea(new Dimension(30, 0)));

        JLabel label2 = new JLabel(" Défenseur");
        label2.setFont(label2.getFont().deriveFont(Font.BOLD));
        Joueur2.add(label2);

        nomJ2.setDropMode(DropMode.USE_SELECTION);
        nomJ2.setFocusCycleRoot(false);
        nomJ2.setFocusTraversalPolicyProvider(false);
        nomJ2.setFocusable(true);
        nomJ2.setText("Joueur 2");
        nomJ2.setVisible(true);
        Joueur2.add(nomJ2);

        DefaultComboBoxModel<String> defaultComboBoxModel3 = new DefaultComboBoxModel<>();
        defaultComboBoxModel3.addElement("Humain");
        defaultComboBoxModel3.addElement("IA Facile");
        defaultComboBoxModel3.addElement("IA Moyenne");
        defaultComboBoxModel3.addElement("IA Difficile");
        defaultComboBoxModel3.setSelectedItem("IA Facile");// Sélectionner "IA Facile" comme valeur par défaut
        typeJ2.setModel(defaultComboBoxModel3);
        Joueur2.add(typeJ2);

        Joueur2.add(Box.createRigidArea(new Dimension(30, 0)));

        MenuSaisies.add(Joueur2);

        Boutons.add(jouerButton);
        Boutons.add(menuPrincipalButton);

        Boutons.setMaximumSize(new Dimension(441, 30));

        MenuSaisies.add(Boutons);

        // Quitter
        quitter = new CButton("Quitter");
        quitter.addActionListener((e) -> {
            controleur.toClose();
        });

        BoutonsQuitter.add(quitter);
        BoutonsQuitter.setMaximumSize(new Dimension(441, 30));
        MenuSaisies.add(BoutonsQuitter);

    }

    private void actionBoutonJouer(CollecteurEvenements c) {
        TypeJoueur[] types = new TypeJoueur[2];
        switch(typeJ1.getSelectedIndex()) {
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
}
