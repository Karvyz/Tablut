package Modele;

import Modele.Coordonne;
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

    public ArrayList<Coordonne> getDeplacementVerticaList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
        int x = this.x;
        int y = this.y;
        int i = 1;
        //On regarde les cases en haut
        while(x-i >= 0 && plateau[x-i][y] == null){
            deplacement.add(new Coordonne(x-i, y));
            i++;
        }
        //On regarde les cases en bas
        i = 1;
        while(x+i < 9 && plateau[x+i][y] == null){
            deplacement.add(new Coordonne(x+i, y));
            i++;
        }
        return deplacement;
    }

    public ArrayList<Coordonne> getDeplacementHorizontaleList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
        int x = this.x;
        int y = this.y;
        int i = 1;
        //On regarde les cases à gauche
        while(y-i >= 0 && plateau[x][y-i] == null){
            deplacement.add(new Coordonne(x, y-i));
            i++;
        }
        //On regarde les cases à droite
        i = 1;
        while(y+i < 9 && plateau[x][y+i] == null){
            deplacement.add(new Coordonne(x, y+i));
            i++;
        }
        return deplacement;
    }

    public ArrayList<Coordonne> getDeplacement(Pion[][] plateau){
        int x = this.x;
        int y = this.y;
        ArrayList<Coordonne> deplacement = new ArrayList<Coordonne>();
        deplacement = getDeplacementVerticaList(plateau, deplacement);
        deplacement = getDeplacementHorizontaleList(plateau, deplacement);
        return deplacement;
    }


}
