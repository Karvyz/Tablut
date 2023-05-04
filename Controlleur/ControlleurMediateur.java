package Controlleur;

import Global.Configuration;
import Modele.*;
import Vues.*;
import Vues.CollecteurEvenements;


import java.io.*;
import java.sql.SQLOutput;

public class ControlleurMediateur implements CollecteurEvenements {

	Vues vues;
	IA ia1;
	IA ia2;
	Animation animIA1, animIA2;
	Animation animDemarrage;

    public static final int HUMAIN = 0;
    public static final int FACILE = 1;
    public static final int MOYEN = 2;
    public static final int DIFFCILE = 3;
	
    Jeu jeu;
	Joueurs[][] joueurs = new Joueurs[2][4];;
	final int lenteurAttente = 50;
	int decompte=20;
	private Pion selectionne;
	private boolean pionSelec = false;

	private int[] typeJoueur = new int[4];


	public ControlleurMediateur(Jeu j)  {
		jeu = j;


		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][HUMAIN] = new Humain(i, jeu);
			joueurs[i][FACILE] = new IA_facile(TypeJoueur.IA_FACILE, jeu, "");
            joueurs[i][MOYEN] = new IA_moyen(TypeJoueur.IA_FACILE, jeu, "");
            joueurs[i][DIFFCILE] = new IA_difficile_MassacrePion(TypeJoueur.IA_DIFFICILE, jeu, "");
		}
		typeJoueur[0] = HUMAIN; //DE BASE
		typeJoueur[1] = HUMAIN; //type

