package Controlleur;

import Modele.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class IA2_facile extends IA2 {
    ControlleurMediateur ctrl;
    Joueurs j;
    List<Coup> coups;
    Coup c1;
    Coup c2;
    Random r;


    IA2_facile(Jeu jeu, Joueurs ia, Joueurs adversaire, ControlleurMediateur ctrl) {
        super(jeu, ia, adversaire, ctrl);
        horizon = 1;
        antiCycle = new HashMap<>();
        alphaBeta = new int[horizon];
        r = new Random();
    }
}
