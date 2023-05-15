package Modele;

import Controlleur.IA_DifficileTemps;
import Controlleur.heuristiques.HeuristiqueLeRoiCCiao;
import Controlleur.heuristiques.HeuristiqueLongLiveTheKing;

public class JoueursCreation {

    public static Joueurs createJoueur(String nom, TypeJoueur type, TypePion roleJ, Jeu jeu) {
        switch (type) {
            case HUMAIN:
                return new Controlleur.Humain(nom, roleJ, jeu);
            case IA_FACILE:
                return new Controlleur.IA_facile(nom, roleJ, jeu);
            case IA_MOYEN:
                return new Controlleur.IA_monte(nom, roleJ, jeu);
            case IA_DIFFICILE:
                return new Controlleur.IA_DifficileProfondeur(nom ,roleJ, jeu, new HeuristiqueLongLiveTheKing(), 4);
        }
        return null;
    }
}
