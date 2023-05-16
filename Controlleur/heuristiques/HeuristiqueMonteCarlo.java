package Controlleur.heuristiques;

import Modele.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class HeuristiqueMonteCarlo extends Heuristique{
    @Override
    public float evaluation(Niveau n, TypePion typePion) {
        float nbSimulations = 1000;
        int scoreTotal = 0;
        int res = 0;

        for (int i = 0; i < nbSimulations; i++) {
//            System.out.println("Simulation " + i);
            // Cloner la matrice actuelle pour la simulation
            Niveau nSimul = n.clone();


            // Simulation d'une partie aléatoire
            TypePion typeCourant = typePion;
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
            if ((res == 1 && typePion == TypePion.ATTAQUANT) || (res == 2 && typePion == TypePion.DEFENSEUR))
                scorePartie = 1;
            else if ((res == 1) || (res == 2)) {
                scorePartie = -1;
            }
            scoreTotal += scorePartie;
            res = 0;
        }

        return scoreTotal; // Multiplication pour augmenter la précision de l'évaluation
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
}
