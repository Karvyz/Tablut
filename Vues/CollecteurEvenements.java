package Vues;

import Modele.*;

public interface CollecteurEvenements {

    void tictac();
    // void changeTaille(int t);

    void clicSouris(int l, int c);
    void dragANDdrop(Coordonne coordonne, Coordonne coordonne2);

    void fixerMediateurVues(Vues v);

    void afficherDemarrage();

    void afficherMenuPrincipal();

    void afficherMenuNouvellePartie();

    void afficherJeu();

    void afficherMenuChargerPartie();

    void nouvellePartie(String nomJ1, TypeJoueur typeJ1, TypePion roleJ1, String nomJ2, TypeJoueur typeJ2, TypePion roleJ2);

    void partieSuivante();

    Jeu jeu();

    void toClose();

    void afficherRegles();

    void toucheClavier(String touche);

    void restaurePartie(String fichier);
}

