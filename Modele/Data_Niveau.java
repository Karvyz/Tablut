package Modele;

import java.io.Serializable;
import java.util.Stack;
import Structures.Pile;

public class Data_Niveau implements Serializable {
    private static final long serialVersionUID = 1L;//déclare une constante de sérialisation
    public Niveau niveau;
    public Pile coup_annule;
    public Pile coup_a_refaire;
    public Stack<Coup> pileIA_annule;
    public Stack<Coup> pileIA_refaire;
    private final int joueurC;
    public Joueurs attaquant;
    public Joueurs defenseur;
    public boolean enCours;

    /**Classe permettant de stocker un objet de la classe pour la sauvegarde*/
    public Data_Niveau( Niveau niveau, Pile coup_annule, Pile coup_a_refaire, Stack<Coup> pileIA_annule, Stack<Coup> pileIA_refaire, int JC, Joueurs attaque, Joueurs defense, boolean enCours) {
        this.niveau = niveau;
        this.coup_annule = coup_annule;
        this.coup_a_refaire = coup_a_refaire;
        this.pileIA_annule = pileIA_annule;
        this.pileIA_refaire = pileIA_refaire;
        this.joueurC = JC;
        this.attaquant = attaque;
        this.defenseur = defense;
        this.enCours = enCours;
    }

    public int get_JC(){
        return joueurC;
    }
}
