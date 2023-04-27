package Modele;

import Modele.Coordonne;
import java.util.ArrayList;

public class Pion implements Cloneable {
    Coordonne coordonne;
    TypePion type; //0 pion Noir, 1 pion Blanc, 


    public Pion(int x, int y, TypePion type){
        coordonne = new Coordonne(x, y);
        this.type = type;
    }

    public Pion(Coordonne coordonne, TypePion type){
        this.coordonne = coordonne;
        this.type = type;
    }

    public Pion(Pion p){
        this.coordonne = new Coordonne(p.coordonne);
        this.type = p.type;
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

    public Coordonne getCoordonne(){
        return coordonne;
    }

    public void setX(int x){
        this.coordonne.x = x;
    }

    public void setY(int y){
        this.coordonne.y = y;
    }

    private boolean emplacementValide(int x, int y) {
        if (x == 0 && (y == 0 || y == 8))
            return false;
        if (x == 8 && (y == 0 || y == 8))
            return false;
        return x != 4 || y != 4;
    }

    private ArrayList<Coordonne> getDeplacementVerticaList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
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
        return deplacement;
    }

    private ArrayList<Coordonne> getDeplacementHorizontaleList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
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
        return deplacement;
    }

    public ArrayList<Coordonne> getDeplacement(Pion[][] plateau){
        ArrayList<Coordonne> deplacement = new ArrayList<Coordonne>();
        getDeplacementVerticaList(plateau, deplacement);
        getDeplacementHorizontaleList(plateau, deplacement);
        return deplacement;
    }
}
