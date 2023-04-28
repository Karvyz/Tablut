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

        jeu.jouer(depart, arrive); 

		return true;
	}

}
