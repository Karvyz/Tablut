package Controlleur;

import Modele.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class IA_moyen extends Joueurs {

    public IA_moyen(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.IA_MOYEN, roleJ, j);
    }

    @Override
    public boolean tempsEcoule() {
        TypePion current_type = ((jeu.get_num_JoueurCourant()) % 2) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        Pion pion;
        ArrayList<Coordonne> deplacements;
        do {
            pion = pions.get(ThreadLocalRandom.current().nextInt(0, pions.size()));
            deplacements = pion.getDeplacement(jeu.n.plateau);

        } while (deplacements.size() == 0);
        jeu.jouer(pion.getCoordonne(), deplacements.get(ThreadLocalRandom.current().nextInt(0, deplacements.size())));
        return true;
    }

}
