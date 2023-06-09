package Modele;

import java.io.Serializable;

public class Coordonne implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L; //déclare une constante de sérialisation
    int x;
    int y;

    public Coordonne(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordonne(Coordonne coordonne) {
        this.x = coordonne.x;
        this.y = coordonne.y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Coordonne)) return false;

        Coordonne other = (Coordonne) obj;
        return this.x == other.x && this.y == other.y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public Coordonne clone() {
        try {
            return (Coordonne) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Ne devrait pas arriver car on implémente Cloneable
        }
    }

}
