package Modele;

import Patterns.Observable;
import Structures.Pile;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Controlleur.ControlleurMediateur;


import java.util.Random;

import static java.util.Objects.requireNonNull;

public class Jeu extends Observable{

    public Niveau n;
    private Joueurs joueur1;
    private Joueurs joueur2;

    //private Historique historique;

    // Choix du joueur initial aléatoire
    private final Random rand;
    private int choixJoueurDebut = -1;
    //private Sauvegarde sauvegarde;

    
    private int joueurCourant;
    private boolean enCours = false;
    public Pile coup_annule;
	public Pile coup_a_refaire;
    private InterfaceGraphique interfaceGraphique;
    private Configuration config;
    
    
    public Jeu(){
        rand = new Random();
        //nouvellePartie();
        //System.out.println(n);
    }
    public void nouveauJoueur(String nom, TypeJoueur type) {
        if (joueur1 == null) {
            joueur1 = new Joueurs(type, this, nom);
            //joueur1.pions().setType(TypePion.ATTAQUANT);
        }
        else if (joueur2 == null) {
            joueur2 = new Joueurs(type, this, nom);
            //joueur2.pions().setType(TypePion.DEFENSEUR);
            //sauvegarde = new Sauvegarde(this);
        }
        else {
            throw new IllegalStateException("Impossible d'ajouter un nouveau joueur : tous les joueurs ont déjà été ajoutés");
        }
    }
    /**
     * Crée une nouvelle partie de taille par défaut
     */
    public void nouvellePartie() {
        if(joueur1 == null || joueur2 == null)
            throw new IllegalStateException("Impossible de créer une nouvelle partie : tous les joueurs n'ont pas été ajoutés");


        enCours = true;
        this.config = new Configuration();
        this.n = new Niveau(config);
        this.joueurCourant = 0;
        this.coup_annule = new Pile();
        this.coup_a_refaire = new Pile();
        this.enCours = true;
        //CollecteurEvenements control = new ControlleurMediateur(this);
        // InterfaceGraphique IG = new InterfaceGraphique(this, control);
        // this.setInterfaceGraphique(IG);
        // InterfaceGraphique.demarrer(this, control, interfaceGraphique);
        metAJour();
    }

    public Joueurs vainqueur() {
        if (!partieTerminee()) {
            return null;
        }
        // TODO : retourner le joueur gagnant
        return null;
    }

    public boolean partieTerminee() {
        if(n==null)
            return false;
        return n.estTermine();
    }

    public Joueurs joueur1() {
        requireNonNull(joueur1, "Impossible de récupérer le joueur 1 : le joueur n'a pas été créé");
        return joueur1;
    }

    public Joueurs joueur2() {
        requireNonNull(joueur2, "Impossible de récupérer le joueur 2 : le joueur n'a pas été créé");
        return joueur2;
    }

    public void nouvellePartie(String fichier) { //Pour charger une partie
       
        //this.fermerInterfaceGraphique(); //TODO c'est juste pour faire bo mais il faudra modif

        if (load(fichier)){
            System.out.println("Jeu chargé depuis le fichier: " + fichier);
        }
        else{
            System.out.println("Impossible de charger le jeu depuis: "+ fichier);
        } 
        // CollecteurEvenements control = new ControlleurMediateur(this);
        // InterfaceGraphique IG = new InterfaceGraphique(this, control);
        // this.setInterfaceGraphique(IG);
        // InterfaceGraphique.demarrer(this, control, interfaceGraphique);

    }

    public void setInterfaceGraphique(InterfaceGraphique interfaceGraphique) {
        this.interfaceGraphique = interfaceGraphique;
    }

    // public void fermerInterfaceGraphique() {
    //     if (interfaceGraphique != null) {
    //         interfaceGraphique.fermerFenetrePrincipale();
    //     }
    // }

    public boolean save(String fichier){
        try {
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	
			Data_Niveau data_niveau = new Data_Niveau(this.config, this.n, this.coup_annule, this.coup_a_refaire, joueurCourant);
	
			objectOut.writeObject(data_niveau);
			objectOut.close();
			fileOut.close();
            setEnCours(false);
            //this.fermerInterfaceGraphique();
            return true;
	
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }


    public void jouer(Coordonne depart, Coordonne arrive){
		this.coup_annule.empiler(this.n.clone());
        int i = n.deplace_pion(depart, arrive);
        System.out.println("Déplacement du pion de (" + depart.getX() +"," + depart.getY() + ") en (" + arrive.getX() + "," + arrive.getY() +")");
        if (i > 0){
            setEnCours(false);
            if (i == 1)
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
            else
                System.out.println("PARTIE FINI CAR ROI EVADE");
            System.out.println(n);
        }

        //TODO test si une partie est finie;

        this.coup_a_refaire.clear();

        joueurSuivant();
        metAJour();
    }

    

    
    public boolean load(String fichier){
        Data_Niveau data_niveau = null;
        
		try {
            FileInputStream fileIn = new FileInputStream(fichier);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
			data_niveau = (Data_Niveau) objectIn.readObject();
			this.n = data_niveau.niveau;
			this.coup_annule = data_niveau.coup_annule;
			this.coup_a_refaire = data_niveau.coup_a_refaire;
			this.joueurCourant = data_niveau.joueurCourant;
            setEnCours(false);
            
			objectIn.close();
			fileIn.close();
            
			System.out.println("Le jeu a été chargé.");
			return true;

		} catch (FileNotFoundException e) {
			System.err.println("Fichier non trouvé : " + fichier);
		} catch (EOFException | InvalidClassException e) {
			System.err.println("Erreur lors de la lecture du fichier : " + fichier);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
            System.err.println("Classe Data_Niveau introuvable");
		}
		return false;
	}
    
    //On regarde si le joueur a manger un pion adverse
    
    public void joueurSuivant(){
        joueurCourant = (joueurCourant + 1) %2;
    }
    
    public int joueurCourant(){
        return joueurCourant;
    }
    
    public boolean enCours(){
        return enCours;
    }

    public void setEnCours(boolean b) {
        enCours = b;
    }
    
    
    public int get_num_JoueurCourant(){
        return joueurCourant;
    }

    public Joueurs getJoueurCourant(){
        switch (joueurCourant) {
            case 0:
                return joueur1;
            case 1:
                return joueur2;
            default :
                return null;
        }
    }

    public Joueurs getJoueurSuivant(){
        switch (joueurCourant) {
            case 0 :
                return joueur2;
            case 1 :
                return joueur1;
            default :
                return null;
        }
    }

    public Niveau getNiveau(){
        return n;
    }

    public Joueurs getAttaquant(){
        if (joueur1.aPionsNoirs())
            return joueur1;
        else
            return joueur2;
    }

    public Joueurs getDefenseur(){
        if (joueur1.aPionsNoirs())
            return joueur2;
        else
            return joueur1;
    }
}
