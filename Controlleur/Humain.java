package Controlleur;

import java.util.ArrayList;
import java.util.Scanner;

import Modele.Coordonne;
import Modele.Jeu;

public class Humain extends Joueurs {

    public Humain(int num, Jeu jeu) {
        super(num, jeu);
    }
    
    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean jeu(Coordonne depart, Coordonne arrive) {
        // Coordonne depart = new Coordonne(l, c);
        // Pion p = jeu.n.getPion(l,c);
        // ArrayList<Coordonne> liste_depl = p.getDeplacement(jeu.n.plateau);
        // p.affiche_liste_deplacement(liste_depl);
        // if (liste_depl.isEmpty()){ //Aucun coup possible pour ce pion
        //     return false;
        // }
        // Coordonne arrive = demandeDepl();
     
        //if(check_Deplacement(liste_depl, arrive))
        jeu.jouer(depart, arrive); 
        //else          
            //return false;

		return true;
	}

    private Coordonne demandeDepl(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez les coordonnées de deplacement x y: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
		//scanner.close();

        return new Coordonne(x, y);

    }

    public boolean check_Deplacement(ArrayList<Coordonne> liste, Coordonne p) {
		return liste.contains(p);
	}

}
