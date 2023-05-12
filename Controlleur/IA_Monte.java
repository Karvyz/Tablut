package Controlleur;

import Modele.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class IA_Monte extends IA_difficile {
    public IA_Monte(String nom, TypePion roleJ, Jeu j, long timeLimitMs) {
        super(nom, roleJ, j, timeLimitMs);
    }

    public Coordonne[] jouecoupalea(Niveau niv, TypePion roleJ) {
        Coordonne[] coordcoup = new Coordonne[2];
        ArrayList<Pion> pions = niv.getPions(roleJ);
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
        int nbSimulations = 10;
        TypePion roleJ = ((jeu.get_num_JoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        int scoreTotal = 0;
        Coordonne[] coordcoup;
        int res = 0;

        for (int i = 0; i < nbSimulations; i++) {
            // Cloner la matrice actuelle pour la simulation
            Niveau nSimul = n.clone();

            // Simulation d'une partie aléatoire
            while (!(res > 0)) {
                coordcoup = jouecoupalea(nSimul, roleJ);
                res = nSimul.deplace_pion(coordcoup[0], coordcoup[1]);
            }

            // Calculer le score de la partie simulée
            int scorePartie = 0;
            if ((res == 1 && roleJ == TypePion.ATTAQUANT) || (res == 2 && roleJ == TypePion.DEFENSEUR))
                scorePartie = 1;
            else if ((res == 1) || (res == 2))
                scorePartie = -1;
            scoreTotal += scorePartie;
        }

        // Calculer la moyenne des scores des parties simulées
        double scoreMoyen = (double) scoreTotal / nbSimulations;

        return (int) (scoreMoyen * 1000); // Multiplication pour augmenter la précision de l'évaluation
    }
}
