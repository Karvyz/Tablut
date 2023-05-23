package Vues;

import Modele.*;

public interface CollecteurEvenements {

    void tictac();

    void tictac2();

    boolean clicSouris(Pion selec, int l, int c);

    boolean dragANDdrop(Coordonne coordonne, Coordonne coordonne2);

    void fixerMediateurVues(Vues v);

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

    void restaurePartie();

    void fixeJeu(Jeu jeu);

    void afficherQuickPartie();

    void nouvelleQuickPartie();

    boolean chargerPartie(String s);

    boolean getStop();

    void saveGame();
}

