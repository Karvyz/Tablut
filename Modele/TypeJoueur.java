package Modele;

import java.io.Serializable;

public enum TypeJoueur implements Serializable {
    HUMAIN("Humain"),
    IA_FACILE("IA Facile"),
    IA_MOYEN("IA Moyen"),
    IA_DIFFICILE("IA Difficile");
    private static final long serialVersionUID = 1L; //déclare une constante de sérialisation

    private final String nom;

    TypeJoueur(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}