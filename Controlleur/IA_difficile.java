package Controlleur;

import Modele.*;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.exit;

public abstract class IA_difficile extends IA{

    int nevaluation = 0;
    int bypass1 = 0;
    int bypass2 = 0;
    static int MAX_DEPTH = 6;

    public IA_difficile(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.IA_DIFFICILE, roleJ, j);
    }

    @Override
    public int joue() {
        System.out.println("yessaie");
        nevaluation = 0;
        bypass1 = 0;
        bypass2 = 0;
        long l = System.currentTimeMillis();
        //Code de l'IA renvoyer vrai une fois que le coup est joué
        TypePion current_type = ((jeu.get_num_JoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        int valeur_retour = Integer.MIN_VALUE;
        ArrayList<Coordonne> departs = new ArrayList<>();
        ArrayList<Coordonne> arrivees = new ArrayList<>();
        int nb_branches = 0;
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);

            for (int i = 0; i < deplacements.size(); i++) {
                nb_branches++;
                Niveau clone = jeu.n.clone();
                int retour = clone.deplace_pion(pion.getCoordonne(), deplacements.get(i));
                int tmp = analyse_recursive(clone, 1, Integer.MAX_VALUE);
                if (retour != 0)
                    tmp = Integer.MAX_VALUE;
                if (tmp >= valeur_retour) {
                    if (tmp > valeur_retour) {
                        departs.clear();
                        arrivees.clear();
                        valeur_retour = tmp;
//                        System.out.println("changement valeur retour : " + valeur_retour);

                    }
                    departs.add(pion.getCoordonne());
                    arrivees.add(deplacements.get(i));
                }
            }
        }
        int index = new Random().nextInt(departs.size());
        Coordonne pion_depart = departs.get(index);
        Coordonne pion_arrivee = arrivees.get(index);
//        System.out.println(nb_branches + " branches");
//        System.out.println(nevaluation + " evalutations en " + (System.currentTimeMillis() -l ) + "ms");
        System.out.println(nevaluation);
        return jeu.jouer(pion_depart, pion_arrivee);

    }

    private int analyse_recursive(Niveau n, int depth, int alphaBetaLimit) {

        TypePion current_type = ((jeu.get_num_JoueurCourant() + depth) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;

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
                        return Integer.MAX_VALUE - depth;
                    return Integer.MIN_VALUE + depth;
                }
                if (depth == MAX_DEPTH - 1) {
                    nevaluation++;
                    return evaluation(clone);
                }
                else {
                    int tmp = analyse_recursive(clone, depth + 1, valeur_retour);
                    if (depth % 2 == 0) {
                        if (tmp < alphaBetaLimit) {
                            bypass1++;
                            return tmp;
                        }
                        if (tmp > valeur_retour){
                            valeur_retour = tmp;
                        }
                    }
                    else {
                        if (tmp > alphaBetaLimit) {
                            bypass2++;
                            return tmp;
                        }
                        if (tmp < valeur_retour){
                            valeur_retour = tmp;
                        }
                    }
                }
            }
        }
        return  valeur_retour;
    }

    public abstract int evaluation(Niveau n);
}
