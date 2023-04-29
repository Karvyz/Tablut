package Controlleur;
import Modele.Coordonne;
import Modele.Jeu;
import Modele.Niveau;
import Modele.Pion;
import Structures.Pile;
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
	
	public Pile coup_annule;
	public Pile coup_a_refaire;
	

	public ControlleurMediateur(Jeu j)  {
		jeu = j;
		joueurs = new Joueurs[2][4];
		typeJoueur = new int[4];
		coup_annule = new Pile();
		coup_a_refaire = new Pile();

		for (int i = 0; i < joueurs.length; i++) {
			joueurs[i][HUMAIN] = new Humain(i, jeu);
			joueurs[i][FACILE] = new IA_facile(i, jeu);
			joueurs[i][MOYEN] = new IA_moyen(i, jeu);
			joueurs[i][DIFFCILE] = new IA_difficile(i, jeu);
			typeJoueur[i] = HUMAIN; //type 
		}


	}
	
	@Override
	public void dragANDdrop(Coordonne src, Coordonne dst){
		Niveau niveau_avant_coup = jeu.n.copy();
		coup_annule.empiler(niveau_avant_coup);
		if (joueurs[joueurCourant][typeJoueur[joueurCourant]].jeu(src, dst)){// MODIF de jeu.n ici
			changeJoueur();
			coup_a_refaire.clear();
		}
		else{
			coup_annule.depiler(); //on dépile car on empile a chaque tentative de drag & drop
		}
	}

    @Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic sur un pion, on affiche ses déplacements possibles
		
		Pion caseSelec = jeu.n.getPion(l,c);
		if (caseSelec == null){
			System.out.println("Cette case ne contient aucun pion");
		}
		else{
			if (!jeu.n.check_clic_selection_pion(caseSelec, joueurCourant)){
				System.out.println("Ce pion ne vous appartient pas");
			}
			else{
				caseSelec.affiche_liste_deplacement(caseSelec.getDeplacement(jeu.n.plateau));
			}
		}
	}
	
    public void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}
	
    public void tictac(){
        if (jeu.enCours()) {
			if(jeu.n.PlusdePion(joueurCourant)){
				jeu.setEnCours(false);
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

	public boolean refaire_coup() {
		if (coup_a_refaire.estVide())
			return false;
	
		coup_annule.empiler(jeu.n.copy());
		Niveau a_refaire = coup_a_refaire.depiler();
		jeu.n = a_refaire.copy();
		jeu.metAJour();
		jeu.joueurSuivant();
		changeJoueur();
		return true;
	
	}
	
	public boolean restaurer_niveau(){
		if (coup_annule.estVide()){
			return false;
		}
		coup_a_refaire.empiler(jeu.n.copy()); //stock l'état avant d'annuler
		Niveau restaure = coup_annule.depiler(); //Recupère le niveau précedent
		jeu.n = restaure.copy();
		jeu.metAJour();
		jeu.joueurSuivant(); //La variable du jeu doit aussi être modifie
		changeJoueur(); //On redonne la main au joueur précedent
		return true;
	}
}
