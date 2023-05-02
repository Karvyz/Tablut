package Modele;

import java.io.Serializable;

import Structures.Pile;

public class Data_Niveau implements Serializable {
        public Niveau niveau;
        public Pile coup_annule;
        public Pile coup_a_refaire;
        public int joueurCourant;
        public ConfigurationJeu config;
    
        public Data_Niveau(ConfigurationJeu config, Niveau niveau, Pile coup_annule, Pile coup_a_refaire, int JC) {
            this.niveau = niveau;
            this.coup_annule = coup_annule;
            this.coup_a_refaire = coup_a_refaire;
            this.config = config;
            this.joueurCourant = JC;
        }
}
