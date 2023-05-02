package Vues;

import Modele.TypeJoueur;
import Vues.JComposants.CButton;
import Vues.JComposants.CComboxBox;
import Vues.JComposants.CTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Type;

public class VueMenuSaisies extends JPanel {

    CollecteurEvenements controleur;

    private JPanel MenuSaisies;
    private JTextField nomJ1;
    private JComboBox typeJ1;
    private JButton jouerButton;
    private JButton menuPrincipalButton;
    private JPanel Boutons;
    private JPanel Joueur2;
    private JPanel Joueur1;
    private JTextField nomJ2;
    private JComboBox typeJ2;
    private JLabel JoueurCommence;
    private JComboBox comboBox1;
    int logoHeight;
    Image t;
    Image vs;
    int called = 0;

    public VueMenuSaisies(CollecteurEvenements c) {
        controleur = c;

        typeJ1 = new CComboxBox();
        typeJ2 = new CComboxBox();
        menuPrincipalButton = new CButton();
        jouerButton = new CButton().blanc();
        comboBox1 = new CComboxBox();
        nomJ1 = new CTextField();
        nomJ2 = new CTextField();
        Boutons = new JPanel();

        setBackground(Color.PINK);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Chargement des assets
        //t = Imager.getImageBuffer("topbanner.png");
        vs = Imager.getImageBuffer("assets/VS.png");

        MenuSaisies = this;
        setupUI();

        for (Component co : MenuSaisies.getComponents()) {
            if (co instanceof JPanel) {
                for (Component coo : ((JPanel) co).getComponents()) {
                    if (coo instanceof CComboxBox) ((CComboxBox) coo).setEditable(true);
                    else if (coo instanceof JLabel) {
                        coo.setFont(new Font("Arial", Font.PLAIN, 14));
                        coo.setForeground(Color.WHITE);
                    }
                }
            }

        }

        menuPrincipalButton.addActionListener((e) -> controleur.afficherMenuPrincipal());

        jouerButton.addActionListener((e) -> {
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
                    //types[0] = TypeJoueur.HUMAIN;
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
                    //types[1] = TypeJoueur.HUMAIN;
                    break;
            }
            c.nouvellePartie(
                    nomJ1.getText(),
                    types[0],
                    // --
                    nomJ2.getText(),
                    types[1]
            );
            c.afficherJeu();
        });

        nomJ1.addFocusListener(new FocusAdapter() {
            final String s = nomJ1.getText();

            @Override
            public void focusGained(FocusEvent e) {
                if (nomJ1.getText().equals(s)) {
                    nomJ1.setText("");
                    nomJ1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomJ1.getText().isEmpty()) {
                    nomJ1.setForeground(Color.BLACK);
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
                    nomJ2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nomJ2.getText().isEmpty()) {
                    nomJ2.setForeground(Color.BLACK);
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
        MenuSaisies.setLayout(new GridLayout(4, 1));
        MenuSaisies.setMaximumSize(new Dimension(441, 110));
        MenuSaisies.setMinimumSize(new Dimension(441, 110));
        MenuSaisies.setPreferredSize(new Dimension(441, 110));

        Joueur1 = new JPanel();
        Joueur1.setLayout(new GridLayout(1, 4));
        Joueur1.setBackground(new Color(-14276823));
        Joueur1.setOpaque(false);

        Joueur1.setMaximumSize(new Dimension(300, 110/3));

        MenuSaisies.add(Joueur1);

        JLabel label1 = new JLabel(" Joueur 1");
        Joueur1.add(label1);

        nomJ1.setDropMode(DropMode.USE_SELECTION);
        nomJ1.setFocusCycleRoot(false);
        nomJ1.setFocusTraversalPolicyProvider(false);
        nomJ1.setFocusable(true);
        nomJ1.setText("J1");
        nomJ1.setVisible(true);
        Joueur1.add(nomJ1);

        DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        defaultComboBoxModel1.addElement("Humain");
        defaultComboBoxModel1.addElement("IA Facile");
        defaultComboBoxModel1.addElement("IA Moyenne");
        defaultComboBoxModel1.addElement("IA Difficile");
        typeJ1.setModel(defaultComboBoxModel1);
        Joueur1.add(typeJ1);

        Joueur2 = new JPanel();
        Joueur2.setLayout(new GridLayout(1, 4));
        Joueur2.setBackground(new Color(-14276823));
        Joueur2.setOpaque(false);

        Joueur2.setMaximumSize(new Dimension(441, 110/3));

        MenuSaisies.add(Joueur2);

        JLabel label2 = new JLabel(" Joueur 2");
        Joueur2.add(label2);

        nomJ2.setDropMode(DropMode.USE_SELECTION);
        nomJ2.setFocusCycleRoot(false);
        nomJ2.setFocusTraversalPolicyProvider(false);
        nomJ2.setFocusable(true);
        nomJ2.setText("J2");
        nomJ2.setVisible(true);
        Joueur2.add(nomJ2);

        DefaultComboBoxModel<String> defaultComboBoxModel3 = new DefaultComboBoxModel<>();
        defaultComboBoxModel3.addElement("Humain");
        defaultComboBoxModel3.addElement("IA Facile");
        defaultComboBoxModel3.addElement("IA Moyenne");
        defaultComboBoxModel3.addElement("IA Difficile");
        typeJ2.setModel(defaultComboBoxModel3);
        Joueur2.add(typeJ2);

        Boutons.add(jouerButton);
        Boutons.add(menuPrincipalButton);

        Boutons.setMaximumSize(new Dimension(441, 110/3));

        MenuSaisies.add(Boutons);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        //Color color1 = new Color(255, 140, 85);
        //Color color2 = new Color(255, 120, 105);
        //GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        //g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

        int width = (int) (getWidth() * 0.25);
        logoHeight = (vs.getHeight(null) * width) / vs.getWidth(null);

        int x = (int) ((getWidth() - width) / 2) - 5;

        g.drawImage(vs, x, 35, width, logoHeight, null);
        if (called == 0) {
            setBorder(BorderFactory.createEmptyBorder(logoHeight + 50, 0, 0, 0));
            called = 1;
        }
    }
}
