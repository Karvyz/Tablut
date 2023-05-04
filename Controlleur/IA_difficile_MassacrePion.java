package Controlleur;

import Modele.Jeu;
import Modele.Niveau;
import Modele.TypeJoueur;

public class IA_difficile_MassacrePion extends IA_difficile {
    public IA_difficile_MassacrePion(TypeJoueur type, Jeu jeu, String nom) {
        super(type, jeu, nom);
    }

    @Override
    public int evaluation(Niveau n) {
        nevaluation++;
        int attaquants = 16;
        int defenseurs = 8;
        for (int i = 0; i < n.getTaille(); i++) {
            for (int j = 0; j < n.getTaille(); j++) {
                if (!n.estVide(i, j)) {
                    switch (n.typePion(i, j)) {
                        case ATTAQUANT:
                            attaquants--;
                            break;
                        case DEFENSEUR:
                            defenseurs--;
                            break;
                        case ROI:
                    }
                }
            }
        }
        if (jeu.get_num_JoueurCourant() == 0) {
            return defenseurs - attaquants;
        }
        return attaquants - defenseurs;
    }


}
