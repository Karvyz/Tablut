package Modele;

import java.util.ArrayList;

public class Roi extends Pion {

    public Roi(int x, int y) {
        super(x, y, TypePion.ROI);
    }

    private void getDeplacementVerticaList(Pion[][] plateau, ArrayList<Coordonne> deplacement) {
        int x = getX();
        int y = getY();
        int i = 1;
        //On regarde les cases en haut
        while (x - i >= 0 && plateau[x - i][y] == null) {
            deplacement.add(new Coordonne(x - i, y));
            i++;
        }
        //On regarde les cases en bas
        i = 1;
        while (x + i < 9 && plateau[x + i][y] == null) {
            deplacement.add(new Coordonne(x + i, y));
            i++;
        }
    }

    private void getDeplacementHorizontaleList(Pion[][] plateau, ArrayList<Coordonne> deplacement) {
        int x = getX();
        int y = getY();
        int i = 1;
        //On regarde les cases à gauche
        while (y - i >= 0 && plateau[x][y - i] == null) {
            deplacement.add(new Coordonne(x, y - i));
            i++;
        }
        //On regarde les cases à droite
        i = 1;
        while (y + i < 9 && plateau[x][y + i] == null) {
            deplacement.add(new Coordonne(x, y + i));
            i++;
        }
    }

    @Override
    public ArrayList<Coordonne> getDeplacement(Pion[][] plateau) {
        ArrayList<Coordonne> deplacement = new ArrayList<>();
        getDeplacementVerticaList(plateau, deplacement);
        getDeplacementHorizontaleList(plateau, deplacement);
        return deplacement;
    }

}
