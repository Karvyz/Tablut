package Controlleur;
import java.util.ArrayList;

import Modele.Coordonne;
import Modele.Jeu;
import Modele.Niveau;
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
	final int lenteurAttente = 50;
	int decompte;
	int numTour = 1;

	
	public Pion selectionne;
	private boolean pionSelec = false;
	private boolean deplSelec = false;
	private Niveau retour;
	

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
		
		Pion caseSelec = jeu.n.getPion(l,c);

		if(pionSelec == true && deplSelec == true ){
			jeu.n = retour.copy(); //on a cliqué ailleurs après avoir joué le coup mais avant de valider
			if(caseSelec != null && !caseSelec.equals(selectionne)){ //Ici on essaie de changer de pion de départ
				jeu.metAJour();
				setDeplSelec(false);
			}
		}

		if (caseSelec == null && pionSelec == true){ //ICI on cherche a déplacer un pion
			Coordonne depart = new Coordonne(selectionne.getX(), selectionne.getY());
			Coordonne arrive = new Coordonne(l, c);
			if(!jeu.n.check_clic_selection_dest(selectionne, l, c))
				System.out.println("Destination invalide"); //a ce stade on a toujours un pion selectionne
			else{
				if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(depart, arrive) )// MODIF de jeu.n ici
					deplSelec = true;
				else
					System.out.println("Coup invalide");
			}
		} 	
		else{ //Selection du pion 
			if (jeu.n.check_clic_selection_pion(caseSelec, joueurCourant)){ //Vérifie que le Pions choisit est bien de notre Type, joueur 0 implique de jouer les Attaquants et joueur 1 implique de jouer Defenseurs et Roi
				pionSelec = true;
				deplSelec = false; 
				selectionne = caseSelec.clone(); //on stock le pion selectionne

			}
			else{
				System.out.println("Ce pion n'est pas valide");
			}
		}
	}


    public void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		retour = jeu.n.copy(); //on fait une copy du niveau courant;
		decompte = lenteurAttente;
	}

    public void tictac(){
        //System.out.println(joueurCourant);

        if (jeu.enCours()) {
			if(jeu.n.PlusdePion(joueurCourant)){
				jeu.enCours = false;
				System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
			}else{
				
				if (decompte == 0) {
					int type = typeJoueur[joueurCourant];
	
					// Lorsque le temps est écoulé on le transmet au joueur courant.
					// Si un coup a été joué (IA) on change de joueur.
					if (joueurs[joueurCourant][type].tempsEcoule()) { //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
						changeJoueur();
					} else {
					// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
						if (numTour == 1){
							retour = jeu.n.copy(); //On fais une copy du niveau courant au premier tour
							numTour = 0;
						}
						if (joueurs[joueurCourant][type].numJ == 0)
							System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ + " vous devez déplacer un pion noir ");
						else
							System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ + " vous devez déplacer un pion blanc ou le roi");
	
						decompte = lenteurAttente;
					}
				} else {
					decompte--;
				}
			}
		}
    }

	@Override
	public void changeJoueur(int j, int t) {
		System.out.println("Nouveau type " + t + " pour le joueur " + j);
		typeJoueur[j] = t;
	}

	public boolean isPionSelec() { //renvoi true si pionSelec = true
		return pionSelec;
	}
	public void setPionSelec(boolean pionSelec) { //change la valeur boolenne de pionSelec
		this.pionSelec = pionSelec;
	}
	
	public boolean isDeplSelec() {
		return deplSelec;
	}

	public void setDeplSelec(boolean deplSelec) {
		this.deplSelec = deplSelec;
	}

}
