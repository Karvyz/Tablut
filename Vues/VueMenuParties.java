package Vues;

import Vues.JComposants.CButton;

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
    private JButton menuPrincipalButton;
    private JButton loadButton;
    private JButton backButton;
    private JButton deleteButton;
    private CollecteurEvenements controleur;

    Image background;

    Vues vues;

    public VueMenuParties(CollecteurEvenements controleur) {
        this.controleur = controleur;

        menuPrincipalButton = new CButton();
        loadButton = new CButton().vert();
        deleteButton = new CButton().rouge();

        // Chargement des assets
        background = Imager.getImageBuffer("logo.png");

        initializeComponents();
    }

    private void initializeComponents() {
        //setLayout(new GridLayout(8, 3, -1, -1));
        setLayout(new GridBagLayout());

        // Espace vertical
        Box verticalSpace0 = Box.createVerticalBox();
        verticalSpace0.add(Box.createVerticalStrut(2));
        GridBagConstraints gspacer0 = new GridBagConstraints();
        gspacer0.gridx = 1;
        gspacer0.gridy = 0;
        gspacer0.gridwidth = 3;
        gspacer0.gridheight = 1;
        gspacer0.fill = GridBagConstraints.HORIZONTAL;
        gspacer0.anchor = GridBagConstraints.CENTER;
        add(verticalSpace0, gspacer0);

        // Texte "Choisir une partie à charger ou à supprimer :" (label1)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        final JLabel label1 = new JLabel();
        Font label1Font = new Font("Arial", Font.BOLD, 20);
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(0x000000));
        label1.setText("Choisir une partie à charger ou à supprimer :");
        add(label1, gbc);

        // Espaces vertical
        Box verticalSpace1 = Box.createVerticalBox();
        verticalSpace1.add(Box.createVerticalStrut(100));
        GridBagConstraints gspacer1 = new GridBagConstraints();
        gspacer1.gridx = 1;
        gspacer1.gridy = 2;
        gspacer1.gridwidth = 3;
        gspacer1.gridheight = 1;
        gspacer1.fill = GridBagConstraints.VERTICAL;
        gspacer1.anchor = GridBagConstraints.CENTER;
        add(verticalSpace1, gspacer1);

        // Espaces horizontal
        Box horizontalSpace1 = Box.createHorizontalBox();
        horizontalSpace1.add(Box.createHorizontalStrut(20));
        GridBagConstraints gspacer2 = new GridBagConstraints();
        gspacer2.gridx = 0;
        gspacer2.gridy = 3;
        gspacer2.gridwidth = 1;
        gspacer2.gridheight = 1;
        gspacer2.fill = GridBagConstraints.HORIZONTAL;
        //gspacer2.anchor = GridBagConstraints.CENTER;
        add(horizontalSpace1, gspacer2);


        // Liste des parties sauvegardées (fileList)
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setForeground(new Color(-16777216));
        //fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        fileList.setFont(new Font("Arial", Font.PLAIN, 18)); // Changez la taille du texte des fichiers ici
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setVisibleRowCount(5);
        //fileList.setOpaque(false);
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

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JScrollPane scrollPane = new JScrollPane(fileList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(new Color(-16777216));
        scrollPane.setOpaque(false);
        add(scrollPane, gbc);

        // Espaces horizontal
        Box horizontalSpace2 = Box.createHorizontalBox();
        horizontalSpace2.add(Box.createHorizontalStrut(20));
        GridBagConstraints gspacer3 = new GridBagConstraints();
        gspacer3.gridx = 2;
        gspacer3.gridy = 3;
        gspacer3.gridwidth = 1;
        gspacer3.gridheight = 1;
        gspacer3.fill = GridBagConstraints.HORIZONTAL;
        gspacer3.anchor = GridBagConstraints.CENTER;
        add(horizontalSpace2, gspacer3);

        // Espaces vertical
        Box verticalSpace2 = Box.createVerticalBox();
        verticalSpace2.add(Box.createVerticalStrut(100));
        GridBagConstraints gspacer4 = new GridBagConstraints();
        gspacer4.gridx = 1;
        gspacer4.gridy = 4;
        gspacer4.gridwidth = 3;
        gspacer4.gridheight = 1;
        gspacer4.fill = GridBagConstraints.VERTICAL;
        gspacer4.anchor = GridBagConstraints.CENTER;
        add(verticalSpace2, gspacer4);

        // Panel pour les boutons "Charger la partie" et "Supprimer la partie"
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2, -1, -1));
        panel1.setOpaque(false);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panel1, gbc);

        loadButton.setFocusable(true);
        loadButton.setHideActionText(false);
        loadButton.setHorizontalTextPosition(SwingConstants.LEFT);
        loadButton.setText("Charger Partie");
        //loadButton.setPreferredSize(new Dimension(100, 50));

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile != null) {
                    //System.out.println(controleur);
                    if (controleur.chargerPartie("Resources/save/" + selectedFile) == false){
                        System.out.println("Ligne 177 de VueMenuParties ");
                    }
                    controleur.fixeJeu(controleur.jeu().getJeu());
                    controleur.afficherJeu();
                    controleur.restaurePartie();
                }
            }
        });

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(loadButton, gbc);
        //add(loadButton, gbc);

        deleteButton.setHideActionText(false);
        deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
        deleteButton.setText("Supprimer Partie");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile != null) {
                    // Affiche une boîte de dialogue de confirmation
                    int response = JOptionPane.showConfirmDialog(null,
                            "Êtes-vous sûr de vouloir supprimer "+selectedFile+" ?",
                            "Confirmer la suppression",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        // Si l'utilisateur clique sur Oui, supprime le fichier
                        File file = new File("Resources/save/" + selectedFile);
                        if (file.delete()) {
                            // Le fichier a été supprimé avec succès, rafraîchir la liste
                            refreshFileList();
                        } else {
                            // Une erreur s'est produite lors de la suppression du fichier
                            System.out.println("Une erreur s'est produite lors de la suppression du fichier.");
                        }
                    }
                }
            }
        });

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(deleteButton, gbc);

        // Espace vertical
        Box verticalSpace3 = Box.createVerticalBox();
        verticalSpace3.add(Box.createVerticalStrut(100));
        GridBagConstraints gspacer5 = new GridBagConstraints();
        gspacer5.gridx = 1;
        gspacer5.gridy = 6;
        gspacer5.gridwidth = 3;
        gspacer5.gridheight = 1;
        gspacer5.fill = GridBagConstraints.VERTICAL;
        gspacer5.anchor = GridBagConstraints.CENTER;
        add(verticalSpace3, gspacer5);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);
        menuPrincipalButton.setText("Menu Principal");
        menuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.afficherMenuPrincipal();
            }
        });
        add(menuPrincipalButton, gbc);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Affichage de l'image d'arrière plan
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}