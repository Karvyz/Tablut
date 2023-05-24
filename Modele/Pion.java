package Modele;

import java.io.Serializable;
import java.util.ArrayList;


public class Pion implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L; //déclare une constante de sérialisation
    private Coordonne coordonne;
    private TypePion type; //0 pion Noir, 1 pion Blanc, 

    public Pion(int x, int y, TypePion type) {
        coordonne = new Coordonne(x, y);
        this.type = type;
    }

    public Pion(Coordonne coordonne, TypePion type) {
        this.coordonne = coordonne;
        this.type = type;
    }

    public TypePion getType() {
        return type;
    }

    public Coordonne getCoordonne() {
        return coordonne;
    }

    public int getX() {
        return this.coordonne.x;
    }

    public int getY() {
        return this.coordonne.y;
    }

    public void setX(int x) {
        this.coordonne.x = x;
    }

    public void setY(int y) {
        this.coordonne.y = y;
    }

    public void setCoordonne(Coordonne c) {
        this.coordonne = c;
    }

    private boolean emplacementValide(int x, int y) {
        if (x == 0 && (y == 0 || y == 8))
            return false;
        if (x == 8 && (y == 0 || y == 8))
            return false;
        return x != 4 || y != 4;
    }

    private void getDeplacementVerticaList(Pion[][] plateau, ArrayList<Coordonne> deplacement) {
        int x = coordonne.x;
        int y = coordonne.y;
        int i = 1;
        //On regarde les cases en haut
        while (x - i >= 0 && plateau[x - i][y] == null) {
            if (emplacementValide(x - i, y))
                deplacement.add(new Coordonne(x - i, y));
            i++;
        }
        //On regarde les cases en bas
        i = 1;
        while (x + i < 9 && plateau[x + i][y] == null) {
            if (emplacementValide(x + i, y))
                deplacement.add(new Coordonne(x + i, y));
            i++;
        }
    }

    private void getDeplacementHorizontaleList(Pion[][] plateau, ArrayList<Coordonne> deplacement) {
        int x = coordonne.x;
        int y = coordonne.y;
        int i = 1;
        //On regarde les cases à gauche
        while (y - i >= 0 && plateau[x][y - i] == null) {
            if (emplacementValide(x, y - i))
                deplacement.add(new Coordonne(x, y - i));
            i++;
        }
        //On regarde les cases à droite
        i = 1;
        while (y + i < 9 && plateau[x][y + i] == null) {
            if (emplacementValide(x, y + i))
                deplacement.add(new Coordonne(x, y + i));
            i++;
        }
    }

    public ArrayList<Coordonne> getDeplacement(Pion[][] plateau) {
        ArrayList<Coordonne> deplacement = new ArrayList<>();
        getDeplacementVerticaList(plateau, deplacement);
        getDeplacementHorizontaleList(plateau, deplacement);
        return deplacement;
    }

    @Override
    public String toString() {
        return "Pion selectionne (" + this.coordonne.x + ", " + this.coordonne.y + ", type=" + this.getType() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Pion other = (Pion) obj;
        return this.coordonne.equals(other.coordonne) && this.type == other.type;
    }

    @Override
    public Pion clone() {
        try {
            Pion copie = (Pion) super.clone();
            copie.coordonne = new Coordonne(this.coordonne.getX(), this.coordonne.getY());
            copie.type = this.type; // Pas besoin de cloner si TypePion est une enum ou un type immuable
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("La classe Pion doit être cloneable", e);
        }
    }
}