//		joueurs[1][DIFFCILE] = new IA_facile(1, jeu);
	}

	@Override//Deplacement en Drag&Drop
	public void dragANDdrop(Coordonne src, Coordonne dst) {
		if (joueurs[jeu().get_num_JoueurCourant()][typeJoueur[jeu.get_num_JoueurCourant()]].jeu(src, dst)) {// MODIF de jeu.n ici
			changeJoueur();
		}
	}

	@Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic sur un pion, on affiche ses déplacements possibles

		/*if(jeu.getJoueurCourant().type() != TypeJoueur.HUMAIN){
			System.out.println("ici");
			return;
		}*/
		Pion caseSelec = jeu.n.getPion(l,c);

		if (caseSelec == null && pionSelec ){ //ICI on cherche a déplacer
			Coordonne depart = new Coordonne(selectionne.getX(), selectionne.getY());
			Coordonne arrive = new Coordonne(l, c);
			if (joueurs[jeu.get_num_JoueurCourant()][typeJoueur[jeu.get_num_JoueurCourant()]].jeu(depart, arrive )){
				changeJoueur();
				pionSelec = false;
			}
		}
		else{ //Selection du pion
			if (jeu.n.check_clic_selection_pion(caseSelec, jeu.get_num_JoueurCourant())){
				pionSelec = true;
				selectionne = caseSelec.clone(); //on stock le pion selectionne
				caseSelec.affiche_liste_deplacement(caseSelec.getDeplacement(jeu.n.plateau));
			}
			else{
				System.out.println("Ce pion ne vous appartient pas");
			}
		}
	}

	@Override
	public void fixerMediateurVues(Vues v) {
		vues = v;
	}

	private void verifierMediateurVues(String message) {
		if (vues == null) {
			throw new IllegalStateException(message + " : médiateur de vues non fixé");
		}
	}

	private void verifierJeu(String message) {
		if (jeu == null) {
			throw new IllegalStateException(message + " : aucune partie commencée");
		}
	}

	@Override
	public void afficherDemarrage() {
		verifierMediateurVues("Impossible d'afficher le démarrage");
		vues.afficherDemarrage();
	}

	@Override
	public void afficherMenuPrincipal() {
		verifierMediateurVues("Impossible d'afficher le menu principal");
		vues.afficherMenuPrincipal();
	}

	@Override
	public void afficherMenuNouvellePartie() {
		verifierMediateurVues("Impossible d'afficher le menu nouvelle partie");
		vues.afficherMenuNouvellePartie();
	}

	@Override
	public void afficherJeu() {
		verifierMediateurVues("Impossible d'afficher le jeu");
		vues.afficherJeu();
	}

	@Override
	public void afficherMenuChargerPartie() {
		verifierMediateurVues("Impossible d'afficher le menu des parties sauvegardées");
		vues.afficherMenuChargerPartie();
	}



	@Override
	public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, String nomJ2, TypeJoueur typeJ2){
		verifierMediateurVues("Impossible de créer une nouvelle partie");
		//jeu = new Jeu();
		System.out.println(typeJ1 +"," +typeJ2);
		jeu.nouveauJoueur(nomJ1, typeJ1);
		jeu.nouveauJoueur(nomJ2, typeJ2);
		jeu.nouvellePartie();
		vues.nouvellePartie();
		initIA(typeJ1, typeJ2);
		typeJoueur[0] = typeJ1.ordinal(); //type
		typeJoueur[1] = typeJ2.ordinal(); //type


	}

	@Override
	public void partieSuivante() {
		verifierJeu("Impossible de passer à la partie suivante");
		jeu.nouvellePartie();
		vues.nouvellePartie();
		afficherJeu();
	}

	@Override
	public Jeu jeu() {
		verifierJeu("Impossible de renvoyer un jeu");
		return jeu;
	}

	@Override
	public void toClose() {
		vues.close();
		System.exit(0);
	}

	@Override
	public void afficherRegles() {
		vues.afficherR();
	}

	@Override
	public void annuler() {
		//System.out.print("Annuler : " + jeu().joueurActuel().nom() + " ");
		if (jeu.coup_annule.estVide()){
			System.out.println("Impossible d'annuler");
			return;
		}
		jeu.coup_a_refaire.empiler(jeu.n.clone()); //stock l'état avant d'annuler
		Niveau restaure = jeu.coup_annule.depiler(); //Recupère le niveau précedent
		jeu.n = restaure.clone();
		jeu.metAJour();
		jeu.joueurSuivant(); //La variable du jeu doit aussi être modifie
		changeJoueur(); //On redonne la main au joueur précedent
		System.out.println("Annulation effectué");
	}

	@Override
	public void refaire() {
		if (jeu.coup_a_refaire.estVide()) {
			System.out.println("Aucun coup n'est a refaire");
			return;
		}
		jeu.coup_annule.empiler(jeu.n.clone());
		Niveau a_refaire = jeu.coup_a_refaire.depiler();
		jeu.n = a_refaire.clone();
		jeu.metAJour();
		jeu.joueurSuivant();
		changeJoueur();
		System.out.println("Coup refait");
	}


	@Override
	public void toucheClavier(String touche) {
		if (jeu().partieTerminee()) {
			return;
		}
		switch (touche) {
			case "Annuler":
				annuler();
				break;
			case "Refaire":
				refaire();
				break;
			default:
				Configuration.instance().logger().info("Touche inconnue : " + touche);
		}
	}

	private void initIA(TypeJoueur typeJ1, TypeJoueur typeJ2){
		//int lenteurAnimationIA = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationIA"));
		System.out.println(typeJ1 +","+typeJ2);
		switch (typeJ1) {
			case IA_DIFFICILE:
				joueurs[0][DIFFCILE] = new IA_difficile_MassacrePion(typeJ1, jeu, "");
				break;
			case IA_MOYEN:
				System.out.println("IA MOYENNE NON MISE");
				joueurs[0][MOYEN] = new IA_moyen(typeJ1, jeu, "");
				break;
			case IA_FACILE:
				joueurs[0][FACILE] = new IA_facile(typeJ1, jeu, "");
				break;

		}
		/*if (typeJ1 != TypeJoueur.HUMAIN) {
			animIA1 = new AnimationIA(lenteurAnimationIA, ia1);
		}*/

		switch (typeJ2) {
			case IA_DIFFICILE:
				joueurs[1][DIFFCILE] = new IA_difficile_MassacrePion(typeJ2, jeu, "");
				break;
			case IA_MOYEN:
				System.out.println("IA MOYENNE NON MISE");
				joueurs[1][MOYEN] = new IA_moyen(typeJ2, jeu, "");
				break;
			case IA_FACILE:
				joueurs[1][FACILE] = new IA_facile(typeJ2, jeu, "");
				break;
		}
		/*if (typeJ2 != TypeJoueur.HUMAIN) {
			animIA2 = new AnimationIA(lenteurAnimationIA, ia2);
		}*/
	}



	void changeJoueur() {
		decompte = lenteurAttente;
	}

		public void tictac(){

			if (animDemarrage == null) {
				int lenteurAnimation = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationDemarrage"));
				animDemarrage = new AnimationDemarrage(lenteurAnimation, this);
			}
			if (!animDemarrage.terminee()) {
				animDemarrage.temps();
				return;
			}



			if (jeu.enCours()) {
				if (jeu == null || jeu().partieTerminee()) {
					return;
				}

				/*if (jeu().getJoueurCourant() == jeu.joueur1()) {
					animIA1.temps();
				} else {
					animIA2.temps();
				}*/
				if (jeu().n.PlusdePion(jeu().get_num_JoueurCourant())) {
					jeu().setEnCours(false);
					System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
				} else {

					if (decompte == 0) {
						int type = typeJoueur[jeu.get_num_JoueurCourant()];
						System.out.println("Type du joueur : "+ type);

						if (joueurs[jeu.get_num_JoueurCourant()][type].tempsEcoule()) { //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
							changeJoueur();
						} else {
							if (type == HUMAIN)
								System.out.println("On vous attend, joueur " + joueurs[jeu.get_num_JoueurCourant()][type].numJ + " vous devez déplacer un pion noir ");
							else
								System.out.println("On vous attend, joueur " + joueurs[jeu.get_num_JoueurCourant()][type].numJ + " vous devez déplacer un pion blanc ou le roi");

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

	//Le niveau courant devient le sommet de la pile a_refaire
	public boolean refaire_coup() {
		if (jeu.coup_a_refaire.estVide())
			return false;
	
		jeu.coup_annule.empiler(jeu.n.clone());
		Niveau a_refaire = jeu.coup_a_refaire.depiler();
		jeu.n = a_refaire.clone();
		jeu.metAJour();
		jeu.joueurSuivant();
		changeJoueur();
		return true;
	
	}
	
	//Le niveau courant devient le sommet de la pile annule_coup, soit le coup précedent
	public boolean restaurer_niveau(){
		if (jeu.coup_annule.estVide()){
			return false;
		}
		jeu.coup_a_refaire.empiler(jeu.n.clone()); //stock l'état avant d'annuler
		Niveau restaure = jeu.coup_annule.depiler(); //Recupère le niveau précedent
		jeu.n = restaure.clone();
		jeu.metAJour();
		jeu.joueurSuivant(); //La variable du jeu doit aussi être modifie
		changeJoueur(); //On redonne la main au joueur précedent
		return true;
	}

	

}
