package Controlleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.TypePion;

public class IA_expert extends IA_difficile{
    public IA_expert(String nom, TypePion roleJ, Jeu j) {
        super(nom, roleJ, j);
    }

    @Override
    public int evaluation(Niveau n) {
        int eval = 0, attaquants = 16, defenseurs = 8;
        nevaluation++;
        if (jeu.get_num_JoueurCourant() == 0) { // attaquant
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
                    if (!n.estVide(i, j)) {
                        switch (n.typePion(i, j)) {
                            case ATTAQUANT:
                                attaquants--;
                                break;
                            case DEFENSEUR:
                                defenseurs--;
                                break;
                            case ROI:
                                break;
                        }
                    }
                }
            }
            if (attaquants == 0){
                return -1000;
            } else {
                return eval+(defenseurs-attaquants);
            }
        } else { //defenseur
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
            return attaquants - defenseurs;
        }
    }
}