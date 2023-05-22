package Controlleur;
import Modele.*;
import java.io.Serializable;


public abstract class IA extends Joueurs implements Serializable {
    private static final long serialVersionUID = 1L; //déclare une constante de sérialisation
    public IA(String nom, TypeJoueur type, TypePion roleJ, Jeu j) {
        super(nom, type, roleJ, j);
    }

    @Override
	public boolean tempsEcoule() {
		joue();
		return true;
	}

    public int joue() {
        return jeu.jouer(meilleurCoup());
    }

    public abstract Coup meilleurCoup();
    
}
