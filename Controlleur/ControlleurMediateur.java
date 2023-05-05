package Controlleur;

import Global.Configuration;
import Modele.*;
import Vues.*;
import Vues.CollecteurEvenements;

import java.sql.SQLOutput;


public class ControlleurMediateur implements CollecteurEvenements{

	Vues vues;
	Animation animIA1, animIA2;
	Animation animDemarrage;

    Jeu jeu;

	final int lenteurAttente = 200;
	int decompte;
	private Pion selectionne;
	private boolean pionSelec = false;
	public ControlleurMediateur(Jeu j)  {
		jeu = j;
	}

	@Override
	public Jeu jeu() {
		verifierJeu("Impossible de renvoyer un jeu");
		return jeu;
	}

	/**Méthode en rapport avec le lancement d'une partie*/
	@Override
	public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, TypePion roleJ1 ,String nomJ2, TypeJoueur typeJ2, TypePion roleJ2){
		verifierMediateurVues("Impossible de créer une nouvelle partie");
		jeu.nouveauJoueur(nomJ1, typeJ1, roleJ1); //Initialisation des joueurs
		jeu.nouveauJoueur(nomJ2, typeJ2, roleJ2);
		jeu.nouvellePartie();
		vues.nouvellePartie();
	}

	public void restaurePartie(String fichier){
		Jeu nouveauJeu = jeu.chargerPartie(fichier);
		if (nouveauJeu == null){
			return;
		}

		this.jeu = nouveauJeu;
		vues.restaurePartie();
		jeu.setEnCours(true);
	}

	@Override
	public void partieSuivante() {
		verifierJeu("Impossible de passer à la partie suivante");
		jeu.nouvellePartie();
	}



	/**Méthode en rapport avec l'interaction HommeMachine */
	@Override//Deplacement en Drag&Drop
	public void dragANDdrop(Coordonne src, Coordonne dst) {
		if (jeu.joueurs[jeu().get_num_JoueurCourant()].jeu(src, dst)) {// MODIF de jeu.n ici
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
			if (jeu.joueurs[jeu.get_num_JoueurCourant()].jeu(depart, arrive )){
				changeJoueur();
				pionSelec = false;
				System.out.println(jeu().getNiveau());
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

	/**Méthode permettant la permutation des joueurs*/
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

			if (jeu().n.PlusdePion(jeu().get_num_JoueurCourant())) {
				jeu().setEnCours(false);
				System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
			}
			//TODO ici l'IA joue instanténément donc problème pour annuler coup en IA vs Humain
			else if (jeu.joueurs[jeu.get_num_JoueurCourant()].tempsEcoule()) //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
				changeJoueur();
			else if (decompte == 0) {
				if (jeu.joueurs[jeu.get_num_JoueurCourant()].estHumain() && jeu.joueurs[jeu.get_num_JoueurCourant()].aPionsNoirs())
					System.out.println("C'est a vous de jouer : L'ATTAQUANT");
				else
					System.out.println("C'est a vous de jouer : LE DEFENSEUR");
				decompte = lenteurAttente;
			}
			else {
				decompte--;
			}
		}
	}
	void changeJoueur() {
		decompte = lenteurAttente;
	}


	/**Méthode en rapport avec l'IHM*/
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
