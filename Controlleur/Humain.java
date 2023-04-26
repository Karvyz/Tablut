package Controlleur;

import java.util.Scanner;

import Modele.Coordonne;
import Modele.Jeu;

public class Humain extends Joueurs {

    public Humain(int num, Jeu jeu) {
        super(num, jeu);
    }
    
    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean jeu(int l, int c) {
        Coordonne depart = new Coordonne(l, c);
        switch(numJ){
            case 0:
                if(jeu.n.estAttaquant(l,c)){ //Condition a supprimer si on check avant que le pion cliqué est bien un attquant
                    Coordonne depl = demandeDepl();
                    jeu.n.deplace_pion(depart, depl);
                    System.out.println("Le joueur 0 a joué");
                }else{
                    System.out.println("Ce pion n'est pas un attquant");
                    return false;
                }
                break;
            case 1:
                if(jeu.n.estDefenseur(l,c) || jeu.n.estRoi(l, c)){ //Condition a supprimer si on check avant que le pion cliqué est bien un attquant
                    Coordonne depl = demandeDepl();
                    jeu.n.deplace_pion(depart, depl);
                    System.out.println("Le joueur 1 a joué");
                }else{
                    System.out.println("Ce pion n'est pas un défenseur ni le roi");

                    return false;
                }
                break;
        }
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

}
