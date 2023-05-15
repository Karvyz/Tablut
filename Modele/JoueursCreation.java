package Modele;

import Controlleur.IA_DifficileTemps;
import Controlleur.heuristiques.HeuristiqueLeRoiCCiao;

public class JoueursCreation {

    public static Joueurs createJoueur(String nom, TypeJoueur type, TypePion roleJ, Jeu jeu) {
        switch (type) {
            case HUMAIN:
                return new Controlleur.Humain(nom, roleJ, jeu);
            case IA_FACILE:
                return new Controlleur.IA_facile(nom, roleJ, jeu);
            case IA_MOYEN:
                return new Controlleur.IA_moyen(nom, roleJ, jeu);
            case IA_DIFFICILE:
                return new Controlleur.IA_monte(nom ,roleJ, jeu);
        }
        return null;
    }
}
