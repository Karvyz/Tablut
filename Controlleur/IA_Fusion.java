package Controlleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.TypePion;

public class IA_Fusion extends IA_difficile{
    float lltk;
    float lrcc;
    float MP;
    float AR;
    public IA_Fusion(String nom, TypePion roleJ, Jeu j, long timeLimitMs, float lltk, float lrcc, float MP, float AR) {
        super(nom, roleJ, j, timeLimitMs);
        this.lltk = lltk;
        this.lrcc = lrcc;
        this.MP = MP;
        this.AR = AR;
    }
    @Override
    public float evaluation(Niveau n) {
        return lltk * lltk(n) + lrcc * lrcc(n) + MP * MP(n) + AR * AttaqueRoi(n);
    }

    @Override
    public String toString() {
        return "IA_Fusion{" +
                "lltk=" + lltk +
                ", lrcc=" + lrcc +
                ", MP=" + MP +
                ", AR=" + AR +
                '}';
    }

    float lltk(Niveau n) {
        int x = 0, y = 0;
        int attaquants = 0, defenseurs = 0, eval = 0, check = 0, check2 = 0;
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
        if (jeu.get_num_JoueurCourant() != 0) {
            for (int i = x - 2; i < x + 2; i++) {
                for (int j = y - 2; j < y + 2; j++) {
                    if (n.estAttaquant(i, j)) {
                        eval--;
                    }
                }
            }
            //si il y a un attaquant sur des lignes ou colonnes du roi
            for (int i = 0; i < n.getTaille(); i++) {
                if (n.estAttaquant(i, y)) {
                    check--;
                } else if (n.estAttaquant(x, i)) {
                    check2--;
                } else if (n.estDefenseur(i, y) && check < 0) {
                    check++;
                } else if (n.estDefenseur(x, i) && check2 < 0) {
                    check2++;
                }
            }
            eval += check + check2;
        } else {
            //on regarde les contours de la map et s'il y a deux attaquants collés ou un attaquant collé à une Forteresse ou des defenseurs sur cette ligne/colonne on baisse l'evaluation
            //si il y a rien de tout ça on augmente l'evaluation
            for (int i = 0; i < n.getTaille(); i++) {
                for (int j = 0; j < n.getTaille(); j++) {
                    if (i - 1 >= 0 && n.estAttaquant(i, j)) {
                        if (n.estAttaquant(i - 1, j) || n.estForteresse(i - 1, j) || n.estDefenseur(i - 1, j)) {
                            eval--;
                        } else if (n.estRoi(i - 1, j)) {
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                    if (i + 1 < n.getTaille() && n.estAttaquant(i, j)) {
                        if (n.estAttaquant(i + 1, j) || n.estForteresse(i + 1, j) || n.estDefenseur(i + 1, j)) {
                            eval--;
                        } else if (n.estRoi(i + 1, j)) {
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                    if (j - 1 >= 0 && n.estAttaquant(i, j)) {
                        if (n.estAttaquant(i, j - 1) || n.estForteresse(i, j - 1) || n.estDefenseur(i, j - 1)) {
                            eval--;
                        } else if (n.estRoi(i, j - 1)) {
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                    if (j + 1 < n.getTaille() && n.estAttaquant(i, j)) {
                        if (n.estAttaquant(i, j + 1) || n.estForteresse(i, j + 1) || n.estDefenseur(i, j + 1)) {
                            eval--;
                        } else if (n.estRoi(i, j + 1)) {
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                }
            }
        }
        return eval;
    }

    float lrcc(Niveau n) {
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
                return 10000;
            }
            if (attaquants < 3 || (d < 1 || g < 1 || b < 1 || h < 1)) {
                return -10000;
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

    float MP(Niveau n) {
        int attaquants = 16;
        int defenseurs = 8;
        for (int i = 0; i < n.getTaille(); i++) {
            for (int j = 0; j < n.getTaille(); j++) {
                if (!n.estVide(i, j)) {
                    switch (n.typePion(i, j)) {
                        case ATTAQUANT:
                            attaquants--;
                            break;
                        case DEFENSEUR:
                            defenseurs--;
                            break;
                        case ROI:
                    }
                }
            }
        }
        if (jeu.get_num_JoueurCourant() == 0) {
            return defenseurs - attaquants;
        }
        return attaquants - defenseurs;
    }

    float AttaqueRoi(Niveau n) {
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
        if (jeu.get_num_JoueurCourant() == 0) { // attaquant
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
}
