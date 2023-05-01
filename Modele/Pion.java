package Modele;

import java.util.ArrayList;

public class Pion {
    public Coordonne coordonne;
    private TypePion type;

    Pion(Coordonne coordonne, TypePion type) {
        this.coordonne = coordonne;
        this.type = type;
    }

    Pion(int x, int y, TypePion type) {
        new Pion(new Coordonne(x, y), type);
    }

    Piece toPiece() {
        switch(type){
            case ATTAQUANT:
                return Piece.NOIR;
            case DEFENSEUR:
                return Piece.BLANC;
            case ROI:
                return Piece.ROI;
            default:
                return null;
        }
    }

    public TypePion getType() {
        return type;
    }
    
    public int getX(){
        return this.coordonne.x;
    }
    
    public int getY(){
        return this.coordonne.y;
    }
    
    public void setX(int x){
        this.coordonne.x = x;
    }
    
    public void setY(int y){
        this.coordonne.y = y;
    }

    @Override
    public String toString() {
        return "Pion sélectionné (" + this.coordonne.x + ", " + this.coordonne.y + ") ";
    }

    // public boolean estCorrect() { //Normalment inutile car gérer par l'IHM
    //     return (getX()>=0 && getX()<9 && getY()>=0 && getY()<9);
    // }

    private boolean emplacementValide(int x, int y) {
        if (x == 0 && (y == 0 || y == 8))
            return false;
        if (x == 8 && (y == 0 || y == 8))
            return false;
        return x != 4 || y != 4;
    }

    private void getDeplacementVerticaList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
        int x = coordonne.x;
        int y = coordonne.y;
        int i = 1;
        //On regarde les cases en haut
        while(x-i >= 0 && plateau[x-i][y] == null){
            if (emplacementValide(x - i, y))
                deplacement.add(new Coordonne(x-i, y));
            i++;
        }
        //On regarde les cases en bas
        i = 1;
        while(x+i < 9 && plateau[x+i][y] == null){
            if (emplacementValide(x + i, y))
                deplacement.add(new Coordonne(x+i, y));
            i++;
        }
    }

    private void getDeplacementHorizontaleList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
        int x = coordonne.x;
        int y = coordonne.y;
        int i = 1;
        //On regarde les cases à gauche
        while(y-i >= 0 && plateau[x][y-i] == null){
            if (emplacementValide(x, y - i))
                deplacement.add(new Coordonne(x, y - i));
            i++;
        }
        //On regarde les cases à droite
        i = 1;
        while(y+i < 9 && plateau[x][y+i] == null){
            if (emplacementValide(x, y + i))
                deplacement.add(new Coordonne(x, y + i));
            i++;
        }
    }

    public ArrayList<Coordonne> getDeplacement(Pion[][] plateau){
        ArrayList<Coordonne> deplacement = new ArrayList<Coordonne>();
        getDeplacementVerticaList(plateau, deplacement);
        getDeplacementHorizontaleList(plateau, deplacement);
        return deplacement;
    }

    public void affiche_liste_deplacement(ArrayList<Coordonne> liste){
        System.out.print("Déplacements possibles { ");
        for(Coordonne c : liste){
            System.out.print("(" + c.getX() + "," + c.getY() +") ");
        }
        System.out.println("}");
    }
}
