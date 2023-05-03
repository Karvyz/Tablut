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


public class Jeu extends Observable{

    public Niveau n;
    private int joueurCourant;
    private boolean enCours = false;
    public Pile coup_annule;
	public Pile coup_a_refaire;
    private InterfaceGraphique interfaceGraphique;

    public Jeu(){
        nouvellePartie();
    }
    
    /**
     * Crée une nouvelle partie de taille par défaut
     */
    public void nouvellePartie() {
        this.n = new Niveau();
        this.joueurCourant = 0;
        this.coup_annule = new Pile();
        this.coup_a_refaire = new Pile();
        this.enCours = true;
        CollecteurEvenements control = new ControlleurMediateur(this);
        InterfaceGraphique IG = new InterfaceGraphique(this, control);
        this.setInterfaceGraphique(IG);
        InterfaceGraphique.demarrer(this, control, interfaceGraphique);
        metAJour();
    }



    public void nouvellePartie(String fichier) { //Pour charger une partie
       
        this.fermerInterfaceGraphique(); //TODO c'est juste pour faire bo mais il faudra modif

        if (load(fichier)){
            System.out.println("Jeu chargé depuis le fichier: " + fichier);
        }
        else{
            System.out.println("Impossible de charger le jeu depuis: "+ fichier);
        } 
        CollecteurEvenements control = new ControlleurMediateur(this);
        InterfaceGraphique IG = new InterfaceGraphique(this, control);
        this.setInterfaceGraphique(IG);
        InterfaceGraphique.demarrer(this, control, interfaceGraphique);

    }

    public void setInterfaceGraphique(InterfaceGraphique interfaceGraphique) {
        this.interfaceGraphique = interfaceGraphique;
    }

    public void fermerInterfaceGraphique() {
        if (interfaceGraphique != null) {
            interfaceGraphique.fermerFenetrePrincipale();
        }
    }

    public boolean save(String fichier){
        try {
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	
			Data_Niveau data_niveau = new Data_Niveau(this.n, this.coup_annule, this.coup_a_refaire, joueurCourant);
	
			objectOut.writeObject(data_niveau);
			objectOut.close();
			fileOut.close();
            setEnCours(false);
            this.fermerInterfaceGraphique();
            return true;
	
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

    public int getJoueurCourant() { return joueurCourant;}

    public void jouer(Coordonne depart, Coordonne arrive){
        int i = n.deplace_pion(depart, arrive);
        System.out.println("Déplacement du pion de (" + depart.getX() +"," + depart.getY() + ") en (" + arrive.getX() + "," + arrive.getY() +")");
        if (i > 0){
            setEnCours(false);
            if (i == 1)
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
            else if (i == 2)
                System.out.println("PARTIE FINI CAR ROI EVADE");
            else
                System.out.println("EGALITE");
            System.out.println(n);
        }
        
        //TODO test si une partie est finie;
        
        joueurSuivant();
        metAJour();
    }

    

    public boolean load(String fichier){
		Data_Niveau data_niveau;

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

    public void setEnCours(boolean enCours){
        this.enCours = enCours;
    }
}
