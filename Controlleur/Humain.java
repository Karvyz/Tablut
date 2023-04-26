package Controlleur;

import Modele.Jeu;

public class Humain extends Joueurs {

    public Humain(int num, Jeu jeu) {
        super(num, jeu);
    }
    
    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean jeu(int i, int j) {
		return true;
	}

}
