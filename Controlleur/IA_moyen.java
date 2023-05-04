package Controlleur;

import Modele.Jeu;
import Modele.Joueurs;
import Modele.TypeJoueur;
import Modele.TypePion;

public class IA_moyen extends Joueurs {

    public IA_moyen(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.IA_MOYEN, roleJ, j);
    }

}
