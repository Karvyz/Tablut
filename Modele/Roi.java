package Modele;

import java.util.ArrayList;

public class Roi extends Pion {

    public Roi(int x, int y) {
        super(x, y, TypePion.ROI);
        //TODO Auto-generated constructor stub
    }

    public Roi(Coordonne coordonne) {
        super(coordonne, TypePion.ROI);
        //TODO Auto-generated constructor stub
    }

    private ArrayList<Coordonne> getDeplacementVerticaList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
        int x = coordonne.x;
        int y = coordonne.y;
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

    private ArrayList<Coordonne> getDeplacementHorizontaleList(Pion[][] plateau, ArrayList<Coordonne> deplacement){
        int x = coordonne.x;
        int y = coordonne.y;
        int i = 1;
        //On regarde les cases à gauche
        while(y-i >= 0 && plateau[x][y-i] == null){
            deplacement.add(new Coordonne(x, y - i));
            i++;
        }
        //On regarde les cases à droite
        i = 1;
        while(y+i < 9 && plateau[x][y+i] == null){
            deplacement.add(new Coordonne(x, y + i));
            i++;
        }
        return deplacement;
    }

    @Override
    public ArrayList<Coordonne> getDeplacement(Pion[][] plateau){
        ArrayList<Coordonne> deplacement = new ArrayList<Coordonne>();
        getDeplacementVerticaList(plateau, deplacement);
        getDeplacementHorizontaleList(plateau, deplacement);
        return deplacement;
    }
    
}
