package Vues;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Vues {
    JFrame frame;
    VueJeu vueJeu;

    VueMenuParties2 vueMenuParties;

    final static String DEMARRAGE = "Démarrage";
    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_SAISIES = "Nouvelle Partie";
    final static String MENU_PARTIES = "Charger Partie";
    final static String JEU = "Jeu";
    final static Dimension DIMENSION_DEFAUT = new Dimension(1511, 850);
    final static Dimension DIMENSION_MENU_PARTIES = new Dimension(1020, 600);

    Vues(JFrame f) {
        frame = f;
    }

    void fixerVueJeu(VueJeu vue) {
        vueJeu = vue;
    }

    void fixerVueMenuParties(VueMenuParties2 vue) {
        vueMenuParties = vue;
    }

    public void nouvellePartie() {
        if (vueJeu == null) {
            throw new IllegalStateException("Impossible de créer une nouvelle partie : vue du jeu non fixée");
        }
        vueJeu.nouvellePartie();
    }

    public void restaurePartie() {
        vueJeu.restaurePartie();
    }

    private void afficher(String nom) {
        if (nom.equals(MENU_PARTIES) && vueMenuParties != null) {
            vueMenuParties.refreshFileList();
            vueMenuParties.refresh();
            //frame.setMinimumSize(DIMENSION_MENU_PARTIES);
            //frame.setSize(DIMENSION_MENU_PARTIES);
            frame.setMinimumSize(DIMENSION_DEFAUT);
        } else {
            frame.setMinimumSize(DIMENSION_DEFAUT);
            //frame.setSize(DIMENSION_DEFAUT);
        }
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), nom);
    }

    public void afficherDemarrage() {
        afficher(DEMARRAGE);
    }

    public void afficherMenuPrincipal() {
        afficher(MENU_PRINCIPAL);
    }

    public void afficherMenuNouvellePartie() {
        afficher(MENU_SAISIES);
    }

    public void afficherJeu() {
        afficher(JEU);
    }

    public void afficherMenuChargerPartie() {
        afficher(MENU_PARTIES);
    }

    public void close() {
        frame.setVisible(true);
        frame.dispose();
    }

    public void afficherR() {
        try {
            String inputPdf = "regles_du_jeu_Tablut.pdf";
            Path tempOutput = Files.createTempFile("TempManual", ".pdf");
            tempOutput.toFile().deleteOnExit();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(inputPdf)) {
                Files.copy(is, tempOutput, StandardCopyOption.REPLACE_EXISTING);
            }
            Desktop.getDesktop().open(tempOutput.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}