package Vues;

import Vues.JComposants.RulesCardLayout;

import javax.swing.*;
import java.awt.*;

import static Vues.JComposants.RulesCardLayout.getImages;

public class Vues {
    JFrame frame;
    VueJeu vueJeu;

    VueMenuParties vueMenuParties;

    final static String MENU_PRINCIPAL = "Menu Principal";
    final static String MENU_SAISIES = "Nouvelle Partie";
    final static String MENU_PARTIES = "Charger Partie";
    final static String JEU = "Jeu";
    static Dimension DIMENSION_DEFAUT;

    RulesCardLayout rulesCardLayout;

    Vues(JFrame f) {
        frame = f;
        rulesCardLayout = new RulesCardLayout();
        rulesCardLayout.addRulesCards(getImages());
    }

    void fixerVueJeu(VueJeu vue) {
        vueJeu = vue;
    }

    void fixerVueMenuParties(VueMenuParties vue) {
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
        // Affichage de la taille de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //System.out.println("Screen size: " + screenSize.width + "x" + screenSize.height);
        DIMENSION_DEFAUT = new Dimension(screenSize.width / 6 * 5, screenSize.height / 6 * 5);
        if (nom.equals(MENU_PARTIES) && vueMenuParties != null) {
            vueMenuParties.refreshFileList();
            vueMenuParties.refresh();
        }
        frame.setMinimumSize(DIMENSION_DEFAUT);
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