package Controlleur;


import Modele.*;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class IA_monte extends IA{
    TypePion monType;
    TypePion typeAdversaire;


    public IA_monte(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.IA_DIFFICILE, roleJ, j);

    }


    public Coordonne[] jouecoupalea(Niveau niv) {
        Coordonne[] coordcoup = new Coordonne[2];
        ArrayList<Pion> pions = niv.getPions(monType);
        Pion pion;
        ArrayList<Coordonne> deplacements;
        do {
            pion = pions.get(ThreadLocalRandom.current().nextInt(0, pions.size()));
            deplacements = pion.getDeplacement(niv.plateau);


        }while (deplacements.size() == 0);
        coordcoup[0] = pion.getCoordonne();
        coordcoup[1] = deplacements.get(ThreadLocalRandom.current().nextInt(0, deplacements.size()));
        return coordcoup;
    }

    public float evaluation(Niveau n) {
        int nbSimulations = 1000;
        int scoreTotal = 0;
        Coordonne[] coordcoup;
        int res = 0;


        for (int i = 0; i < nbSimulations; i++) {
            System.out.println("Simulation " + i);
            // Cloner la matrice actuelle pour la simulation
            Niveau nSimul = n.clone();


            // Simulation d'une partie aléatoire
            while (!(res > 0)) {
                coordcoup = jouecoupalea(nSimul);
                Coup coup = new Coup(coordcoup[0], coordcoup[1]);
                res = nSimul.deplace_pion(coup);
            }


            // Calculer le score de la partie simulée
            int scorePartie = 0;
            if ((res == 1 && monType == TypePion.ATTAQUANT) || (res == 2 && monType == TypePion.DEFENSEUR))
                scorePartie = 1;
            else if ((res == 1) || (res == 2))
                scorePartie = -1;
            scoreTotal += scorePartie;
            res = 0;
        }


        // Calculer la moyenne des scores des parties simulées
        double scoreMoyen = (double) scoreTotal / nbSimulations;


        return (int) (scoreMoyen * 1000); // Multiplication pour augmenter la précision de l'évaluation
    }

    public Coup meilleurCoup() {
        if (((jeu.get_num_JoueurCourant()) % 2 ) == 0) {
            monType = TypePion.ATTAQUANT;
            typeAdversaire = TypePion.DEFENSEUR;
        }
        else {
            monType = TypePion.DEFENSEUR;
            typeAdversaire = TypePion.ATTAQUANT;
        }
        double res_eval = Double.NEGATIVE_INFINITY;
        double res_eval_temp;
        int res_fin;
        Coup temp, max = null;
        ArrayList<Pion> pions = jeu.n.getPions(monType);
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);
            for (int i = 0; i < deplacements.size(); i++) {
                temp = new Coup(pion.getCoordonne(),deplacements.get(i));
                Niveau clone = jeu.n.clone();
                res_fin = clone.deplace_pion(temp);
                if (res_fin == 1 || res_fin == 2)
                    return temp;
                else if (res_fin <= 0) {
                    res_eval_temp = evaluation(clone);
                    if (res_eval_temp > res_eval){
                        max = temp;
                        res_eval = res_eval_temp;
                    }
                } else {
                    if (evaluation(jeu.n) < 200){
                        return temp;
                    }
                }
            }
        }
        return max;
    }
}


