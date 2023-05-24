package Modele;

import Controlleur.IA_DifficileProfondeur;
import Controlleur.heuristiques.HeuristiqueFusion;
import Patterns.Observable;
import Structures.Pile;
import java.io.*;
import java.util.Stack;
import static java.util.Objects.requireNonNull;

public class Jeu extends Observable implements Serializable {
    private static final long serialVersionUID = 1L; //déclare une constante de sérialisation
    public Niveau n;
    public Joueurs[] joueurs = new Joueurs[2];
    private boolean debutPartie;
    private boolean enCours;
    private int joueurCourant;
    private Joueurs vainqueur;
    public Pile coup_annule;
    public Pile coup_a_refaire;
    public Stack<Coup> pileIA_annule ;
    public Stack<Coup> pileIA_refaire ;
    public boolean test_annuler_refaire = false;
    private Coordonne DepartIA;
    private Coordonne ArriveIA;
    private Coup aideIA;
    private boolean coup_joue;
    private final GestionnaireDeCoup gestionnaireDeCoup;
    private boolean consulter;

    private boolean jeu_boucle = false;

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

        this.n = new Niveau();
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

    public void reset() {
        //this.n = new Niveau(config);
        this.coup_annule = new Pile();
        this.coup_a_refaire = new Pile();
        this.pileIA_annule =  new Stack<>();
        this.pileIA_refaire =  new Stack<>();
        this.joueurCourant = 0;
        this.joueurs[0] = null;
        this.joueurs[1] = null;
        this.vainqueur = null;
        this.enCours = false;
        setAideIA(null);
        setCoordooneJouerIA(null,null);
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
        checkvictoire(i);

        //Si l'IA joue, on ne dépile pas a refaire
        if (getJoueurCourant().estHumain()) {
            this.coup_a_refaire.clear();
            pileIA_refaire.clear();
        }
        if (enCours()){
            joueurSuivant();
        }
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
                setAboucle(true);
                System.out.println("Bah normalement il y a égalité");
            }
            setEnCours(false);
        }
    }

    public void solution() {
        Coup aide = new IA_DifficileProfondeur("", TypePion.ATTAQUANT, this, new HeuristiqueFusion(0.4668334F, 0.33374965F,0.9967921F, 0.5499482F), 4).meilleurCoup();
        setAideIA(aide);
        metAJour();
    }

    public boolean peutAnnuler() {
        if(coup_annule.estVide())
            return false;
        return joueurs[0].estHumain() || coup_annule.size() != 1;
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
     * Méthode utile en fin de partie
     */
    public Joueurs vainqueur() {
        if (!partieTerminee()) {
            return null;
        }
        return vainqueur;
    }

    public void setVainqueur(Joueurs vainqueur) {
        this.vainqueur = vainqueur;
    }

    public boolean partieTerminee() {
        return !enCours();
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


    public Coup getAideIA() {
        return aideIA;
    }

    public void setAideIA(Coup aideIA) {
        this.aideIA = aideIA;
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

    public void setAboucle(boolean b){
        jeu_boucle = b;
    }
    public boolean aBoucle(){
        return jeu_boucle;
    }

    public String toString() {
        return "Jeu \n{" +
                ", enCours: " + enCours +
                "niveau: " + n +
                ", joueurs: [" +
                joueurs[0] + ", " +
                joueurs[1] +
                ", joueurCourant: " + joueurCourant +
                "}";
    }


}
