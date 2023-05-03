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

    public IA_difficile(int num, Jeu jeu) {
        super(num, jeu);
    }

    class MyRunable implements Runnable {
        int game_status;
        int return_value;
        Jeu jeu;
        Pion pion;
        Coordonne deplacement;

        MyRunable(Jeu jeu, Pion pion, Coordonne deplacement) {
            this.jeu = jeu;
            this.pion = pion;
            this.deplacement = deplacement;
        }
        @Override
        public void run() {
            Niveau clone = jeu.n.clone();
            game_status = clone.deplace_pion(pion.getCoordonne(), deplacement);
            return_value = analyse_recursive(clone, 1, Integer.MAX_VALUE);
        }
    }

    @Override
    boolean tempsEcoule() {
        nevaluation = 0;
        bypass1 = 0;
        bypass2 = 0;
        long l = System.currentTimeMillis();
        //Code de l'IA renvoyer vrai une fois que le coup est joué
        TypePion current_type = ((jeu.getJoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        int valeur_retour = Integer.MIN_VALUE;
        ArrayList<Coordonne> departs = new ArrayList<>();
        ArrayList<Coordonne> arrivees = new ArrayList<>();
        int nb_branches = 0;
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);

            ArrayList<Thread> threads = new ArrayList<>();
            ArrayList<MyRunable> myRunables = new ArrayList<>();
            for (int i = 0; i < deplacements.size(); i++) {
                nb_branches++;
                MyRunable myRunable = new MyRunable(jeu, pion, deplacements.get(i));
                myRunables.add(myRunable);
                Thread thread = new Thread(myRunable);
                thread.start();
                threads.add(thread);
            }
            for (int i = 0; i < threads.size(); i++) {
                try {
                    threads.get(i).join();
                    int tmp = myRunables.get(i).return_value;
                    if (myRunables.get(i).game_status != 0)
                        tmp = Integer.MAX_VALUE;
                    if (tmp >= valeur_retour){
                        if (tmp > valeur_retour) {
                            departs.clear();
                            arrivees.clear();
                            valeur_retour = tmp;
                            System.out.println("changement valeur retour : " + valeur_retour);
                        }
                        departs.add(pion.getCoordonne());
                        arrivees.add(deplacements.get(i));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        int index = new Random().nextInt(departs.size());
        Coordonne pion_depart = departs.get(index);
        Coordonne pion_arrivee = arrivees.get(index);
        jeu.jouer(pion_depart, pion_arrivee);
        System.out.println(nb_branches + " branches");
        System.out.println(bypass1 + " bypasse1    " + bypass2 + "bypass2");
        System.out.println(nevaluation + " evalutations en " + (System.currentTimeMillis() -l ) + "ms");
        return true;
    }

    private int analyse_recursive(Niveau n, int depth, int alphaBetaLimit) {
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
