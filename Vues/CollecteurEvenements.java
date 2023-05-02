package Vues;

import Modele.Coordonne;

public interface CollecteurEvenements {

    void tictac();
    void changeJoueur(int j, int t);
    // void changeTaille(int t);

    void clicSouris(int l, int c);
    void dragANDdrop(Coordonne coordonne, Coordonne coordonne2);
}

