package Controlleur;

import Modele.Jeu;
import Modele.Joueurs;

import java.util.HashMap;
import java.util.Random;

public class IA2_moyen extends IA2 {

    ControlleurMediateur ctrl;
    Random r;

    IA2_moyen(Jeu jeu, Joueurs ia, Joueurs adversaire, ControlleurMediateur ctrl) {
        super(jeu, ia, adversaire, ctrl);
        horizon = 3;
        antiCycle = new HashMap<>();
        alphaBeta = new int[horizon];
        r = new Random();
    }
}
