package Modele;

public class JoueursCreation {

    public static Joueurs createJoueur(String nom, TypeJoueur type, TypePion roleJ, Jeu jeu) {
        if (type == TypeJoueur.HUMAIN) {
            return new Controlleur.Humain(nom, roleJ, jeu);
        } else if (type == TypeJoueur.IA_FACILE ) {
            return new Controlleur.IA_facile(nom,roleJ, jeu);
        } else if (type == TypeJoueur.IA_MOYEN ) {
            return new Controlleur.IA_difficile_le_roi_c_ciao(nom, roleJ, jeu);
        }
        else if (type == TypeJoueur.IA_DIFFICILE) {
            return new Controlleur.IA_expert(nom, roleJ, jeu);
        }
        else {
            throw new IllegalArgumentException("Type de joueur non supporté");
        }
    }
}
