package Modele;

import Patterns.Observable;
import Structures.Pile;
import Vues.InterfaceGraphique;

import java.io.*;
import java.util.Random;
import static java.util.Objects.requireNonNull;

public class Jeu extends Observable implements Serializable {

    public Niveau n;
    private Joueurs joueur1;
    private Joueurs joueur2;

    // Choix du joueur initial aléatoire
    private final Random rand;
    private int choixJoueurDebut = -1;
    //private Sauvegarde sauvegarde;


    private int joueurCourant;
    private boolean enCours = false;
    public Pile coup_annule;
	public Pile coup_a_refaire;
    private InterfaceGraphique interfaceGraphique;
    public ConfigurationJeu config;


    public Jeu(){
        rand = new Random(); //TODO explications de ça ??
    }


    public void nouveauJoueur(String nom, TypeJoueur type) {
        if (joueur1 == null) {
            joueur1 = new Joueurs(type, this, nom);
        }
        else if (joueur2 == null) {
            joueur2 = new Joueurs(type, this, nom);
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

        setEnCours(true);
        System.out.println("Début de la partie");
        this.config = new ConfigurationJeu();
        this.n = new Niveau(config);
        this.joueurCourant = 0; //Attaquant est le joueur 0, soit J1 dans l'interface
        this.coup_annule = new Pile();
        this.coup_a_refaire = new Pile();
        //metAJour();
    }


    public void jouer(Coordonne depart, Coordonne arrive){
        this.coup_annule.empiler(this.n.clone());
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

        this.coup_a_refaire.clear();

        joueurSuivant();
        metAJour();
    }

    public void annuler() {
        //System.out.print("Annuler : " + jeu().joueurActuel().nom() + " ");
        if (coup_annule.estVide()){
            System.out.println("Impossible d'annuler");
            return;
        }
        coup_a_refaire.empiler(n.clone()); //stock l'état avant d'annuler
        Niveau restaure = coup_annule.depiler(); //Recupère le niveau précedent
        n = restaure.clone();
        metAJour();
        joueurSuivant(); //La variable du jeu doit aussi être modifie
        System.out.println("Annulation effectué");
    }


    public void refaire() {
        if (coup_a_refaire.estVide()) {
            System.out.println("Aucun coup n'est a refaire");
            return;
        }
        coup_annule.empiler(n.clone());
        Niveau a_refaire = coup_a_refaire.depiler();
        n = a_refaire.clone();
        metAJour();
        joueurSuivant();
        System.out.println("Coup refait");
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


    public boolean sauvegarderPartie(String fichier){
        try {
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	        System.out.println("Sauvegarde du jeu dans le fichier: " + fichier);
			Data_Niveau data_niveau = new Data_Niveau(this.config, this.n, this.coup_annule, this.coup_a_refaire, joueurCourant, joueur1(), joueur2());
	
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

    public boolean chargerPartie(String fichier){
        Data_Niveau data_niveau = null;

        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            data_niveau = (Data_Niveau) objectIn.readObject();
            this.n = data_niveau.niveau;
            this.coup_annule = data_niveau.coup_annule;
            this.coup_a_refaire = data_niveau.coup_a_refaire;
            this.joueurCourant = data_niveau.joueurCourant;
            this.joueur1 = data_niveau.attaquant;
            this.joueur2 = data_niveau.defenseur;


            objectIn.close();
            fileIn.close();

            System.out.println("Le jeu a été chargé.");

        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + fichier);
            return false;
        } catch (EOFException | InvalidClassException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + fichier);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Classe Data_Niveau introuvable");
            return false;
        }
        return true;
    }
    
    public void joueurSuivant(){
        joueurCourant = (joueurCourant + 1) %2;
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

    public void set_num_JoueurCourant(int i){
        joueurCourant = i;
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
