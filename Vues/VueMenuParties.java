package Vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;

public class VueMenuParties extends JPanel {
    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private JButton loadButton;
    private JButton backButton;
    private CollecteurEvenements controleur;

    Vues vues;

    public VueMenuParties(CollecteurEvenements controleur) {
        this.controleur = controleur;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setFont(new Font("Arial", Font.PLAIN, 18)); // Changez la taille du texte des fichiers ici
        // Ajout de l'écouteur de double-clic ici
        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {  // Double-clic
                    // Trouver l'index de l'élément sélectionné
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        // Simuler le clic sur le bouton de chargement
                        loadButton.doClick();
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(fileList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        loadButton = new JButton("Charger la partie");
        loadButton.setMaximumSize(new Dimension(150, 30)); // Changez la taille du bouton "Charger la partie" ici
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile != null) {
                    //System.out.println(controleur);
                    if (controleur.chargerPartie("Resources/save/" + selectedFile) == false){
                        System.out.println("Ligne 43 de VueMenuParties ");
                    }
                    controleur.fixeJeu(controleur.jeu().getJeu());
                    //System.out.println(controleur);
                    controleur.afficherJeu();
                    controleur.restaurePartie();
                }
            }
        });
        buttonPanel.add(loadButton);

        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Ajouter un espacement entre les boutons

        backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.afficherMenuPrincipal();
            }
        });
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        refreshFileList();
    }

    public void refreshFileList() {
        File saveDir = new File("Resources/save/");
        if (saveDir.exists() && saveDir.isDirectory()) {
            File[] saveFiles = saveDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".save");
                }
            });

            fileListModel.clear();
            if (saveFiles != null) {
                for (File saveFile : saveFiles) {
                    fileListModel.addElement(saveFile.getName());
                }
            }
        }
    }
}



/*package Vues;

//import Global.Sauvegarde;
import Vues.JComposants.CButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class VueMenuParties extends JPanel {

    CollecteurEvenements controleur;
    private JButton chargerPartie;
    private JButton supprimerPartie;
    private JButton menuPrincipalButton;
    private JPanel MenuParties;
    private JScrollPane Allparties;
    private JList list1;
    Image t;
    final String[] selected = new String[1];

    public VueMenuParties(CollecteurEvenements c) {
        controleur = c;

        menuPrincipalButton = new CButton();
        chargerPartie = new CButton().vert();
        supprimerPartie = new CButton().rouge();
        list1 = new JList();

        // Chargement des assets
        //t = Imager.getImageBuffer("assets/topbanner.png");

        MenuParties = this;

        $$$setupUI$$$();

        menuPrincipalButton.addActionListener((e) -> controleur.afficherMenuPrincipal());

        chargerPartie.addActionListener((e) -> {
            System.out.println("Chargement de : " + selected[0]);
            *//*controleur.jeu().chargerPartie("sauvegarde.save");*//*
        });

        supprimerPartie.addActionListener((e) -> {
            System.out.println("Suppression de : " + selected[0]);
            //Sauvegarde.supprimer(selected[0]);
        });
    }

    *//**
 * Method generated by IntelliJ IDEA GUI Designer
 * >>> IMPORTANT!! <<<
 * DO NOT edit this method OR call it in your code!
 *
 * @noinspection ALL
 * @noinspection ALL
 * @noinspection ALL
 *//*
    private void $$$setupUI$$$() {
        createUIComponents();
        MenuParties = new JPanel();
        MenuParties.setLayout(new GridLayout(8, 3));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2));
        panel1.setOpaque(false);
        MenuParties.add(panel1);

        JButton supprimerPartie = new JButton("Supprimer Partie");
        supprimerPartie.setBackground(new Color(-3949375));
        supprimerPartie.setForeground(new Color(-54016));
        supprimerPartie.setHideActionText(false);
        supprimerPartie.setHorizontalTextPosition(SwingConstants.CENTER);
        panel1.add(supprimerPartie);

        JButton chargerPartie = new JButton("Charger Partie");
        chargerPartie.setBackground(new Color(-3949375));
        chargerPartie.setFocusable(true);
        chargerPartie.setForeground(new Color(-16744180));
        chargerPartie.setHideActionText(false);
        chargerPartie.setHorizontalTextPosition(SwingConstants.CENTER);
        panel1.add(chargerPartie);

        JScrollPane Allparties = new JScrollPane();
        Allparties.setBackground(new Color(-16777216));
        Allparties.setForeground(new Color(-16777216));
        Allparties.setOpaque(false);
        MenuParties.add(Allparties);

        JList list1 = new JList();
        Font list1Font = new Font(null, Font.PLAIN, 14);
        list1.setFont(list1Font);
        list1.setForeground(new Color(-16777216));
        list1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel<>();
        defaultListModel1.addElement("\"Essai\" 03/04/2022   Joueur1 : \"David\", Joueur2 : \"Tom\"");
        defaultListModel1.addElement("\"Essai 2\" 04/04/2022   Joueur1 : \"Léonard\", Joueur2 : \"Victoria\"");
        defaultListModel1.addElement("\"Essai 3\" 05/04/2022   Joueur1 : \"Tata\", Joueur2 : \"Toto\"");
        defaultListModel1.addElement("\"Essai 4\" 06/04/2022   Joueur1 : \"Toto\", Joueur2 : IA FAcile \"Alpha\"");
        defaultListModel1.addElement("\"Essai 5\" 07/04/2022   Joueur1 : \"Tata\", Joueur2 : \"Toto\"");
        list1.setModel(defaultListModel1);
        list1.setOpaque(false);
        list1.setPreferredSize(new Dimension(300, 357));
        list1.setSelectionForeground(new Color(-9541531));
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setVisibleRowCount(10);
        Allparties.setViewportView(list1);

        *//*
        MenuParties.add(new JPanel());
        MenuParties.add(new JPanel());
        MenuParties.add(new JPanel());
        MenuParties.add(new JPanel());
        MenuParties.add(new JPanel());
        MenuParties.add(new JPanel());
        MenuParties.add(new JPanel());

         *//*
    }

    *//**
 * @noinspection ALL
 *//*
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    *//**
 * @noinspection ALL
 *//*
    public JComponent $$$getRootComponent$$$() {
        return MenuParties;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = new Color(255, 140, 85);
        Color color2 = new Color(255, 120, 105);
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

        if (t != null) {
            int width = (int) (getWidth() * 1.7);
            int height = (t.getHeight(null) * width) / t.getWidth(null);
            g.drawImage(t, 0, 0, width, height, null);
        }

        // Création du model avec les string que l'on veut
        final DefaultListModel defaultListModel2 = new DefaultListModel();
        //defaultListModel2.addElement("Essai 1");

        // Partie récupération des noms de parties sauvegardées
        *//*
        if (Sauvegarde.liste() == null) {
            defaultListModel2.addElement("");
        } else {
            for (String s : Sauvegarde.liste()) {
                defaultListModel2.addElement(s);
            }
        }

        // Assignation du modele créé à la liste courante
        list1.setModel(defaultListModel2);
        *//*

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                selected[0] = String.valueOf(list1.getSelectedValue());
            }
        });
    }
}*/
