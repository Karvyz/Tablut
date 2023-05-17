package Modele;

import Controlleur.IA_DifficileTemps;
import Controlleur.heuristiques.HeuristiqueLeRoiCCiao;
import Patterns.Observable;
import Structures.Pile;

import java.io.*;
import java.util.Stack;

import static java.util.Objects.requireNonNull;

public class Jeu extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private GestionnaireDeCoup gestionnaireDeCoup;

    private boolean coup_joue;

    private boolean consulter;


    public Jeu() {
        setEnCours(false);
        joueurs[0] = null; //Pour être sure, peut être inutile
        joueurs[1] = null;
        gestionnaireDeCoup = new GestionnaireDeCoup();
    }


    /**
     * Méthode permettant d'initialiser une partie
     */
    public void nouveauJoueur(String nom, TypeJoueur type, TypePion roleJ) {
        if (joueurs[0] == null && roleJ == TypePion.ATTAQUANT) {
            joueurs[0] = JoueursCreation.createJoueur(nom, type, roleJ, this);
        } else if (joueurs[1] == null && roleJ == TypePion.DEFENSEUR) {
            joueurs[1] = JoueursCreation.createJoueur(nom, type, roleJ, this);
        }
        else {
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

    public void setVainqueur(Joueurs vainqueur) {
        this.vainqueur = vainqueur;
    }

    public boolean partieTerminee() {
        return !enCours();
    }


    /**
     * Méthode en rapport avec les possibilités de jeu
     */
    public int jouer(Coup coup) {
        setAideIA(null);
        setCoupJoue(true);
        this.coup_annule.empiler(this.n.clone());
        int i = n.deplace_pion(coup);
        if (!getJoueurCourant().estHumain()) {
            setCoordooneJouerIA(coup.depart, coup.arrivee);
            pileIA_annule.push(new Coup(coup.depart, coup.arrivee));
        } else {
            setCoordooneJouerIA(null, null);
        }
        //System.out.println("Déplacement du pion de (" + coup.depart.getX() + "," + coup.depart.getY() + ") en (" + coup.arrivee.getX() + "," + coup.arrivee.getY() + ")");
        checkvictoire(i);

        //Si l'IA joue, on ne dépile pas a refaire
        if (getJoueurCourant().estHumain()) {
            this.coup_a_refaire.clear();
            pileIA_refaire.clear();
        }
        //System.out.println(this);
        if (enCours()){
            joueurSuivant();
        }
        //System.out.println(this);
        metAJour();
        return i;
    }

    private void checkvictoire(int i) {
        if (i > 0) {
            if (i == 1) {
                vainqueur = joueurs[0];
                System.out.println("Victoire des Attaquants");
            } else if (i == 2) {
                vainqueur = joueurs[1];
                System.out.println("Victoire des Défenseurs");
            } else {
                vainqueur = getAttaquant(); // pourquoi ???
                System.out.println("Bah normalement il y a égalité");
            }
            setEnCours(false);
        }
    }

    public Coup getAideIA() {
        return aideIA;
    }

    public void setAideIA(Coup aideIA) {
        this.aideIA = aideIA;
    }

    public void solution() {
        Coup aide = new IA_DifficileTemps("", TypePion.ATTAQUANT, this, new HeuristiqueLeRoiCCiao(), 50).meilleurCoup();
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

    public boolean peutAnnuler() {
        if(coup_annule.estVide())
            return false;
        return joueurs[0].estHumain() || coup_annule.size() != 1;
        // Inutile je pense ??
        /*
        if ((!joueurs[0].estHumain() || !joueurs[1].estHumain())) { // Tester si on a une IA contre un humaion pour annuler le coup de l'IA et de l'humain, ATTENTION,l'IA jouera un autre coup
            if (coup_annule.estVide())
                return false;
        }
         */
    }

    public void annuler() {
        gestionnaireDeCoup.fixeGestionnaire(this, coup_annule, coup_a_refaire, pileIA_annule, pileIA_refaire);
        this.gestionnaireDeCoup.annuler();
    }


    public boolean peutRefaire() {
        return !coup_a_refaire.estVide();
    }

    public void refaire() {
        gestionnaireDeCoup.fixeGestionnaire(this, coup_annule, coup_a_refaire, pileIA_annule, pileIA_refaire);
        this.gestionnaireDeCoup.refaire();

    }

    public void joueurSuivant() {
        joueurCourant = (joueurCourant + 1) % 2;
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
        //this.coup_joue = new Stack<>();
        //this.coup_joue_refaire = new Stack<>();
        this.joueurCourant = 0;
        this.joueurs[0] = null;//TODO a revoir, on doit pas tout le temps mettre a null
        this.joueurs[1] = null;
        this.vainqueur = null;
        this.enCours = false;
        setAideIA(null);
        setCoordooneJouerIA(null,null);


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

    public void setDebutPartie(boolean b){
        //
        debutPartie = b;
    }

    public void setCoupJoue(boolean b){
        coup_joue = b;
    }
    public boolean getCoupJoue(){
        return coup_joue;
    }

    public boolean getConsulter() {return consulter;}

    public void setConsulter(boolean consulter) {this.consulter = consulter;}


}
