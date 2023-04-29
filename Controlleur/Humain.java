package Controlleur;
import Modele.Coordonne;
import Modele.Jeu;
import Modele.Pion;

public class Humain extends Joueurs {

    public Humain(int num, Jeu jeu) {
        super(num, jeu);
    }
    
    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean jeu(Coordonne src, Coordonne dst) {
        Pion depart = jeu.n.getPion(src.getX(), src.getY()); //Recupère le pion
        if (jeu.n.check_clic_selection_pion(depart, jeu.joueurCourant)){ //Vérifie que le Pions choisit est bien de notre Type, joueur 0 implique de jouer les Attaquants et joueur 1 implique de jouer Defenseurs et Roi
            if(jeu.n.check_clic_selection_dest(depart, dst.getX(), dst.getY())){ //On vérifie que la case d'arrive est accessible
                jeu.jouer(src, dst); 
                return true;
            }
            else
                System.out.println("Déplacement impossible");
        }
        else
            System.out.println("le pion choisit est invalide");

		return false;
	}

}
