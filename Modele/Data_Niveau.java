package Modele;

import java.io.Serializable;
import java.util.Stack;

import Structures.CoordonnePair;
import Structures.Pile;

public class Data_Niveau implements Serializable {
    public Niveau niveau;
    public Pile coup_annule;
    public Pile coup_a_refaire;
    public Stack<CoordonnePair> pileIA_annule;
    public Stack<CoordonnePair> pileIA_refaire;
    private int joueurC;
    public ConfigurationJeu config;

    public Joueurs attaquant;
    public Joueurs defenseur;

    public boolean enCours;

    public Data_Niveau(ConfigurationJeu config, Niveau niveau, Pile coup_annule, Pile coup_a_refaire, Stack<CoordonnePair> pileIA_refaire, Stack<CoordonnePair> pileIA_annule, int JC, Joueurs attaque, Joueurs defense, boolean enCours) {
        this.niveau = niveau;
        this.coup_annule = coup_annule;
        this.coup_a_refaire = coup_a_refaire;
        this.pileIA_annule = pileIA_annule;
        this.pileIA_refaire = pileIA_refaire;
        this.config = config;
        this.joueurC = JC;
        this.attaquant = attaque;
        this.defenseur = defense;
        this.enCours = enCours;
    }

    public int get_JC(){
        return joueurC;
    }
}
