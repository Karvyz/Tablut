package Modele;

import Controlleur.IA_difficile_le_roi_c_ciao;
import Patterns.Observable;
import Structures.Pile;

import java.io.*;
import java.util.Stack;

import static java.util.Objects.requireNonNull;

public class Jeu extends Observable implements Serializable {

    public Niveau n;

    public Joueurs[] joueurs = new Joueurs[2];

    private Joueurs vainqueur;
    private int joueurCourant;
    private boolean enCours;
    public Pile coup_annule;
    public Pile coup_a_refaire;

    public Stack<Coup> pileIA_annule ;
    public Stack<Coup> pileIA_refaire ;

    public ConfigurationJeu config;

    public boolean test_annuler_refaire = false;
    private Coordonne DepartIA;
    private Coordonne ArriveIA;

    private Coup aideIA;

    private boolean debutPartie;

    public Jeu() {
        setEnCours(false);
        joueurs[0] = null; //Pour être sure, peut être inutile
        joueurs[1] = null;
    }

    /**
     * Méthode permettant d'initialiser une partie
     */
    public void nouveauJoueur(String nom, TypeJoueur type, TypePion roleJ) {
        if (joueurs[0] == null && roleJ == TypePion.ATTAQUANT) {
            joueurs[0] = JoueursCreation.createJoueur(nom, type, roleJ, this);
        } else if (joueurs[1] == null && roleJ == TypePion.DEFENSEUR) {
            joueurs[1] = JoueursCreation.createJoueur(nom, type, roleJ, this);

        }/*else if(joueurs[0] != null && roleJ == TypePion.ATTAQUANT){
            joueurs[0] = JoueursCreation.createJoueur(joueurs[0].nom(),joueurs[0].type(),joueurs[0].RoleJoueur(), this);
        } else if (joueurs[1] != null && roleJ == TypePion.DEFENSEUR) {
            joueurs[1] = JoueursCreation.createJoueur(joueurs[1].nom(), joueurs[1].type(), joueurs[1].RoleJoueur(), this);

        } */else {
            throw new IllegalStateException("Impossible d'ajouter un nouveau joueur : tous les joueurs ont déjà été ajoutés");
        }
    }

    public void nouvellePartie() {
        if (joueurs[0] == null || joueurs[1] == null)
            throw new IllegalStateException("Impossible de créer une nouvelle partie : tous les joueurs n'ont pas été ajoutés");

        this.config = new ConfigurationJeu();
        this.n = new Niveau(config);
        if (joueurs[0].aPionsNoirs())
            this.joueurCourant = 0; //Le joueur qui commence est l'attaquant
        else {
            this.joueurCourant = 1;
        }
        this.coup_annule = new Pile();
        this.coup_a_refaire = new Pile();
        this.pileIA_annule =  new Stack<>();
        this.pileIA_refaire =  new Stack<>();
        setDebutPartie(true);
        setEnCours(true);
    }

    /**
     * Méthode utile en fin de partie
     */
    public Joueurs vainqueur() {
        if (!partieTerminee()) {
            return null;
        }
        // TODO : retourner le joueur gagnant
        return vainqueur;
    }

    public boolean partieTerminee() {
        return !enCours();
    }


    /**
     * Méthode en rapport avec les possibilités de jeu
     */
    public int jouer(Coup coup) {
        setAideIA(null);
        setDebutPartie(false);
        this.coup_annule.empiler(this.n.clone());
        int i = n.deplace_pion(coup);
        if (!getJoueurCourant().estHumain()) {
            setCoordooneJouerIA(coup.depart, coup.arrivee);
            pileIA_annule.push(new Coup(coup.depart, coup.arrivee));
        } else {
            setCoordooneJouerIA(null, null);
        }
        System.out.println("Déplacement du pion de (" + coup.depart.getX() + "," + coup.depart.getY() + ") en (" + coup.arrivee.getX() + "," + coup.arrivee.getY() + ")");
        if (i > 0) {
            if (i == 1) {
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
                vainqueur = joueurs[0];
            } else if (i == 2) {
                System.out.println("PARTIE FINI CAR ROI EVADE");
                vainqueur = joueurs[1];
            } else //TODO plus tard
                System.out.println("EGALITE");
            //System.out.println(n); //Affichez le jeu en fin de partie
            setEnCours(false);
        }

        //Si l'IA joue, on ne dépile pas a refaire
        if (!getJoueurCourant().estHumain()) {
            this.coup_a_refaire.clear();
            pileIA_refaire.clear();
        }
        //System.out.println(this);
        joueurSuivant();
        //System.out.println(this);
        metAJour();
        return i;
    }

    public Coup getAideIA() {
        return aideIA;
    }

    public void setAideIA(Coup aideIA) {
        this.aideIA = aideIA;
    }

    public void solution() {
        Coup aide = new IA_difficile_le_roi_c_ciao("", TypePion.ATTAQUANT, this, 50).meilleurCoup();
        setAideIA(aide);
        metAJour();
    }

    public void setCoordooneJouerIA(Coordonne depart, Coordonne arrive) {
        this.DepartIA = depart;
        this.ArriveIA = arrive;
    }

    public Coordonne getCoordooneDepartIA() {
        return this.DepartIA;
    }


    public Coordonne getCoordooneArriveIA() {
        return this.ArriveIA;
    }

