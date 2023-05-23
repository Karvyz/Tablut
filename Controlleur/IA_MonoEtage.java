package Controlleur;


import Controlleur.heuristiques.Heuristique;
import Modele.*;


import java.util.ArrayList;


public class IA_MonoEtage extends IA {
    TypePion monType;
    TypePion typeAdversaire;
    Heuristique heuristique;

    public IA_MonoEtage(String nom, TypePion roleJ, Jeu j, Heuristique heuristique) {
        super(nom, TypeJoueur.IA_MOYEN, roleJ, j);
        this.heuristique = heuristique;
    }

    class Thready implements Runnable {
        int valeur_deplacement;
        double return_value;
        Coup coup;

        Thready(Coup coup) {
            this.coup = coup;
            return_value = 0;
        }

        @Override
        public void run() {
            Niveau clone = jeu.n.clone();
            valeur_deplacement = clone.deplace_pion(coup);
            if (valeur_deplacement == 0)
                return_value = heuristique.evaluation(clone, monType);
        }
    }

    public Coup meilleurCoup() {
        if (((jeu.get_num_JoueurCourant()) % 2) == 0) {
            monType = TypePion.ATTAQUANT;
            typeAdversaire = TypePion.DEFENSEUR;
        } else {
            monType = TypePion.DEFENSEUR;
            typeAdversaire = TypePion.ATTAQUANT;
        }
        double res_eval = Double.NEGATIVE_INFINITY;
        Coup temp, max = null;
        ArrayList<Pion> pions = jeu.n.getPions(monType);
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<Thready> threadies = new ArrayList<>();
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);
            for (int i = 0; i < deplacements.size(); i++) {
                temp = new Coup(pion.getCoordonne(), deplacements.get(i));
                Thready thready = new Thready(temp);
                threadies.add(thready);
                Thread thread = new Thread(thready);
                thread.start();
                threads.add(thread);

            }
        }
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
                int valeur_deplacement = threadies.get(i).valeur_deplacement;
                if (valeur_deplacement == 1 || valeur_deplacement == 2) {
                    res_eval = Integer.MAX_VALUE;
                    max = threadies.get(i).coup;
                }
                else {
                    double return_value = threadies.get(i).return_value;
                    if (return_value > res_eval) {
                        max = threadies.get(i).coup;
                        res_eval = return_value;

                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("max : " + res_eval);
        return max;
    }
}


