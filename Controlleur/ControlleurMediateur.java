package Controlleur;
import java.util.ArrayList;
import java.util.Scanner;

import Modele.Coordonne;
import Modele.Jeu;
import Modele.Pion;
import Modele.TypePion;
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
	final int lenteurAttente = 20;
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
        
        // Scanner scanner = new Scanner(System.in);
        // //System.out.println();
        // System.out.print("Entrez les coordonnées du pion de votre choix x y : ");
        // l = scanner.nextInt();
        // c = scanner.nextInt();

        Pion p = jeu.n.getPion(l,c);
		ArrayList<Pion> pions_dispo = getPionsDispo(joueurCourant);

		if (check_clic(pions_dispo, p)){ //Vérifie que le Pions choisit est bien de notre Type, joueur 0 implique de jouer les Attaquants et joueur 1 implique de jouer Defenseurs et Roi
			
			if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(l, c)){
				System.out.println(jeu.n);
				changeJoueur();
			}else{
				System.out.println("Coup invalide");
			}
		}
        
	}

	// @Override
	// public void clicSouris(int l, int c) { //origine
	// 	if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(l, c)){
    //         //System.out.println(jeu.n);
	// 		changeJoueur();
    //     }
	// }

    void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

    public void tictac(){
        //System.out.println(joueurCourant);

        if (jeu.enCours()) {
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];

				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule()) { //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ + " OUBLIEZ PAS DE CLIQUER :)");
					affiche_pions_dispo(joueurCourant);
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
    }


	public TypePion typePion_JC(int JC){
		switch (JC){
			case 0:
				return TypePion.ATTAQUANT;
			case 1:
				return TypePion.DEFENSEUR;
			default:
				System.out.println("Joueur courant inconnu");
				return null;
		}
	}

	public ArrayList<Pion> getPionsDispo(int JoueurCourant){
		ArrayList<Pion> liste ;
        TypePion t = typePion_JC(joueurCourant);

		liste = jeu.n.getPions(t);

        if (t == TypePion.DEFENSEUR){
			TypePion t1 = TypePion.ROI;
            ArrayList<Pion> liste2 = jeu.n.getPions(t1);
            liste.addAll(liste2); // concaténation de list2 à la fin de list1        
        }

		return liste;
	}

	//Prends un joueur et affiche sa liste de pions dispos
    public void affiche_pions_dispo(int j){
		
		ArrayList<Pion> liste = getPionsDispo(j) ;
        System.out.print("Pions disponibles { ");
        for(Pion p : liste){
            System.out.print("(" + p.getX() + "," + p.getY() +") ");
        }
        System.out.println("}");
    }

	//Ici on alterne liste de pion et de coordonne c'est pas bien
	public boolean check_clic(ArrayList<Pion> liste, Pion p) { 
		//if(p.estCorrect()){ //Inutile normalement
		if (!jeu.n.estVide(p))
			return liste.contains(p);
		//}
		return false;
	}

	

}
