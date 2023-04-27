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
        joueurCourant = 0;
        enCours = true;
        metAJour();
	}

    public void jouer(Coordonne depart, Coordonne arrive){
        int i = n.deplace_pion(depart, arrive);
        System.out.println("Le joueur " + joueurCourant + " a déplacé le piont Noir de (" + depart.getX() +"," + depart.getY() + ") en (" + arrive.getX() + "," + arrive.getY() +")");
        if (i > 0){
            enCours = false;
            if (i == 1)
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
            else
                System.out.println("PARTIE FINI CAR ROI EVADE");
        }
        
        //TODO test si une partie est finie;
        
        joueurCourant = (joueurCourant + 1) %2;
        metAJour();
    }

    //On regarde si le joueur a manger un pion adverse
    


    public boolean enCours(){
        return enCours;
    }
}
