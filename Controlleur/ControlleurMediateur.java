package Controlleur;
import java.util.ArrayList;

import Global.Configuration;
import Modele.*;
import Vues.*;


public class ControlleurMediateur implements CollecteurEvenements {

	Vues vues;
	IA2 ia1;
	IA2 ia2;
	Animation animIA1, animIA2;
	Animation animDemarrage;
    
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

	public boolean pionSelec = false;
	public boolean deplSelec = false;
	public boolean coupValide = false;
	public Pion selectionne;

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
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.

        Pion caseSelec = jeu.n.getPion(l,c);
		if (caseSelec == null && pionSelec == true){ //ICI on cherche a déplacer
			Coordonne depart = new Coordonne(selectionne.getX(), selectionne.getY());
			Coordonne arrive = new Coordonne(l, c);
			if(!check_clic_selection_dest(l, c)){
				System.out.println("Destination invalide");
			}else{
				if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(depart, arrive )){
					changeJoueur();
					deplSelec = true;
					pionSelec = false; //PASSER A FALSE SEULEMENT SI ON VALIDE LE coup
				}else{
					System.out.println("Coup invalide");
				}
			}
		} 
		else{ //Selection du pion 
			if (check_clic_selection_pion(caseSelec)){ //Vérifie que le Pions choisit est bien de notre Type, joueur 0 implique de jouer les Attaquants et joueur 1 implique de jouer Defenseurs et Roi
				System.out.println(caseSelec);
				pionSelec = true;
				selectionne = caseSelec;
			}
			else{
				System.out.println("Ce pion n'est pas valide");
			}
		}
	}

	@Override
	public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, String nomJ2, TypeJoueur typeJ2) {
		verifierMediateurVues("Impossible de créer une nouvelle partie");
		//jeu = new Jeu();
		jeu.nouveauJoueur(nomJ1, typeJ1);
		jeu.nouveauJoueur(nomJ2, typeJ2);
		jeu.nouvellePartie();
		vues.nouvellePartie();
		initIA(typeJ1,typeJ2);
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
		//jeu().annuler();
	}

	@Override
	public void refaire() {
		//jeu().refaire();
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
		int lenteurAnimationIA = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationIA"));

		switch (typeJ1) {
			case IA_DIFFICILE:
				ia1 = new IA2_difficile(jeu(), jeu().joueur1(), jeu().joueur2(), this);
				break;
			case IA_MOYEN:
				ia1 = new IA2_moyen(jeu(), jeu().joueur1(), jeu().joueur2(), this);
				break;
			case IA_FACILE:
				ia1 = new IA2_facile(jeu(), jeu().joueur1(), jeu().joueur2(), this);
				break;
		}
		if (typeJ1 != TypeJoueur.HUMAIN) {
			animIA1 = new AnimationIA(lenteurAnimationIA, ia1);
		}

		switch (typeJ2) {
			case IA_DIFFICILE:
				ia2 = new IA2_difficile(jeu(),jeu().joueur2(), jeu().joueur1(),this);
				break;
			case IA_MOYEN:
				ia2 = new IA2_moyen(jeu(),jeu().joueur2(), jeu().joueur1(),this);
				break;
			case IA_FACILE:
				ia2 = new IA2_facile(jeu(),jeu().joueur2(), jeu().joueur1(), this);
				break;
		}
		if (typeJ2 != TypeJoueur.HUMAIN) {
			animIA2 = new AnimationIA(lenteurAnimationIA, ia2);
		}
	}

	@Override
	public boolean sauvegarderPartie() {
		//return jeu().sauvegarder();
		return false;
	}

	@Override
	public void chargerPartie(String nomSauvegarde) {
		//jeu = Sauvegarde.charger(nomSauvegarde);
		//initIA(jeu().joueur1().type(), jeu().joueur2().type());
		//vues.nouvellePartie();
		afficherJeu();
	}

	void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

    public void tictac(){
        //System.out.println(joueurCourant);

		if (animDemarrage == null) {
			int lenteurAnimation = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationDemarrage"));
			animDemarrage = new AnimationDemarrage(lenteurAnimation, this);
		}
		if (!animDemarrage.terminee()) {
			animDemarrage.temps();
			return;
		}

        if (jeu.enCours()) {
			if (jeu == null || jeu().partieTerminee() || jeu().getJoueurCourant().estHumain()) {
				return;
			}
			if (jeu().getJoueurCourant() == jeu().joueur1()) {
				animIA1.temps();
			} else {
				animIA2.temps();
			}

			if(PlusdePion(joueurCourant)){
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
						if (joueurs[joueurCourant][type].numJ == 0)
							System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ + " Vous devez déplacer un pion noir ");
						else
							System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ + " Vous devez déplacer un pion blanc ou le roi");
	
						decompte = lenteurAttente;
					}
				} else {
					decompte--;
				}
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

	public boolean PlusdePion(int JC){
		return getPionsDispo(JC).isEmpty();
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
	public boolean check_clic_selection_pion(Pion p) { 
		if (p != null){
			ArrayList<Pion> pions_dispo = getPionsDispo(joueurCourant); 
			return pions_dispo.contains(p);
		}
		//}
		return false;
	}

	public boolean check_Deplacement(ArrayList<Coordonne> liste, Coordonne p) {
		return liste.contains(p);
	}

	public boolean check_clic_selection_dest(int x, int y){
		ArrayList<Coordonne> liste_depl = selectionne.getDeplacement(jeu.n.plateau);
		if (liste_depl.isEmpty()){ //Aucun coup possible pour ce pion
			return false;
		}
		Coordonne arrive = new Coordonne(x, y);
		if(check_Deplacement(liste_depl, arrive)){
			return true;
		}
		return false;
	}

	

}
