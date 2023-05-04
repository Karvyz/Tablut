package Controlleur;

import Modele.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IA_facile extends IA {

    public IA_facile(TypeJoueur type, Jeu jeu, String nom) {
        super(type, jeu, nom);
    }

    @Override
    public boolean tempsEcoule() {
        TypePion current_type = ((jeu.get_num_JoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
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
