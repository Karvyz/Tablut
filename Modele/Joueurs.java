package Modele;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class Joueurs implements Serializable {
    public int numJ;
    protected Jeu jeu;
    private final TypeJoueur type;
    private final String nom;
    private Pion pions;
    private int nombrePionsManges;
    private int nombreVictoires;
    static int HANDICAP_MAX = 3;

    public Joueurs(TypeJoueur type, Jeu j, String nom) {
        requireNonNull(type, "Le type du joueur ne doit pas être null");
        requireNonNull(j, "Le jeu ne doit pas être null");
        requireNonNull(nom, "Le nom du joueur ne doit pas être null");
        this.type = type;
        this.jeu = j;
        this.nom = nom;
    }

    void initialiserJoueur(Pion p) {
        pions = p;
        nombrePionsManges = 0;
    }

    public String nom() {
        return nom;
    }

    public boolean estHumain() {
        return type == TypeJoueur.HUMAIN;
    }

    public TypeJoueur type() {
        return type;
    }

    public boolean estIaFacile() {
        return type == TypeJoueur.IA_FACILE;
    }

    public boolean estIaMoyen() {
        return type == TypeJoueur.IA_MOYEN;
    }

    public boolean estIaDifficile() {
        return type == TypeJoueur.IA_DIFFICILE;
    }

    public Pion pions() {
        return pions;
    }

    public boolean aPionsBlancs() {
        return pions.getType() == TypePion.DEFENSEUR;
    }

    public boolean aPionsNoirs() {
        return pions.getType() == TypePion.ATTAQUANT;
    }

    public int nombrePionsManges() {
        return nombrePionsManges;
    }

    void ajouterPionManges() {
        nombrePionsManges++;
    }

    public int nombreVictoires() {
        return nombreVictoires;
    }

    void ajouterVictoire() {
        nombreVictoires++;
    }

    void enleverVictoire() {
        if (nombreVictoires == 0) {
            throw new IllegalStateException("Impossible d'enlever une victoire au joueur : aucune victoire");
        }
        nombreVictoires--;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom + "'" +
                ", type=" + type +
                ", pions=" + pions +
                ", nombrePionsManges=" + nombrePionsManges +
                ", nombreVictoires=" + nombreVictoires +
                "}";
    }

    // Méthode appelée pour tous les joueurs lors d'un clic sur le plateau
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
    public boolean jeu(Coordonne i, Coordonne j) {
		return false;
	}

    // Méthode appelée pour tous les joueurs une fois le temps écoulé
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
    public boolean tempsEcoule() {
		return false;
	}
}
