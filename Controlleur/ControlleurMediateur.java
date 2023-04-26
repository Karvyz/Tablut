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
        typeJoueur[1] = FACILE; //type 
	}

    @Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(l, c))
			changeJoueur();
	}

    void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

    public void tictac(){
        System.out.println(joueurCourant);

        if (jeu.enCours()) {
			if (decompte == 0) {
				int type = typeJoueur[joueurCourant];
                System.out.println("Le type: " + type);

				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant][type].tempsEcoule()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					System.out.println("On vous attend, joueur " + joueurs[joueurCourant][type].numJ);
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
    }





}
