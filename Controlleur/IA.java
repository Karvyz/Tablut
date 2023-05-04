package Controlleur;

import Modele.Jeu;
import Modele.Joueurs;
import Modele.TypeJoueur;

import static Modele.TypeJoueur.*;

public class IA extends Joueurs {

    public IA(TypeJoueur type, Jeu jeu, String nom) {
		super(type, jeu, nom);
    }

    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean jeu(int i, int j) {
		return true;
	}

    @Override
	public boolean tempsEcoule() {
		// // Pour cette IA, on selectionne aléatoirement une case libre
		// int i, j;

		// i = r.nextInt(plateau.hauteur());
		// j = r.nextInt(plateau.largeur());
		// while (!plateau.libre(i, j)) {
		// 	i = r.nextInt(plateau.hauteur());
		// 	j = r.nextInt(plateau.largeur());
		// }
		// plateau.jouer(i, j);
		return true;
	}
    
}