    public void annuler() {


        //System.out.print("Annuler : " + jeu().joueurActuel().nom() + " ");
        if (coup_annule.estVide()) {
            System.out.println("Impossible d'annuler");
            return;
        }
        if (!joueurs[0].estHumain() && coup_annule.taille() == 1) {
            System.out.println("Impossible d'annuler");
            return;
        }
        coup_a_refaire.empiler(n.clone()); //stock l'état avant d'annuler
        Niveau restaure = coup_annule.depiler(); //Recupère le niveau précedent
        n = restaure.clone();

        if ((!joueurs[0].estHumain() || !joueurs[1].estHumain())) { // Tester si on a une IA contre un humaion pour annuler le coup de l'IA et de l'humain, ATTENTION,l'IA jouera un autre coup
            if (coup_annule.estVide()) {
                System.out.println("Impossible d'annuler");
                return;
            }
            coup_a_refaire.empiler(n.clone()); //stock l'état avant d'annuler
            restaure = coup_annule.depiler(); //Recupère le niveau précedent
            n = restaure.clone();

            if(!pileIA_annule.isEmpty()){
                Coup a_rempiler = pileIA_annule.pop(); //On supprime le coup joué
                pileIA_refaire.push(a_rempiler);
                if(pileIA_annule.size() == 0){
                    setCoordooneJouerIA(null, null);
                }
                else{
                    Coup sommet = pileIA_annule.peek(); //On récupère le coup a affiche
                    setCoordooneJouerIA(sommet.depart, sommet.arrivee);
                }
            }

            joueurSuivant();
        }

        joueurSuivant(); //La variable du jeu doit aussi être modifie
        metAJour();
        test_annuler_refaire = true;
        setAideIA(null);
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
        joueurSuivant();

        if (!getJoueurCourant().estHumain()) { // Tester si on a une IA contre un humaion pour annuler le coup de l'IA et de l'humain, ATTENTION,l'IA jouera un autre coup
            if (coup_a_refaire.estVide()) {
                System.out.println("Impossible de refaire");
                return;
            }
            coup_annule.empiler(n.clone());
            a_refaire = coup_a_refaire.depiler();
            n = a_refaire.clone();
            Coup sommet = pileIA_refaire.pop();
            pileIA_annule.push(sommet);
            setCoordooneJouerIA(sommet.depart, sommet.arrivee);
            joueurSuivant();

        }

        metAJour();
        test_annuler_refaire = true;
        setAideIA(null);
        System.out.println("Coup refait");
    }



    public void joueurSuivant() {
        //System.out.println("Avant" +joueurCourant);
        joueurCourant = (joueurCourant + 1) % 2;
        //System.out.println("Apres"  +joueurCourant);
    }


    /**
     * Méthode getter et setter pour les joueurs
     */


    public boolean enCours() {
        return enCours;
    }

    public void setEnCours(boolean b) {
        enCours = b;
    }

    public void setAnnuler_refaire(boolean b) {
        test_annuler_refaire = b;
    }

    public int get_num_JoueurCourant() {
        return joueurCourant;
    }

    public void set_num_JoueurCourant(int i) {
        joueurCourant = i;
    }

    public Joueurs getJoueur1() {
        requireNonNull(joueurs[0], "Impossible de récupérer le joueur 1 : le joueur n'a pas été créé");
        return joueurs[0];
    }

    public Joueurs getJoueur2() {
        requireNonNull(joueurs[1], "Impossible de récupérer le joueur 2 : le joueur n'a pas été créé");
        return joueurs[1];
    }

    public Joueurs getJoueurCourant() {
        switch (joueurCourant) {
            case 0:
                return joueurs[0];
            case 1:
                return joueurs[1];
            default:
                return null;
        }
    }

    public Joueurs getJoueurSuivant() {
        switch (joueurCourant) {
            case 0:
                return joueurs[1];
            case 1:
                return joueurs[0];
            default:
                return null;
        }
    }

    public Niveau getNiveau() {
        return n;
    }

    public Joueurs getAttaquant() {
        if (joueurs[0].aPionsNoirs())
            return joueurs[0];
        else
            return joueurs[1];
    }

    public Joueurs getDefenseur() {
        if (joueurs[1].aPionsNoirs())
            return joueurs[1];
        else
            return joueurs[0];
    }

    public void reset() {
        //this.n = new Niveau(config);
        this.coup_annule = new Pile();
        this.coup_a_refaire = new Pile();
        this.pileIA_annule =  new Stack<>();
        this.pileIA_refaire =  new Stack<>();
        this.joueurCourant = 0;
        this.joueurs[0] = null;//TODO a revoir, on doit pas tout le temps mettre a null
        this.joueurs[1] = null;
        this.vainqueur = null;
        this.enCours = false;
        /*this.joueurs[0].fixeJeuJoueur(this);
        this.joueurs[1].fixeJeuJoueur(this);*/
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Jeu \n{");
        sb.append(", enCours: ").append(enCours);
        sb.append("niveau: ").append(n);
        sb.append(", joueurs: [");
        sb.append(joueurs[0]).append(", ");
        sb.append(joueurs[1]);
        sb.append(", joueurCourant: ").append(joueurCourant);
        sb.append("}");
        return sb.toString();
    }

    public int[] info_pion(Joueurs j) {
        int[] info = new int[2];
        if (j.aPionsNoirs()) {
            info[0] = n.nb_pion_nr();
            info[1] = 8 - n.nb_pion_blc();
        } else {
            info[0] = n.nb_pion_blc();
            info[1] = 16 - n.nb_pion_nr();
        }
        return info;
    }

    public Jeu getJeu() {
        return this;
    }


    public boolean debutPartie() {
        return debutPartie;
    }

    private  void setDebutPartie(boolean b){
        debutPartie = b;
    }
}
