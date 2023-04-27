package Vues;

import Modele.*;

public interface CollecteurEvenements {

    void tictac();
    void changeJoueur(int j, int t);
    // void changeTaille(int t);

    void clicSouris(int l, int c);

    void fixerMediateurVues(Vues v);

    void afficherDemarrage();

    void afficherMenuPrincipal();

    void afficherMenuNouvellePartie();

    void afficherJeu();

    void afficherMenuChargerPartie();

    void nouvellePartie(String nomJ1, TypeJoueur typeJ1, String nomJ2, TypeJoueur typeJ2);

    void partieSuivante();

    Jeu jeu();

    void toClose();

    void afficherRegles();

    void annuler();

    void refaire();

    void toucheClavier(String touche);

    boolean sauvegarderPartie();

    void chargerPartie(String nomSauvegarde);
}

