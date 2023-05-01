package Controlleur;

import Modele.*;

import java.util.ArrayList;

public class IA_difficile extends IA{

    int nevaluation = 0;
    static int MAX_DEPTH = 5;

    public IA_difficile(int num, Jeu jeu) {
        super(num, jeu);
    }

    @Override
    boolean tempsEcoule() {
        nevaluation = 0;
        long l = System.currentTimeMillis();
        //Code de l'IA renvoyer vrai une fois que le coup est joué
        TypePion current_type = ((jeu.getJoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        int valeur_retour = Integer.MIN_VALUE;
        Coordonne pion_depart = new Coordonne(0,0);
        Coordonne pion_arrivee = new Coordonne(0,0);
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);

            for (Coordonne deplacement : deplacements) {
                Niveau clone = jeu.n.clone();
                int retour = clone.deplace_pion(pion.getCoordonne(), deplacement);
                int tmp = analyse_recursive(clone, 1);
                if (retour != 0)
                    tmp = Integer.MAX_VALUE;
                if (tmp > valeur_retour){
                    valeur_retour = tmp;
                    pion_depart = pion.getCoordonne();
                    pion_arrivee = deplacement;
                    System.out.println("changement valeur retour : " + valeur_retour);

                }
            }
        }
        jeu.jouer(pion_depart, pion_arrivee);
        System.out.println(nevaluation + " evalutations en " + (System.currentTimeMillis() -l ) + "ms");
        return true;
    }

    private int analyse_recursive(Niveau n, int depth) {
        TypePion current_type = ((jeu.getJoueurCourant() + depth) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = n.getPions(current_type);

        int valeur_retour = Integer.MAX_VALUE;
        if (depth % 2 == 0)
            valeur_retour = Integer.MIN_VALUE;
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements= pion.getDeplacement(n.plateau);
            for (Coordonne deplacement : deplacements) {
                Niveau clone = n.clone();
                int valeur_deplacement = clone.deplace_pion(pion.getCoordonne(), deplacement);
                if (valeur_deplacement != 0) {
                    if (depth % 2 == 0)
                        return Integer.MAX_VALUE;
                    return Integer.MIN_VALUE;
                }
                if (depth == MAX_DEPTH - 1) {
                    return evaluation(clone);
                }
                else {
                    int tmp = analyse_recursive(clone, depth + 1);
                    if (depth % 2 == 0) {
                        if (tmp > valeur_retour){
                            valeur_retour = tmp;
                        }
                    }
                    else {
                        if (tmp < valeur_retour){
                            valeur_retour = tmp;
                        }
                    }
                }
            }
        }
        return  valeur_retour;
    }

    public int evaluation(Niveau n) {
        nevaluation++;
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
        if (jeu.getJoueurCourant() == 0) {
            return defenseurs - attaquants;
        }
        return attaquants - defenseurs;
    }
}
