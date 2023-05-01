package Modele;

import Patterns.Observable;

import java.util.Random;

import static java.util.Objects.requireNonNull;

public class Jeu extends Observable{

    public Niveau n;
    public boolean enCours = false;
    private Joueurs joueur1;
    private Joueurs joueur2;
    private int joueurCourant;

    //private Historique historique;

    // Choix du joueur initial aléatoire
    private final Random rand;
    private int choixJoueurDebut = -1;
    //private Sauvegarde sauvegarde;

    public Jeu(){
        rand = new Random();
        //nouvellePartie();
        //System.out.println(n);
    }

    public void nouveauJoueur(String nom, TypeJoueur type) {
        if (joueur1 == null) {
            joueur1 = new Joueurs(type, this, nom);
        }
        else if (joueur2 == null) {
            joueur2 = new Joueurs(type, this, nom);
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

        // L'attaquant commence toujours (
        if(joueur1.aPionsNoirs()) {
            joueurCourant = 0;
        }
        else {
            joueurCourant = 1;
        }

        this.n = new Niveau();
        enCours = true;
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
        // TODO : vérifier si la partie est terminée
        return false;
    }

    public Joueurs joueur1() {
        requireNonNull(joueur1, "Impossible de récupérer le joueur 1 : le joueur n'a pas été créé");
        return joueur1;
    }

    public Joueurs joueur2() {
        requireNonNull(joueur2, "Impossible de récupérer le joueur 2 : le joueur n'a pas été créé");
        return joueur2;
    }

    public Joueurs joueurCourant() {
        if (joueurCourant == 0) {
            return joueur1();
        }
        return joueur2();
    }


    public void jouer(Coordonne depart, Coordonne arrive){
        int i = n.deplace_pion(depart, arrive);
        System.out.println("Le joueur " + joueurCourant + " a déplacé le piont Noir de (" + depart.getX() +"," + depart.getY() + ") en (" + arrive.getX() + "," + arrive.getY() +")");
        if (i > 0){
            enCours = false;
            if (i == 1)
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
            else
                System.out.println("PARTIE FINI CAR ROI EVADE");
        }

        //TODO test si une partie est finie;

        joueurCourant = (joueurCourant + 1) %2;
        metAJour();
    }

    //On regarde si le joueur a manger un pion adverse
    


    public boolean enCours(){
        return enCours;
    }

    public Joueurs getJoueurCourant(){
        return switch (joueurCourant) {
            case 0 -> joueur1;
            case 1 -> joueur2;
            default -> null;
        };
    }

    public Joueurs getJoueurSuivant(){
        return switch (joueurCourant) {
            case 0 -> joueur2;
            case 1 -> joueur1;
            default -> null;
        };
    }
}
