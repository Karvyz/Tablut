package Controlleur;

import Modele.Jeu;
import Modele.Joueurs;
import Modele.TypeJoueur;
import Modele.TypePion;

import java.io.Serializable;

import static Modele.TypeJoueur.*;

public abstract class IA extends Joueurs implements Serializable {
    private static final long serialVersionUID = 1L;
    public IA(String nom, TypeJoueur type, TypePion roleJ, Jeu j) {
        super(nom, type, roleJ, j);
    }

    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
    // Si un joueur n'est pas concerné, il lui suffit de l'ignorer
    boolean jeu(int i, int j) {
        return true;
    }

    @Override
	public boolean tempsEcoule() {
		joue();
		return true;
	}

	abstract int joue();
    
}
