package Controlleur;

import Global.Configuration;
import Modele.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class IA2 {
    Joueurs ia;
    Joueurs adversaire;
    HashMap<String, Integer> antiCycle;
    int horizon;
    ControlleurMediateur ctrl;
    int[] alphaBeta;
    Coup c1, c2, premierCoup, secondCoup;
    int minmax;
    boolean nouveauTour;

    IA2(Jeu jeu, Joueurs ia, Joueurs adversaire, ControlleurMediateur ctrl) {
        this.ia = ia;
        this.adversaire = adversaire;
        c1 = c2 = null;
        this.ctrl = ctrl;
        minmax = 1;
        nouveauTour = true;
    }

    public boolean tempsEcoule() {
        // // Pour cette IA, on selectionne aléatoirement une case libre
        // int i, j;

        // i = r.nextInt(plateau.hauteur());
        // j = r.nextInt(plateau.largeur());
        // while (!plateau.libre(i, j)) {
        // 	i = r.nextInt(plateau.hauteur());
        // 	j = r.nextInt(plateau.largeur());
        // }
        // plateau.jouer(i, j);
        return true;
    }


    void jouer() {
        /*
        if (nouveauTour) {
            this.calcul(ctrl.jeu().plateau(), horizon, 1);
            nouveauTour = false;
        }
        if (ctrl.jeu().prochaineActionSelectionPion()) {
            //Selection
            ctrl.jouer(c1.depart().ligne(), c1.depart().colonne());
        }
        else if (ctrl.jeu().prochaineActionJouerCoup() && ctrl.jeu().nombreCoupsRestantsTour() == 2) {
            // Coup 1
            ctrl.jouer(c1.arrivee().ligne(), c1.arrivee().colonne());
            // Coup 2
        } else if (ctrl.jeu().prochaineActionJouerCoup()) {
            ctrl.jouer(c2.arrivee().ligne(), c2.arrivee().colonne());
        }

         */
    }

}