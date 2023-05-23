package Controlleur.heuristiques;

import Modele.Niveau;
import Modele.TypePion;

public class HeuristiqueAttaqueRoi extends Heuristique {

    @Override
    public double evaluation(Niveau n, TypePion typePion) {
        int x = 0, y = 0;
        int attaquants = 0;
        int defenseurs = 0;
        int counthg = 0;
        int countbg = 0;
        int counthd = 0;
        int countbd = 0;
        for (int i = 0; i < n.getTaille(); i++) {
            for (int j = 0; j < n.getTaille(); j++) {
                if (!n.estVide(i, j)) {
                    switch (n.typePion(i, j)) {
                        case ATTAQUANT:
                            attaquants++;
                            break;
                        case DEFENSEUR:
                            defenseurs++;
                            break;
                        case ROI:
                            x = i;
                            y = j;
                            break;
                    }
                }
            }
        }
        for (int i = 0; i < n.getTaille(); i++) {
            for (int j = 0; j < n.getTaille(); j++) {
                if (!n.estVide(i, j)) {
                    if (n.typePion(i, j) == TypePion.ATTAQUANT) {
                        if (i < x) {
                            if (j < y)
                                counthg++;
                            else
                                counthd++;
                        } else {
                            if (j < y)
                                countbg++;
                            else
                                countbd++;
                        }
                    }
                }
            }
        }
        if (typePion == TypePion.ATTAQUANT) { // attaquant
            if (countbd < 2 && counthd < 2 || countbg < 2 && counthg < 2 || countbd < 2 && countbg < 2 || counthd < 2 && counthg < 2 || attaquants < 3) {
                return -10000;
            } else {
                return attaquants - defenseurs * 2;
            }
        } else { // defenseur
            if (countbd < 2 && counthd < 2 || countbg < 2 && counthg < 2 || countbd < 2 && countbg < 2 || counthd < 2 && counthg < 2) {
                return 10000;
            } else {
                return defenseurs - attaquants - Math.min(Math.min(counthg + countbg, counthd + countbd), Math.min(counthg + counthd, countbg + countbd)); // on retourne le nombre de defenseurs - le nombre d'attaquants - le nombre minimum de pions d'un coté du roi
            }
        }
    }

    @Override
    public String toString() {
        return "Attaque Roi";
    }
}
