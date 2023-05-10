package Controlleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.TypeJoueur;
import Modele.TypePion;

public class IA_difficile_Long_live_the_king extends IA_difficile{
    public IA_difficile_Long_live_the_king(String nom, TypePion roleJ, Jeu j) {
        super(nom, roleJ, j);
    }

    @Override
    public int evaluation(Niveau n) {
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
        if(jeu.get_num_JoueurCourant() != 0){
            for(int i = x-2; i < x+2; i++){
                for(int j = y-2; j < y+2; j++){
                    if(n.estAttaquant(i, j)){
                        eval--;
                    }
                }
            }
            //si il y a un attaquant sur des lignes ou colonnes du roi
            for(int i = 0; i < n.getTaille(); i++){
                if(n.estAttaquant(i, y)){
                    check--;
                } else if(n.estAttaquant(x, i)){
                    check2--;
                } else if (n.estDefenseur(i, y) && check<0){
                    check++;
                } else if(n.estDefenseur(x, i) && check2<0){
                    check2++;
                }
            }
            eval += check + check2;
        } else {
            //on regarde les contours de la map et s'il y a deux attaquants collés ou un attaquant collé à une Forteresse ou des defenseurs sur cette ligne/colonne on baisse l'evaluation
            //si il y a rien de tout ça on augmente l'evaluation
            for(int i = 0; i < n.getTaille(); i++){
                for(int j = 0; j <n.getTaille(); j++){
                    if (i-1 >= 0 && n.estAttaquant(i,j)){
                        if(n.estAttaquant(i-1, j) || n.estForteresse(i-1, j) || n.estDefenseur(i-1, j)){
                            eval--;
                        } else if (n.estRoi(i-1, j)){
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                    if (i+1 < n.getTaille() && n.estAttaquant(i,j)){
                        if(n.estAttaquant(i+1, j) || n.estForteresse(i+1, j) || n.estDefenseur(i+1, j)){
                            eval--;
                        } else if (n.estRoi(i+1, j)){
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                    if (j-1 >= 0 && n.estAttaquant(i,j)){
                        if(n.estAttaquant(i, j-1) || n.estForteresse(i, j-1) || n.estDefenseur(i, j-1)){
                            eval--;
                        } else if (n.estRoi(i, j-1)){
                            eval += 5;
                        } else {
                            eval++;
                        }
                    }
                    if (j+1 < n.getTaille() && n.estAttaquant(i,j)){
                        if(n.estAttaquant(i, j+1) || n.estForteresse(i, j+1) || n.estDefenseur(i, j+1)){
                            eval--;
                        } else if (n.estRoi(i, j+1)){
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
}
