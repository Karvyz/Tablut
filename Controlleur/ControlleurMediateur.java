package Controlleur;
import java.util.ArrayList;

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
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.

        Pion caseSelec = jeu.n.getPion(l,c);
		if (caseSelec == null && pionSelec == true){ //ICI on cherche a déplacer
			Coordonne depart = new Coordonne(selectionne.getX(), selectionne.getY());
			Coordonne arrive = new Coordonne(l, c);
			if(!jeu.n.check_clic_selection_dest(selectionne, l, c)){
				System.out.println("Destination invalide");
			}else{
				if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(depart, arrive )){// MODIF SI ON VEUT VALIDER LE COUP, il faut juste montrer que le coup est possible
					changeJoueur();
					deplSelec = true;
					pionSelec = false; //PASSER A FALSE SEULEMENT SI ON VALIDE LE coup
				}else{
					System.out.println("Coup invalide");
				}
			}
		} 
		else{ //Selection du pion 
			if (jeu.n.check_clic_selection_pion(caseSelec, joueurCourant)){ //Vérifie que le Pions choisit est bien de notre Type, joueur 0 implique de jouer les Attaquants et joueur 1 implique de jouer Defenseurs et Roi
				System.out.println(caseSelec);
				pionSelec = true;
				deplSelec = false; //C'est important si on veut valider le coup plus tard
				selectionne = caseSelec;
			}
			else{
				System.out.println("Ce pion n'est pas valide");
			}
		}
	}

    void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
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

}
