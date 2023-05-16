package Controlleur;


import Modele.*;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class IA_monte extends IA {
    TypePion monType;
    TypePion typeAdversaire;


    public IA_monte(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.IA_DIFFICILE, roleJ, j);

    }


    public Coup jouecoupalea(Niveau niv, TypePion typeCourant) {
        ArrayList<Pion> pions = niv.getPions(typeCourant);
        Pion pion;
        ArrayList<Coordonne> deplacements;
        do {
            pion = pions.get(ThreadLocalRandom.current().nextInt(0, pions.size()));
            deplacements = pion.getDeplacement(niv.plateau);


        } while (deplacements.size() == 0);
        return new Coup(pion.getCoordonne(), deplacements.get(ThreadLocalRandom.current().nextInt(0, deplacements.size())));
    }

    public float evaluation(Niveau n) {
        float nbSimulations = 1000;
        int scoreTotal = 0;
        int res = 0;

        for (int i = 0; i < nbSimulations; i++) {
//            System.out.println("Simulation " + i);
            // Cloner la matrice actuelle pour la simulation
            Niveau nSimul = n.clone();


            // Simulation d'une partie aléatoire
            TypePion typeCourant = monType;
            while (res == 0) {
                Coup coup = jouecoupalea(nSimul, typeCourant);
                res = nSimul.deplace_pion(coup);
                if (typeCourant == TypePion.ATTAQUANT)
                    typeCourant = TypePion.DEFENSEUR;
                else
                    typeCourant = TypePion.ATTAQUANT;
            }

            // Calculer le score de la partie simulée
            int scorePartie = 0;
            if ((res == 1 && monType == TypePion.ATTAQUANT) || (res == 2 && monType == TypePion.DEFENSEUR))
                scorePartie = 1;
            else if ((res == 1) || (res == 2)) {
                scorePartie = -1;
            }
            scoreTotal += scorePartie;
            res = 0;
        }

        return scoreTotal; // Multiplication pour augmenter la précision de l'évaluation
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
            return_value = evaluation(niveau);
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


