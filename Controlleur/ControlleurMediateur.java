package Controlleur;
import Modele.Jeu;
import Vues.CollecteurEvenements;


public class ControlleurMediateur implements CollecteurEvenements {
    
    public static final int HUMAIN = 0;
    public static final int FACILE = 1;
    public static final int MOYEN = 2;
    public static final int DIFFCILE = 3;

    Jeu jeu;
	Joueurs[][] joueurs;
	int [] typeJoueur;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;

    public ControlleurMediateur(Jeu j)  {
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

    // public void tictac(){
    //     if (jeu.enCours()) {
	// 		if (decompte == 0) {
	// 			int type = typeJoueur[joueurCourant];
	// 			// Lorsque le temps est écoulé on le transmet au joueur courant.
	// 			// Si un coup a été joué (IA) on change de joueur.
	// 			if (joueurs[joueurCourant][type].tempsEcoule()) {
	// 				changeJoueur();
	// 			} else {
	// 			// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
	// 				System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].num());
	// 				decompte = lenteurAttente;
	// 			}
	// 		} else {
	// 			decompte--;
	// 		}
	// 	}
    // }





}
