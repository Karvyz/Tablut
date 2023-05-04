package Controlleur;

import Global.Configuration;
import Modele.*;
import Vues.*;
import Vues.CollecteurEvenements;


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
	Joueurs[][] joueurs = new Joueurs[2][4];
	private int[] typeJoueur = new int[4];
	final int lenteurAttente = 300;
	int decompte;
	private Pion selectionne;
	private boolean pionSelec = false;
	public ControlleurMediateur(Jeu j)  {
		jeu = j;
		joueurParDefaut();//Pour initiliaser un joueur Humain
	}

	@Override
	public Jeu jeu() {
		verifierJeu("Impossible de renvoyer un jeu");
		return jeu;
	}

	@Override
	public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, String nomJ2, TypeJoueur typeJ2){
		verifierMediateurVues("Impossible de créer une nouvelle partie");
		System.out.println("INITialisation des joueurs \n Type des joueurs choisis pour la partie J0: " + typeJ1 +", J1: " +typeJ2);
		jeu.nouveauJoueur(nomJ1, typeJ1); //Initialisation des joueurs
		jeu.nouveauJoueur(nomJ2, typeJ2);
		initIA(typeJ1, typeJ2);
		typeJoueur[0] = typeJ1.ordinal(); //type
		typeJoueur[1] = typeJ2.ordinal(); //type
		jeu.nouvellePartie();
		vues.nouvellePartie();
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
				selectionne = caseSelec.clone(); //on stocke le pion sélectionne
				caseSelec.affiche_liste_deplacement(caseSelec.getDeplacement(jeu.n.plateau));
			}
			else{
				System.out.println("Ce pion ne vous appartient pas");
			}
		}
	}
	@Override
	public void partieSuivante() {
		verifierJeu("Impossible de passer à la partie suivante");
		jeu.nouvellePartie();
		vues.nouvellePartie();
		afficherJeu();
	}

	@Override
	public void toucheClavier(String touche) {
		if (jeu().partieTerminee()) {
			return;
		}
		switch (touche) {
			case "Annuler":
				jeu.annuler();
				break;
			case "Refaire":
				jeu.refaire();
				break;
			default:
				Configuration.instance().logger().info("Touche inconnue : " + touche);
		}
	}


	//Permet de définir les Objets pour les joueurs, et le type des Joueurs par défault est IA_facile
	private void joueurParDefaut() {
		joueurs[0][HUMAIN] = new Humain(0, jeu);
		joueurs[1][HUMAIN] = new Humain(1, jeu);
	}
	private void initIA(TypeJoueur typeJ1, TypeJoueur typeJ2){
		//int lenteurAnimationIA = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationIA"));
		switch (typeJ1) {
			case IA_DIFFICILE:
				joueurs[0][DIFFCILE] = new IA_difficile_MassacrePion(typeJ1, jeu, "");
				break;
			case IA_MOYEN:
				System.out.println("IA MOYENNE NON MISE"); //TODO
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
				System.out.println("IA MOYENNE NON MISE");//TODO
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

	@Override
	public void changeJoueur(int j, int t) { //Permet de changer le type d'un joueur en cours de partie mais on va surement pas l'utiliser
		System.out.println("Nouveau type " + t + " pour le joueur " + j);
		typeJoueur[j] = t;
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
			int type = typeJoueur[jeu.get_num_JoueurCourant()];
			if (jeu().n.PlusdePion(jeu().get_num_JoueurCourant())) {
				jeu().setEnCours(false);
				System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
			}
			//TODO ici l'IA joue instanténément donc problème pour annuler coup en IA vs Humain
			else if (joueurs[jeu.get_num_JoueurCourant()][type].tempsEcoule()) //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
				changeJoueur();
			else if (decompte == 0) {
				if (type == HUMAIN && jeu.get_num_JoueurCourant()==0)
					System.out.println("C'est a vous de jouer : L'ATTAQUANT ");
				else
					System.out.println("C'est a vous de jouer : LE DEFENSEUR");
				decompte = lenteurAttente;
			}
			else {
				decompte--;
			}
		}
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
		System.out.println("Ouverture du jeu");
		vues.afficherJeu();
	}

	@Override
	public void afficherMenuChargerPartie() {
		verifierMediateurVues("Impossible d'afficher le menu des parties sauvegardées");
		vues.afficherMenuChargerPartie();
	}

	

}
