package Controlleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.TypePion;

public class IA_difficile_le_roi_c_ciao extends IA_difficile{
    public IA_difficile_le_roi_c_ciao(String nom, TypePion roleJ, Jeu j, long timeLimitMs) {
        super(nom, roleJ, j, timeLimitMs);
    }

    @Override
    public float evaluation(Niveau n) {
        int x = 0, y = 0;
        int attaquants = 0;
        int defenseurs = 0;
        int g = 0, d = 0, h = 0, b = 0, counthg = 0, countbg = 0, counthd = 0, countbd = 0;
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
                            defenseurs++;
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
                        if (i < x) { //si la case est en haut du roi
                            if (j < y) // si la case est a gauche du roi
                                counthg++;
                            else if (j > y) // si la case est a droite du roi
                                counthd++;
                            else { // si la case est sur la meme ligne que le roi
                                h++;
                            }
                        } else if (i > x) { // si la case est en bas du roi
                            if (j < y) // si la case est a gauche du roi
                                countbg++;
                            else if (j > y) // si la case est a droite du roi
                                countbd++;
                            else { // si la case est sur la meme ligne que le roi
                                b++;
                            }
                        } else { // si la case est sur la meme ligne que le roi
                            if (j < y) // si la case est a gauche du roi
                                g++;
                            else if (j > y) // si la case est a droite du roi
                                d++;
                        }
                    }
                }
            }
        }
        if (jeu.get_num_JoueurCourant() == 0) { // attaquant
            if (peut_tuer_roi(x, y, n)) {
                return Integer.MAX_VALUE;
            }
            if (attaquants < 3 || (d < 1 || g < 1 || b < 1 || h < 1)) {
                return Integer.MIN_VALUE;
            } else {
                return 8-defenseurs-(16-attaquants);
            }
        } else { // defenseur
            if (countbd + counthd + d < 2 || countbg + counthg + g < 2 || countbd + countbg + b < 2 || counthd + counthg + h < 2) {
                if (x == 0 || y == 0 || x == 8 || y == 8) {
                    if (x == 0 && y == 0 || x == 0 && y == 8 || x == 8 && y == 0 || x == 8 && y == 8) {
                        return 100;
                    } else {
                        return 30;
                    }
                }
                return 20;
            } else {
                return 16-attaquants-(8-defenseurs); // on retourne le nombre de defenseurs - le nombre d'attaquants - le nombre minimum de pions d'un coté du roi
            }
        }
    }

    public boolean peut_tuer_roi(int x, int y, Niveau n) {
        return ((x == 8 || n.estAttaquant(x + 1, y) || n.estForteresse(x + 1, y) || n.estKonakis(x + 1, y))
                && (x == 0 || n.estAttaquant(x - 1, y) || n.estForteresse(x - 1, y) || n.estKonakis(x - 1, y))
                && (y == 8 || n.estAttaquant(x, y + 1) || n.estForteresse(x, y + 1) || n.estKonakis(x, y + 1))
                && (y == 0 || n.estAttaquant(x, y - 1) || n.estForteresse(x, y - 1) || n.estKonakis(x, y - 1)));
    }
}
