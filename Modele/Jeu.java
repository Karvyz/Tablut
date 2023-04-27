package Modele;

import Patterns.Observable;

public class Jeu extends Observable{

    public Niveau n;
    int joueurCourant;
    public boolean enCours = false;

    public Jeu(){
        nouvellePartie();
        System.out.println(n);
    }
    
    /**
	 * Crée une nouvelle partie de taille par défaut
	 */
	public void nouvellePartie() {
		this.n = new Niveau();
        joueurCourant = 0; //Surement useless;
        enCours = true;
        metAJour();
	}

    public int getJoueurCourant() { return joueurCourant;}
    public Niveau getNiveau(){ return n;}

    public boolean enCours(){
        return enCours;
    }
}
