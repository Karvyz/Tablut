package Controlleur;

import Modele.Jeu;
import Modele.Joueurs;

import java.util.HashMap;

public class IA2_difficile extends IA2 {
    IA2_difficile(Jeu jeu, Joueurs ia, Joueurs adversaire, ControlleurMediateur ctrl) {
        super(jeu, ia, adversaire, ctrl);
        horizon = 3;
        antiCycle = new HashMap<>();
        alphaBeta = new int[horizon];
    }

    @Override
    public boolean tempsEcoule() {
        //Code de l'IA renvoyer vrai une fois que le coup est joué
        return true;
    }
}
