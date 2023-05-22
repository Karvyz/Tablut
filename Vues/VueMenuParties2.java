package Vues;

import Modele.Data_Niveau;
import Vues.JComposants.CButton;
import Vues.JComposants.CJScrollBar;
import Vues.JComposants.CLabel;
import Vues.JComposants.CListCellRenderer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class VueMenuParties2 extends JPanel {
    private JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    private JButton menuPrincipalButton;
    private JButton loadButton;
    private JButton backButton;
    private JButton deleteButton;
    private CollecteurEvenements controleur;

    JPanel infoPanel;
    CLabel label1, label2, label3, label4;
    String attaquant, defenseur;

    Image background;

    Vues vues;

    public VueMenuParties2(CollecteurEvenements controleur) {
        this.controleur = controleur;

        menuPrincipalButton = new CButton().blanc();
        menuPrincipalButton.setBackground(new Color(0xD9D9D9));
        loadButton = new CButton().vert();
        deleteButton = new CButton().rouge();

        // Chargement des assets
        background = Imager.getImageBuffer("logo.png");

        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());

        // Espace vertical
        Box verticalSpace0 = Box.createVerticalBox();
        verticalSpace0.add(Box.createVerticalStrut(200));
        GridBagConstraints gspacer0 = new GridBagConstraints();
        gspacer0.gridx = 0;
        gspacer0.gridy = 0;
        gspacer0.gridwidth = 4;
        gspacer0.gridheight = 1;
        gspacer0.fill = GridBagConstraints.VERTICAL;
        gspacer0.anchor = GridBagConstraints.CENTER;
        add(verticalSpace0, gspacer0);

        // Liste des parties sauvegardées (fileList)
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setCellRenderer(new CListCellRenderer());
        //fileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        fileList.setFont(new Font("Courier", Font.PLAIN, 15)); // Changez la taille du texte des fichiers ici
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setVisibleRowCount(5);
        fileList.setOpaque(false);
        // Ajout de l'écouteur de double-clic ici
        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                refresh();
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {  // Double-clic
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        // Simuler le clic sur le bouton de chargement
                        loadButton.doClick();
                    }
                }
            }
        });

        // Permet de selectionné/supprimé grace au clavier
        fileList.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_DELETE) {  // Touche Suppr
                    deleteButton.doClick();
                } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  // Touche Entrée
                    loadButton.doClick();
                } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {  // Touche Échap
                    menuPrincipalButton.doClick();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(fileList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        // Custom JScrollBar
        scrollPane.setVerticalScrollBar(new CJScrollBar());
        scrollPane.setPreferredSize(new Dimension(430, 100));
        scrollPane.setMaximumSize(new Dimension(450, -1));

        // Définition de l'espacement entre les lignes
        int lineSpace = 10;

        // Création d'un EmptyBorder pour l'espacement entre les lignes
        Border lineBorder = new EmptyBorder(lineSpace, 0, 0, 0);

        // Création d'un MatteBorder pour le bord supérieur distinct
        Color borderColor = Color.WHITE; // Couleur du bord supérieur
        int topBorderThickness = 1; // Épaisseur du bord supérieur en pixels
        Border topBorder = BorderFactory.createMatteBorder(topBorderThickness, 0, 0, 0, borderColor);

        // Combinaison des bordures
        Border compoundBorder = BorderFactory.createCompoundBorder(topBorder, lineBorder);

        // Définition du Border pour le JScrollPane
        scrollPane.setBorder(compoundBorder);

        // Ajouter le scrollPane dans un JPanel transparent avec le titre
        JPanel scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout(new GridBagLayout());
        scrollPanePanel.setPreferredSize(new Dimension(430, 80));
        scrollPanePanel.setMaximumSize(new Dimension(450, 1000));
        scrollPanePanel.setBackground(new Color(0x99000000, true));
        //scrollPanePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        CLabel title = new CLabel("Parties sauvegardées");
        title.setFont(new Font("Poppins", Font.BOLD, 20));
        title.setForeground(new Color(0xFFFFFF));
        scrollPanePanel.add(title, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        scrollPane.setOpaque(false);

        scrollPanePanel.add(scrollPane, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 7;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;
        //add(scrollPane, gbc);
        add(scrollPanePanel, gbc);

        // Colonne vide
        Box verticalSpace1 = Box.createVerticalBox();
        verticalSpace1.add(Box.createVerticalStrut(10));
        GridBagConstraints gspacer1 = new GridBagConstraints();
        gspacer1.gridx = 2;
        gspacer1.gridy = 1;
        gspacer1.gridwidth = 1;
        gspacer1.gridheight = 7;
        gspacer1.weighty = 0.25;
        gspacer1.fill = GridBagConstraints.VERTICAL;
        gspacer1.anchor = GridBagConstraints.CENTER;
        add(verticalSpace1, gspacer1);

        // JPanel contenant les infos de la partie selectionnée
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setMinimumSize(new Dimension(350, 100));
        infoPanel.setPreferredSize(new Dimension(350, 100));
        infoPanel.setMaximumSize(new Dimension(350, 100));
        infoPanel.setBackground(new Color(0x99000000, true));
        infoPanel.setVisible(true);
        //infoPanel.setOpaque(false);

        // Nom de la partie (label1)
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridwidth = 1;
        gbc1.gridheight = 1;
        gbc1.fill = GridBagConstraints.NONE;
        gbc1.anchor = GridBagConstraints.CENTER;
        label1 = new CLabel("Aucune partie selectionnée").jaune();
        label1.setOpaque(false);
        infoPanel.add(label1, gbc1);

        // Date de la partie
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.gridwidth = 1;
        gbc2.gridheight = 1;
        gbc2.fill = GridBagConstraints.NONE;
        gbc2.anchor = GridBagConstraints.CENTER;
        label2 = new CLabel("").jaune();
        label2.setOpaque(false);
        infoPanel.add(label2, gbc2);

        // Nom de l'attaquant
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.anchor = GridBagConstraints.WEST;
        label3 = new CLabel("").jaune();
        attaquant = "";
        label3.setText(attaquant);
        label3.setOpaque(false);
        infoPanel.add(label3, gbc3);

        // Nom du défenseur
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 0;
        gbc4.gridy = 3;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.anchor = GridBagConstraints.WEST;
        label4 = new CLabel("").jaune();
        defenseur = "";
        label4.setText(defenseur);
        label4.setOpaque(false);
        infoPanel.add(label4, gbc4);

        // Ajout du JPanel infoPanel
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 3;
        gbc5.gridy = 1;
        gbc5.gridwidth = 1;
        gbc5.gridheight = 2;
        gbc5.weightx = 0.25;
        gbc5.fill = GridBagConstraints.NONE;
        gbc5.anchor = GridBagConstraints.CENTER;
        add(infoPanel, gbc5);


        // Bouton "Charger"
        GridBagConstraints gbc6 = new GridBagConstraints();
        gbc6.gridx = 3;
        gbc6.gridy = 3;
        gbc6.gridwidth = 1;
        gbc6.gridheight = 1;
        gbc6.fill = GridBagConstraints.NONE;
        gbc6.anchor = GridBagConstraints.CENTER;
        gbc6.insets = new Insets(10, 0, 5, 0);

        loadButton.setFocusable(true);
        loadButton.setHideActionText(false);
        loadButton.setHorizontalTextPosition(SwingConstants.LEFT);
        loadButton.setText("Charger Partie");
        loadButton.setMinimumSize(new Dimension(250, 37));
        loadButton.setPreferredSize(new Dimension(250, 37));
        loadButton.setMaximumSize(new Dimension(250, 37));

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                selectedFile = selectedFile.substring(0, selectedFile.lastIndexOf("e") + 1);
                if (selectedFile != null) {
                    //System.out.println(controleur);
                    if (controleur.chargerPartie("Resources/save/" + selectedFile) == false) {
                        System.out.println("Ligne 177 de VueMenuParties ");
                    }
                    controleur.fixeJeu(controleur.jeu().getJeu());
                    controleur.afficherJeu();
                    controleur.restaurePartie();
                }
            }
        });

        add(loadButton, gbc6);

        // Bouton "Supprimer"
        GridBagConstraints gbc7 = new GridBagConstraints();
        gbc7.gridx = 3;
        gbc7.gridy = 4;
        gbc7.gridwidth = 1;
        gbc7.gridheight = 1;
        gbc7.fill = GridBagConstraints.NONE;
        gbc7.anchor = GridBagConstraints.CENTER;
        gbc7.insets = new Insets(5, 0, 5, 0);

        deleteButton.setHideActionText(false);
        deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
        deleteButton.setText("Supprimer Partie");
        deleteButton.setPreferredSize(new Dimension(250, 37));
        deleteButton.setMinimumSize(new Dimension(250, 37));
        deleteButton.setMaximumSize(new Dimension(250, 37));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                selectedFile = selectedFile.substring(0, selectedFile.lastIndexOf("e") + 1);
                if (selectedFile != null) {
                    // Affiche une boîte de dialogue de confirmation
                    int response = JOptionPane.showConfirmDialog(null,
                            "Êtes-vous sûr de vouloir supprimer " + selectedFile + " ?",
                            "Confirmer la suppression",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        // Si l'utilisateur clique sur Oui, supprime le fichier
                        File file = new File("Resources/save/" + selectedFile);
                        if (file.exists() && file.isFile()) {
                            if (file.delete()) {
                                // Le fichier a été supprimé avec succès, rafraîchir la liste
                                refreshFileList();
                            } else {
                                // Une erreur s'est produite lors de la suppression du fichier
                                System.out.println("Une erreur s'est produite lors de la suppression du fichier.");
                            }
                        } else {
                            System.out.println("Le fichier à supprimer n'existe pas ou n'est pas un fichier.");
                        }
                    }
                }
            }
        });

        add(deleteButton, gbc7);

        // Espaces vertical
        Box verticalSpace2 = Box.createVerticalBox();
        GridBagConstraints gbc8 = new GridBagConstraints();
        gbc8.gridx = 3;
        gbc8.gridy = 5;
        gbc8.gridwidth = 1;
        gbc8.gridheight = 2;
        gbc8.fill = GridBagConstraints.BOTH;
        gbc8.anchor = GridBagConstraints.CENTER;
        gbc8.weighty = 1.0;
        add(verticalSpace2, gbc8);

        // Bouton "Menu Principal"
        GridBagConstraints gbc9 = new GridBagConstraints();
        gbc9.gridx = 3;
        gbc9.gridy = 7;
        gbc9.gridwidth = 1;
        gbc9.gridheight = 1;
        gbc9.fill = GridBagConstraints.NONE;
        gbc9.anchor = GridBagConstraints.CENTER;
        gbc9.insets = new Insets(0, 0, 10, 0);
        menuPrincipalButton.setText("Menu Principal");
        menuPrincipalButton.setPreferredSize(new Dimension(250, 37));
        menuPrincipalButton.setMinimumSize(new Dimension(250, 37));
        menuPrincipalButton.setMaximumSize(new Dimension(250, 37));

        menuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.afficherMenuPrincipal();
            }
        });
        add(menuPrincipalButton, gbc9);

        // Espaces vertical
        Box verticalSpace3 = Box.createVerticalBox();
        verticalSpace3.add(Box.createVerticalStrut(100));
        GridBagConstraints gbc10 = new GridBagConstraints();
        gbc10.gridx = 0;
        gbc10.gridy = 8;
        gbc10.gridwidth = 4;
        gbc10.gridheight = 1;
        gbc10.fill = GridBagConstraints.BOTH;
        gbc10.anchor = GridBagConstraints.CENTER;
        add(verticalSpace3, gbc10);

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
                    String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(saveFile.lastModified()));
                    fileListModel.addElement(saveFile.getName() + " - " + date);
                }
            }
        }
    }

    public void refresh() {
        if (fileList.getSelectedValue() == null) {
            loadButton.setEnabled(false);
            label1.setText("Aucune partie selectionnée");
            label2.setText("");
            label3.setText("");
            label4.setText("");
        } else {
            infoPanel.setBackground(new Color(0x99000000, true));
            loadButton.setEnabled(true);
            // Récupération partie selectionnée
            String selectedText = fileList.getSelectedValue();
            String name = selectedText.substring(0, selectedText.lastIndexOf("-") - 1);
            label1.setText(name);

            // Récupération date partie selectionnée
            String date = selectedText.substring(selectedText.lastIndexOf("-") + 2);
            label2.setText(date);

            Data_Niveau data_niveau;

            try {
                FileInputStream fileIn = new FileInputStream("Resources/save/" + name);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                data_niveau = (Data_Niveau) objectIn.readObject();
                attaquant = "Attaquant : " + data_niveau.attaquant.nom();
                defenseur = "Défenseur : " + data_niveau.defenseur.nom();

                objectIn.close();
                fileIn.close();

            } catch (FileNotFoundException e) {
                System.err.println("Fichier non trouvé : " + name);
                return;
            } catch (EOFException | InvalidClassException e) {
                System.err.println("Erreur lors de la lecture du fichier : " + name);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                System.err.println("Classe Data_Niveau introuvable");
                return;
            }

            label3.setText(attaquant);
            label4.setText(defenseur);
        }

        infoPanel.revalidate();
        infoPanel.repaint();

        repaint();

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