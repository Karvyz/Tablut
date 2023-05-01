package Controlleur;

import Modele.Jeu;

public class IA_difficile extends IA{

    public IA_difficile(int num, Jeu jeu) {
        super(num, jeu);
    }

    @Override
	public boolean tempsEcoule() {
		//Code de l'IA renvoyer vrai une fois que le coup est joué
		return true;
	}

}
