package Modele;

import java.util.ArrayList;

enum TypePion {
    ATTAQUANT,
    DEFENSEUR,
    ROI
}

public class Pion {
    int x;
    int y;
    TypePion type; //0 pion Noir, 1 pion Blanc, 


    public Pion(int x, int y, TypePion type){
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public TypePion getType() {
        return type;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public ArrayList<Coordonne> 


}
