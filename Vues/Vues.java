package Vues;

import Vues.JComposants.RulesCardLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static Vues.JComposants.RulesCardLayout.getImages;

public class Vues {
    JFrame frame;
    VueJeu vueJeu;

    VueMenuParties2 vueMenuParties;

    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_SAISIES = "Nouvelle Partie";
    final static String MENU_PARTIES = "Charger Partie";
    final static String JEU = "Jeu";
    final static Dimension DIMENSION_DEFAUT = new Dimension(1599, 900);
    final static Dimension DIMENSION_MENU_PARTIES = new Dimension(1020, 600);

    RulesCardLayout rulesCardLayout;

    Vues(JFrame f) {
        frame = f;
        rulesCardLayout = new RulesCardLayout();
        rulesCardLayout.addRulesCards(getImages());
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

            frame.setMinimumSize(DIMENSION_DEFAUT);
        } else {
            frame.setMinimumSize(DIMENSION_DEFAUT);
        }
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), nom);
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
        rulesCardLayout.setVisible(true);
    }
}