package Modele;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class Joueurs implements Serializable, Cloneable {
    private final String nom;
    private final TypeJoueur type;
    private final TypePion roleJ;

    private TypePion pions;
    private int nombrePionsManges;
    private int nombreVictoires;
    static int HANDICAP_MAX = 3;

    public Jeu jeu;

    public Joueurs(String nom, TypeJoueur type, TypePion roleJ, Jeu j) {
        requireNonNull(nom, "Le nom du joueur ne doit pas être null");
        requireNonNull(type, "Le type du joueur ne doit pas être null");
        requireNonNull(roleJ, "Le role du joueur ne doit pas être null"); // Normalement ça ne peut pas arriver
        requireNonNull(j, "Le jeu ne doit pas être null");
        this.type = type;
        this.jeu = j;
        this.nom = nom;
        this.roleJ = roleJ;
        initialiserJoueur(roleJ);
    }

    void initialiserJoueur(TypePion roleJ) {
        pions = roleJ;
        nombrePionsManges = 0;

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

    public String nom() {
        return nom;
    }

    public boolean estHumain() {
        return type == TypeJoueur.HUMAIN;
    }

    public TypeJoueur type() {
        return type;
    }

    public void fixeJeuJoueur(Jeu j){
        this.jeu = j;
    }

    public boolean aPionsBlancs() {
        return pions == TypePion.DEFENSEUR;
    }

    public boolean aPionsNoirs() {
        return pions == TypePion.ATTAQUANT;
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

    @Override
    public Joueurs clone() {
        try {
            Joueurs clone = (Joueurs) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
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

    public TypePion typePions() {
        return pions;
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

}
