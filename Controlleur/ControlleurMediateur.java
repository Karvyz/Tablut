package Controlleur;
import java.util.ArrayList;
import java.util.Scanner;

import Modele.Coordonne;
import Modele.Jeu;
import Modele.Pion;
import Vues.CollecteurEvenements;


public class ControlleurMediateur implements CollecteurEvenements {
    
    public static final int HUMAIN = 0;
    public static final int FACILE = 1;
    public static final int MOYEN = 2;
    public static final int DIFFCILE = 3;

    Jeu jeu;
	Joueurs[][] joueurs;
	int [] typeJoueur;
	int joueurCourant = 0; //joueur qui commence
	final int lenteurAttente = 50;
	int decompte;

    public ControlleurMediateur(Jeu j)  {
        System.out.println(joueurCourant);
		jeu = j;
		joueurs = new Joueurs[2][4];
		typeJoueur = new int[4];
		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][HUMAIN] = new Humain(i, jeu);
			joueurs[i][FACILE] = new IA_facile(i, jeu);
            joueurs[i][MOYEN] = new IA_moyen(i, jeu);
            joueurs[i][DIFFCILE] = new IA_difficile(i, jeu);
			typeJoueur[i] = HUMAIN; //type 
		}
	}

    @Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
        
        Scanner scanner = new Scanner(System.in);
        //System.out.println();
        System.out.print("Entrez les coordonnées x y : ");
        l = scanner.nextInt();
        c = scanner.nextInt();
        
        Pion p = jeu.n.getPion(l,c);
        ArrayList<Coordonne> liste_depl = p.getDeplacement(jeu.n.plateau); // A REVOIR ICI
        p.affiche_liste_deplacement(liste_depl);
		//scanner.close();
        
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(l, c)){
            System.out.println(jeu.n);
			changeJoueur();
        }
	}

    void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

    public void tictac(){
        //System.out.println(joueurCourant);

        if (jeu.enCours()) {
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];
                //System.out.println("Le type: " + type);

				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
                    affiche_pions_dispo(joueurCourant);
					System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ);
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
    }

    public void affiche_pions_dispo(int j){
		ArrayList<Pion> liste ;
        TypePion t;
        switch(j){
            case 0:
                t = TypePion.ATTAQUANT;
				liste = jeu.n.getPions(t);
                break;
            case 1:
                t = TypePion.DEFENSEUR;
				TypePion t1 = TypePion.ROI;
				liste = jeu.n.getPions(t);
                ArrayList<Pion> liste2 = jeu.n.getPions(t1);
                liste.addAll(liste2); // concaténation de list2 à la fin de list1
				break;

			default:
				System.out.println("Joueur inconnu");
				return;
            
        }
        
        System.out.print("{ ");
        for(Pion p : liste){
            System.out.print("(" + p.getX() + "," + p.getY() +") ");
        }
        System.out.println("}");
    }

/*
//On affiche les pions disponibles du joueurs 0 ou 1

	public void affiche_pions_dispo(int j){
		TypePion t;
		switch(j){
			case 0:
				t = TypePion.ATTAQUANT;
				break;
			case 1:
				t = TypePion.DEFENSEUR;
				break;
		}
		ArrayList<Coordonne> liste = jeu.n.getPions(t);
		System.out.print("{ ");
		for(Coordonne c : liste){
			System.out.println("(" + c.getX() + "," + c.getY() +") ");
		}
		System.out.print(" }");
	}
*/



}
