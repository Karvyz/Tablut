package Controlleur;

import Controlleur.heuristiques.Heuristique;
import Modele.*;

import java.util.ArrayList;
import java.util.Random;

public class IA_DifficileProfondeur extends IA {
    int nevaluation = 0;
    int bypass1 = 0;
    int bypass2 = 0;
    Heuristique heuristique;
    int max_depth;
    TypePion monType;
    TypePion typeAdversaire;

    public IA_DifficileProfondeur(String nom, TypePion roleJ, Jeu j, Heuristique heuristique, int max_depth) {
        super(nom, TypeJoueur.IA_DIFFICILE, roleJ, j);
        this.heuristique = heuristique;
        this.max_depth = max_depth;
    }

    class MyRunable implements Runnable {
        int game_status;
        float return_value;
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
            game_status = clone.deplace_pion(new Coup(pion.getCoordonne(), deplacement));
            return_value = analyse_recursive(clone, 1, Integer.MAX_VALUE);
        }

        private float analyse_recursive(Niveau n, int depth, float alphaBetaLimit) {
            TypePion current_type = (depth % 2 == 0) ? monType : typeAdversaire;
            ArrayList<Pion> pions = n.getPions(current_type);

            float valeur_retour = Integer.MAX_VALUE;
            if (depth % 2 == 0)
                valeur_retour = Integer.MIN_VALUE;
            for (Pion pion : pions) {
                ArrayList<Coordonne> deplacements = pion.getDeplacement(n.plateau);
                for (Coordonne deplacement : deplacements) {
                    Niveau clone = n.clone();
                    int valeur_deplacement = clone.deplace_pion(new Coup(pion.getCoordonne(), deplacement));
                    if (valeur_deplacement != 0) {
                        if (depth % 2 == 0)
                            return Integer.MAX_VALUE - depth;
                        return Integer.MIN_VALUE + depth;
                    }
                    if (depth == max_depth - 1) {
                        nevaluation++;
                        return heuristique.evaluation(clone, current_type);
                    } else {
                        float tmp = analyse_recursive(clone, depth + 1, valeur_retour);
                        if (depth % 2 == 0) {
                            if (tmp < alphaBetaLimit) {
                                bypass1++;
                                return tmp;
                            }
                            if (tmp > valeur_retour) {
                                valeur_retour = tmp;
                            }
                        } else {
                            if (tmp > alphaBetaLimit) {
                                bypass2++;
                                return tmp;
                            }
                            if (tmp < valeur_retour) {
                                valeur_retour = tmp;
                            }
                        }
                    }
                }
            }
            return valeur_retour;
        }
    }

    @Override
    public Coup meilleurCoup() {
        if (((jeu.get_num_JoueurCourant()) % 2 ) == 0) {
            monType = TypePion.ATTAQUANT;
            typeAdversaire = TypePion.DEFENSEUR;
        }
        else {
            monType = TypePion.DEFENSEUR;
            typeAdversaire = TypePion.ATTAQUANT;
        }
        nevaluation = 0;
        bypass1 = 0;
        bypass2 = 0;
        long l = System.currentTimeMillis();
        ArrayList<Pion> pions = jeu.n.getPions(monType);
        float valeur_retour = Integer.MIN_VALUE;
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
                    float tmp = myRunables.get(i).return_value;
                    if (myRunables.get(i).game_status != 0)
                        tmp = Integer.MAX_VALUE;
                    if (tmp >= valeur_retour) {
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
        Coup meilleurCoup = new Coup(departs.get(index), arrivees.get(index));
        System.out.println(nb_branches + " branches");
        System.out.println(bypass1 + " bypasse1    " + bypass2 + "bypass2");
        System.out.println(nevaluation + " evalutations en " + (System.currentTimeMillis() - l) + "ms");
        return meilleurCoup;
    }


}
