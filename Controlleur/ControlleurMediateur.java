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

    void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}

    public void tictac(){
        //System.out.println(joueurCourant);

        if (jeu.enCours()) {
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
