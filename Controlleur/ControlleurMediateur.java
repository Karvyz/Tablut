package Controlleur;

import Modele.Coordonne;
import Modele.Jeu;
import Modele.Niveau;
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
	final int lenteurAttente = 50;
	int decompte;
	private Pion selectionne;
	private boolean pionSelec = false;

	public ControlleurMediateur(Jeu j)  {
		jeu = j;
		joueurs = new Joueurs[2][4];
		typeJoueur = new int[4];

		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][HUMAIN] = new Humain(i, jeu);
			joueurs[i][FACILE] = new IA_facile(i, jeu);
            joueurs[i][MOYEN] = new IA_moyen(i, jeu);
            joueurs[i][DIFFCILE] = new IA_difficile_Long_live_the_king(i, jeu);
			//typeJoueur[i] = DIFFCILE; //type
			typeJoueur[i] = HUMAIN; //type

		}
		//joueurs[1][DIFFCILE] = new IA_facile(1, jeu);
	}
	
	@Override//Deplacement en Drag&Drop
	public void dragANDdrop(Coordonne src, Coordonne dst){
		if (joueurs[jeu.getJoueurCourant()][typeJoueur[jeu.getJoueurCourant()]].jeu(src, dst)){// MODIF de jeu.n ici
			changeJoueur();
		}
	}

    @Override//Déplacement au clic
	public void clicSouris(int l, int c) {
		// Lors d'un clic sur un pion, on affiche ses déplacements possibles
		
		Pion caseSelec = jeu.n.getPion(l,c);

		if (caseSelec == null && pionSelec == true){ //ICI on cherche a déplacer
			Coordonne depart = new Coordonne(selectionne.getX(), selectionne.getY());
			Coordonne arrive = new Coordonne(l, c);
			if (joueurs[jeu.getJoueurCourant()][typeJoueur[jeu.getJoueurCourant()]].jeu(depart, arrive )){
				changeJoueur();
				pionSelec = false; 
			}
		} 
		else{ //Selection du pion 
			if (jeu.n.check_clic_selection_pion(caseSelec, jeu.getJoueurCourant())){ 
				pionSelec = true;
				selectionne = caseSelec.clone(); //on stock le pion selectionne
				caseSelec.affiche_liste_deplacement(caseSelec.getDeplacement(jeu.n.plateau));
			}
			else{
				System.out.println("Ce pion ne vous appartient pas");
			}
		}
	}
	
	//Passe au joueur suivant
    public void changeJoueur() {
		decompte = lenteurAttente;
	}
	

    public void tictac(){
        if (jeu.enCours()) {
			if(jeu.n.PlusdePion(jeu.getJoueurCourant())){
				jeu.setEnCours(false);
				System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
			}else{
				
				if (decompte == 0) {
					int type = typeJoueur[jeu.getJoueurCourant()];
	
					// Lorsque le temps est écoulé on le transmet au joueur courant.
					// Si un coup a été joué (IA) on change de joueur.
					if (joueurs[jeu.getJoueurCourant()][type].tempsEcoule()) { //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
						changeJoueur();
					} else {
					// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.

						if (joueurs[jeu.getJoueurCourant()][type].numJ == 0)
							System.out.println("On vous attend, joueur " + joueurs[jeu.getJoueurCourant()][type].numJ + " vous devez déplacer un pion noir ");
						else
							System.out.println("On vous attend, joueur " + joueurs[jeu.getJoueurCourant()][type].numJ + " vous devez déplacer un pion blanc ou le roi");
	
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