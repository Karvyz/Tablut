package Controlleur.heuristiques;

import Modele.*;

public class HeuristiqueMassacrePion extends Heuristique {

    @Override
    public float evaluation(Niveau n, TypePion typePion) {
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
        if (typePion == TypePion.ATTAQUANT) {
            return defenseurs - attaquants;
        }
        return attaquants - defenseurs;
    }

    @Override
    public String toString() {
        return "MP";
    }
}
