package Controlleur;


import Controlleur.heuristiques.Heuristique;
import Modele.*;


import java.util.ArrayList;


public class IA_MonoEtage extends IA {
    TypePion monType;
    TypePion typeAdversaire;
    Heuristique heuristique;

    public IA_MonoEtage(String nom, TypePion roleJ, Jeu j, Heuristique heuristique) {
        super(nom, TypeJoueur.IA_DIFFICILE, roleJ, j);
        this.heuristique = heuristique;
    }

    class Thready implements Runnable {

        float return_value;
        Niveau niveau;
        Coup coup;

        Thready(Niveau niveau, Coup coup) {
            this.niveau = niveau;
            this.coup = coup;
        }

        @Override
        public void run() {
            return_value = heuristique.evaluation(niveau, monType);
            System.out.println(return_value);
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
        double res_eval_temp;
        int res_fin;
        Coup temp, max = null;
        ArrayList<Pion> pions = jeu.n.getPions(monType);
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<Thready> threadies = new ArrayList<>();
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);
            for (int i = 0; i < deplacements.size(); i++) {
                temp = new Coup(pion.getCoordonne(), deplacements.get(i));
                Niveau clone = jeu.n.clone();
                int valeur_deplacement = clone.deplace_pion(temp);
                if (valeur_deplacement == 1 || valeur_deplacement == 2)
                    return temp;
//                if (valeur_deplacement == 3)
//                    if (evaluation(jeu.n) < 200) {
//                        return temp;
//                    }
                Thready thready = new Thready(clone, temp);
                threadies.add(thready);
                Thread thread = new Thread(thready);
                thread.start();
                threads.add(thread);

            }
        }
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
                float tmp = threadies.get(i).return_value;
                if (tmp > res_eval) {
                    max = threadies.get(i).coup;
                    res_eval = tmp;

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return max;
    }
}


