package Modele;

import Patterns.Observable;

public class Jeu extends Observable{

    public Niveau n;
    private int joueurCourant;
    private boolean enCours = false;

    public Jeu(){
        nouvellePartie();
    }
    
    /**
	 * Crée une nouvelle partie de taille par défaut
	 */
	public void nouvellePartie() {
		this.n = new Niveau();
        joueurCourant = 0;
        enCours = true;
        metAJour();
	}


    public void jouer(Coordonne depart, Coordonne arrive){
        int i = n.deplace_pion(depart, arrive);
        System.out.println("Déplacement du pion de (" + depart.getX() +"," + depart.getY() + ") en (" + arrive.getX() + "," + arrive.getY() +")");
        if (i > 0){
            enCours = false;
            if (i == 1)
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
            else
                System.out.println("PARTIE FINI CAR ROI EVADE");
        }
        
        //TODO test si une partie est finie;
        
        joueurSuivant();
        metAJour();
    }

    //On regarde si le joueur a manger un pion adverse
    
    public void joueurSuivant(){
        joueurCourant = (joueurCourant + 1) %2;
    }

    public int joueurCourant(){
        return joueurCourant;
    }

    public boolean enCours(){
        return enCours;
    }

    public void setEnCours(boolean enCours){
        this.enCours = enCours;
    }
}
