package Controlleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.TypePion;

public class IA_difficile_AttaqueRoi extends IA_difficile {
    public IA_difficile_AttaqueRoi(int num, Jeu jeu) {
        super(num, jeu);
    }
    @Override
    public int evaluation(Niveau n) {
        nevaluation++;
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
        if (jeu.getJoueurCourant() == 0) { // attaquant
            if (countbd < 2 && counthd < 2 || countbg < 2 && counthg < 2 || countbd < 2 && countbg < 2 || counthd < 2 && counthg < 2 || attaquants<3) {
                return Integer.MIN_VALUE;
            } else {
                return attaquants - defenseurs * 2;
            }
        } else { // defenseur
            if (countbd < 2 && counthd < 2 || countbg < 2 && counthg < 2 || countbd < 2 && countbg < 2 || counthd < 2 && counthg < 2) {
                return Integer.MAX_VALUE;
            } else {
                return defenseurs - attaquants - Math.min(Math.min(counthg+countbg, counthd+countbd),Math.min(counthg+counthd, countbg+countbd)); // on retourne le nombre de defenseurs - le nombre d'attaquants - le nombre minimum de pions d'un coté du roi
            }
        }
    }
}
