package Controlleur;

import Modele.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IA_facile extends IA {

    public IA_facile(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.IA_FACILE, roleJ, j);
    }

    @Override
    public Coup meilleurCoup() {
        TypePion current_type = ((jeu.get_num_JoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        Pion pion;
        ArrayList<Coordonne> deplacements;
        do {
            pion = pions.get(ThreadLocalRandom.current().nextInt(0, pions.size()));
            deplacements = pion.getDeplacement(jeu.n.plateau);

        }while (deplacements.size() == 0);
        return new Coup(pion.getCoordonne(), deplacements.get(ThreadLocalRandom.current().nextInt(0, deplacements.size())));
    }
}
