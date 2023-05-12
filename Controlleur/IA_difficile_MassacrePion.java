package Controlleur;

import Modele.*;

public class IA_difficile_MassacrePion extends IA_difficile {
    public IA_difficile_MassacrePion(String nom, TypePion roleJ, Jeu j, long timeLimitMs) {
        super(nom, roleJ, j, timeLimitMs);
    }

    @Override
    public float evaluation(Niveau n) {
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
