package Controlleur;

import Modele.Coordonne;
import Modele.Jeu;
import Modele.Pion;
import Modele.TypePion;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IA_facile extends IA {

    public IA_facile(int num, Jeu jeu) {
        super(num, jeu);
    }

    @Override
    boolean tempsEcoule() {
        TypePion current_type = ((jeu.getJoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        Pion pion;
        ArrayList<Coordonne> deplacements;
        do {
            pion = pions.get(ThreadLocalRandom.current().nextInt(0, pions.size()));
            deplacements = pion.getDeplacement(jeu.n.plateau);

        }while (deplacements.size() == 0);
        jeu.jouer(pion.getCoordonne(), deplacements.get(ThreadLocalRandom.current().nextInt(0, deplacements.size())));
        return true;
    }
}
