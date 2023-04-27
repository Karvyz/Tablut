package Controlleur;

import Modele.Coordonne;
import Modele.Jeu;

abstract class Joueurs {

    Jeu jeu;
    int numJ;
    public Joueurs(int num, Jeu j){
        jeu = j;
        numJ = num;
    }

    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean jeu(Coordonne i, Coordonne j) {
		return false;
	}

    // Méthode appelée pour tous les joueurs une fois le temps écoulé
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean tempsEcoule() {
		return false;
	}
}
