package Modele;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class Joueurs implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;//déclare une constante de sérialisation
    private final String nom;
    private final TypeJoueur type;
    private TypePion pions;
    private int nombrePionsManges;
    public Jeu jeu;

    public Joueurs(String nom, TypeJoueur type, TypePion roleJ, Jeu j) {
        requireNonNull(nom, "Le nom du joueur ne doit pas être null");
        requireNonNull(type, "Le type du joueur ne doit pas être null");
        requireNonNull(roleJ, "Le role du joueur ne doit pas être null"); // Normalement ça ne peut pas arriver
        requireNonNull(j, "Le jeu ne doit pas être null");
        this.type = type;
        this.jeu = j;
        this.nom = nom;
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
                "}";
    }

    @Override
    public Joueurs clone() {
        try {
            return (Joueurs) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public TypePion typePions() {
        return pions;
    }

}
