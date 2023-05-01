package Modele;

enum Piece {
    BLANC("Blanc", 1),
    NOIR("Noir", 2),
    ROI("Roi", 4);

    public static final int NOMBRE = values().length;

    private final String nom;
    private final int valeur;

    Piece(String nom, int valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    int valeur() {
        return valeur;
    }

    static Piece depuisValeur(int valeur) {
        switch (valeur) {
            case 1:
                return BLANC;
            case 2:
                return NOIR;
            case 4:
                return ROI;
            default:
                throw new IllegalArgumentException("Aucune pièce correspondant à la valeur " + valeur);
        }
    }

    Pion toPion() {
        switch (this) {
            case BLANC:
                return Pion.BLANC;
            case NOIR:
                return Pion.NOIR;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}