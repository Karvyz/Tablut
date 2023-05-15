package Modele;

import Structures.Pile;

import java.util.Stack;

public class GestionnaireDeCoup {
    private Pile coup_annule;
    private Pile coup_a_refaire;
    private Stack<Coup> pileIA_annule;
    private Stack<Coup> pileIA_refaire;
    private Jeu jeu;

    public GestionnaireDeCoup() {

    }

    public void fixeGestionnaire(Jeu jeu, Pile coup_annule, Pile coup_a_refaire, Stack<Coup> pileIA_annule, Stack<Coup> pileIA_refaire){
        this.jeu = jeu;
        this.coup_annule = coup_annule;
        this.coup_a_refaire = coup_a_refaire;
        this.pileIA_annule = pileIA_annule;
        this.pileIA_refaire = pileIA_refaire;
    }


    public void annuler() {
        Joueurs j1 = jeu.getJoueur1();
        Joueurs j2 = jeu.getJoueur2();

        if (coup_annule.estVide()) {//On test dans tous les cas si c'est possible d'annuler au moins un coup ou non
            System.out.println("Aucun coup a annuler"); //TODO ajouter un feedforback sur le bouton ou forward (vert si annuler possible rouge sinon)
            return;
        }

        if(j1.estHumain() && j2.estHumain()){
            annuler_HvH();
        }
        else if(j1.estHumain()) {
            annuler_HvIA();
        }
        else if(j2.estHumain()){
            annuler_IAvH();
        }
        else{
            annuler_IAvIA();
        }
    }

    public void refaire() {
        if (coup_a_refaire.estVide()) {
            System.out.println("Aucun coup n'est a refaire");
            return;
        }

        Joueurs j1 = jeu.getJoueur1();
        Joueurs j2 = jeu.getJoueur2();

        if(j1.estHumain() && j2.estHumain()){
            refaire_HvH();
        } else if (j1.estHumain()) {
            refaire_HvIA();
        }else if (j2.estHumain()){
            refaire_IAvH();
        }
        else{
            refaire_IAvIA();
        }
        jeu.metAJour();
        jeu.test_annuler_refaire = true;

    }

    //Permet d'annuler des coups, soit un soit 2 coups
    private void annule_coups(int nb_coups){
        coup_a_refaire.empiler(jeu.n.clone()); //stock l'état avant d'annuler
        Niveau restaure = coup_annule.depiler(); //Recupère le niveau précedent
        jeu.n = restaure.clone();
        if (nb_coups == 2){
            restaure = coup_annule.depiler(); //Depile le coup qu'avait joué l'humain
            jeu.n = restaure.clone();
        }
    }

    private void annuler_HvH() {
        annule_coups(1);
        jeu.joueurSuivant();
    }

    private void annuler_HvIA() {
        annule_coups(2);

        //le coup de l'ia:
        Coup a_rempiler = pileIA_annule.pop(); //On supprime le coup joué
        pileIA_refaire.push(a_rempiler);
        if(pileIA_annule.size() == 0){
            jeu.setCoordooneJouerIA(null, null);
        }
        else{
            Coup sommet = pileIA_annule.peek(); //On récupère le coup a affiche
            jeu.setCoordooneJouerIA(sommet.depart, sommet.arrivee);
        }

        //Inutile de changer de joueur;

    }

    public void annuler_IAvH(){
        if(coup_annule.size() == 1){
            jeu.setCoordooneJouerIA(null, null);
            System.out.println("L'IA a joué que un coup, on ne peut pas l'annuler");
            return;
        }

        annuler_HvIA(); //On en revient a ce cas là

    }

    public void annuler_IAvIA(){
        annule_coups(2);
        //Inutile de changer de joueur;
    }

    private void refaire_coups(int nb_coups){
        coup_annule.empiler(jeu.n.clone());
        Niveau a_refaire = coup_a_refaire.depiler();
        if (nb_coups == 2){
            coup_annule.empiler(a_refaire.clone());
            a_refaire = coup_a_refaire.depiler();
        }
        jeu.n = a_refaire.clone();
        jeu.metAJour();
        jeu.test_annuler_refaire = true;
        jeu.setAideIA(null);

    }

    private void refaire_HvH(){
        refaire_coups(1);
        jeu.joueurSuivant();
    }

    private void refaire_HvIA(){
        refaire_coups(2);
        Coup sommet = pileIA_refaire.pop();
        pileIA_annule.push(sommet);
        jeu.setCoordooneJouerIA(sommet.depart, sommet.arrivee);
        //inutile de changer de joueur
    }

    public void refaire_IAvH(){
        if(coup_a_refaire.size() <= 1){
            jeu.setCoordooneJouerIA(null, null);
            System.out.println("Impossible de refaire");
            return;
        }

        refaire_HvIA();
    }

    private void refaire_IAvIA() {
        refaire_coups(2);
    }
}