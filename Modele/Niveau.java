package Modele;
import java.util.ArrayList;

import Modele.Pion;
import Modele.Roi;

public class Niveau {
    public static final int NOIR = 0;
    public static final int BLANC = 1;
    public static final int ROI = 2;
    int taille = 9;

    public Pion [][] plateau = new Pion[taille][taille];



    //On creer le plateau de jeu
    public Niveau() {
        init_Niveau();
    }

    //On initialise le plateau de jeu
    public void init_Niveau() {
        int [][] pos_attaquants = new int[][] {{0,3}, {0,4}, {0,5}, {1,4}, {3,0}, {4,0}, {5,0}, {4,1}, {8,3}, {8,4}, {8,5}, {7,4}, {4,7}, {3,8}, {4,8}, {5,8}};
        int [][] pos_defenseurs = new int[][] {{2,4}, {3,4}, {4,2}, {4,3}, {5,4}, {6,4}, {4,5}, {4,6}};

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                plateau[i][j] = null;
            }
        }

        Roi r = new Roi(4, 4);
        plateau[4][4] = r;

        for(int i=0; i<16; i++){
            int x = pos_attaquants[i][0];
            int y= pos_attaquants[i][1];
            Pion p = new Pion(x, y, TypePion.ATTAQUANT);
            plateau[x][y] = p;
        }

        for(int i=0; i<8; i++){
            int x = pos_defenseurs[i][0];
            int y= pos_defenseurs[i][1];
            Pion p = new Pion(x, y, TypePion.DEFENSEUR);
            plateau[x][y] = p;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Pion p = plateau[i][j];
                if (p == null) {
                    sb.append("- ");
                } else if (p.getType() == TypePion.ATTAQUANT) {
                    sb.append("N ");
                } else if (p.getType() == TypePion.DEFENSEUR) {
                    sb.append("B ");
                } else if (p.getType() == TypePion.ROI) {
                    sb.append("R ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    //On regarde la taille du plateau
    public int getTaille() {
        return taille;
    }

    public Pion getPion(int x,int y){
        return plateau[x][y];
    }


    //On regarde si la case est vide
    public boolean estVide(int x, int y) {
        return plateau[x][y] == null;
    }
    //On regarde si la case est vide
    public boolean estVide(Pion p) {
        if (plateau[p.getX()][p.getY()] == null){
            return true;
        }
        return false;
    }

    //On regarde si la case est noire
    public boolean estAttaquant(int x, int y) {
        if (!estCorrect(x,y) || plateau[x][y] == null) {
            return false;
        }
        return plateau[x][y].getType() == TypePion.ATTAQUANT;
    }

    public boolean estAttaquant(Pion p) {
        if (p == null) {
            return false;
        }
        return p.getType() == TypePion.ATTAQUANT;
    }

    //On regarde si la case est blanche
    public boolean estDefenseur(int x, int y) {
        if (!estCorrect(x,y) || plateau[x][y] == null) {
            return false;
        }
        return plateau[x][y].getType() == TypePion.DEFENSEUR;
    }

    //On regarde si la case est blanche
    public boolean estDefenseur(Pion p) {
        if (p == null) {
            return false;
        }
        return p.getType() == TypePion.DEFENSEUR;
    }

    //On regarde si la case est le roi
    public boolean estRoi(int x, int y) {
        if (!estCorrect(x,y) || plateau[x][y] == null) {
            return false;
        }
        return plateau[x][y].getType() == TypePion.ROI;
    }

    public boolean estRoi(Pion p) {
        if (p == null) {
            return false;
        }
        return p.getType() == TypePion.ROI;
    }

    //On place une case vide sur le plateau aux coordonnées x et y (cas ou on capture par exemple)
    public void setVide(int x, int y) {
        plateau[x][y] = null;
    }

    public boolean estCorrect(int x, int y){
        return x>=0 && x<9 && y>=0 && y<9;
    }

    //Renvoi la liste d'un seul type de joueur.
    public ArrayList<Pion> getPions(TypePion type){
        ArrayList<Pion> liste = new ArrayList<>();
        for (int x=0; x<taille; x++){
            for (int y=0; y< taille; y++){
                Pion courant = plateau[x][y];
                if(courant != null){
                    if (type == courant.getType()){
                        liste.add(courant);
                    }
                }
            }
        }
        return liste;
    }

    //On regarde si la case est une forteresse
    public boolean estFortresse(int x, int y){
        if (x == 0 || x == 8){
            if (y == 0 || y == 8){
                return true;
            }
        }
        return false;
    }

    //On regarde si la case est un konakis
    public boolean estKonakis(int x, int y){
        if (x==4 && y==4){
            return true;
        }
        return false;
    }

    public void deplace_pion(Coordonne depart, Coordonne dst){
        Pion p = plateau[depart.x][depart.y];
        boolean PartieFinie = false;
        setVide(depart.x, depart.y);
        plateau[dst.x][dst.y] = p;
        p.coordonne = dst;
        AMangerPion(p);
        if(estAttaquant(p)){
            if( AMangerRoi(dst))
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
        }
        if (estRoi(p)){
           if (estFortresse(dst.x, dst.y))
            System.out.println("PARTIE FINI CAR ROI EVADE");
        }

    }

    public boolean AMangerPion(Pion p){
        if (estAttaquant(p)){
            if (estDefenseur(p.getX()+1,p.getY())){
                if(estAttaquant(p.getX()+2,p.getY())){
                    setVide(p.getX()+1,p.getY());
                    return true;
                }
            }
            if (estDefenseur(p.getX()-1, p.getY())){
                if(estAttaquant(p.getX()-2,p.getY())){
                    setVide(p.getX()-1,p.getY());
                    return true;
                }
            }
            if (estDefenseur(p.getX(),p.getY()+1)){
                if(estAttaquant(p.getX(),p.getY()+2)){
                    setVide(p.getX(),p.getY()+1);
                    return true;
                }
            }
            if (estDefenseur(p.getX(),p.getY()-1)){
                if(estAttaquant(p.getX(),p.getY()-2)){
                    setVide(p.getX(),p.getY()-1);
                    return true;
                }
            }  
        }    
        else if (estDefenseur(p)){
            if(estAttaquant(p.getX()+1,p.getY())){
                if(estDefenseur(p.getX()+2,p.getY())){
                    setVide(p.getX()+1,p.getY());
                    return true;
                }
            }
            if (estAttaquant(p.getX()-1,p.getY())){
                if(estDefenseur(p.getX()-2,p.getY())){
                    setVide(p.getX()-1,p.getY());
                    return true;
                }
            }
            if (estAttaquant(p.getX(),p.getY()+1)){
                if(estDefenseur(p.getX(),p.getY()+2)){
                    setVide(p.getX(),p.getY()+1);
                    return true;
                }
            }
            if (estAttaquant(p.getX(),p.getY()-1)){
                if(estDefenseur(p.getX(),p.getY()-2)){
                    setVide(p.getX(),p.getY()-1);
                    return true;
                }
            }      
        }         
        return false;
    }


    //On regarde si la case est contre le bord
    public boolean estContreBord(int x, int y){
        if (x == 0 || x == 8){
            return true;
        }
        else if (y == 0 || y == 8){
            return true;
        }
        return false;
    }

    //On regarde si on a mangé le roi
    public boolean AMangerRoi(Coordonne dplc){
        if(estRoi(dplc.x+1,dplc.y)){
            if(estContreBord(dplc.x+1,dplc.y) && estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
            else if (estAttaquant(dplc.x+2,dplc.y) && estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
        }
        else if (estRoi(dplc.x-1,dplc.y)){
            if(estContreBord(dplc.x-1,dplc.y) && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y-1)){
                return true;
            }
            else if (estAttaquant(dplc.x-2,dplc.y) && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y-1)){
                return true;
            }
        }
        else if (estRoi(dplc.x,dplc.y+1)){
            if(estContreBord(dplc.x,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y+1)){
                return true;
            }
            else if (estAttaquant(dplc.x,dplc.y+2) && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y+1)){
                return true;
            }
        }
        else if (estRoi(dplc.x,dplc.y-1)){
            if(estContreBord(dplc.x,dplc.y-1) && estAttaquant(dplc.x-1,dplc.y-1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
            else if (estAttaquant(dplc.x,dplc.y-2) && estAttaquant(dplc.x-1,dplc.y-1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
        }
        return false;
    }

}
