package Modele;
import java.util.ArrayList;

import Modele.Pion;
import Modele.Roi;

public class Niveau {
    public static final int NOIR = 0;
    public static final int BLANC = 1;
    public static final int ROI = 2;
    int taille = 9;

    Pion [][] plateau = new Pion[taille][taille];



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

    //On regarde si la case est vide
    public boolean estVide(int x, int y) {
        return plateau[x][y] == null;
    }

    //On regarde si la case est noire
    public boolean estAttaquant(int x, int y) {
        return plateau[x][y].getType() == TypePion.ATTAQUANT;
    }

    //On regarde si la case est blanche
    public boolean estDefenseur(int x, int y) {
        return plateau[x][y].getType() == TypePion.DEFENSEUR;
    }

    //On regarde si la case est le roi
    public boolean estRoi(int x, int y) {
        return plateau[x][y].getType() == TypePion.ROI;
    }

    //On place une case vide sur le plateau aux coordonnées x et y (cas ou on capture par exemple)
    public void setVide(int x, int y) {
        plateau[x][y] = null;
    }

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

    // //On regarde si le déplacement horizontal est possible
    // public boolean deplaceH(int x1, int y1, int y2){
    //     if (y1<y2){
    //         for (int i = y1+1; i < y2; i++) {
    //             iintf (!estVide(x1, i)){
    //                 return false;
    //             }
    //         }
    //         return true;
    //     }
    //     else{
    //         for (int i = y1-1; i > y2; i--) {
    //             if (!estVide(x1, i)){
    //                 return false;
    //             }
    //         }
    //         return true;
    //     }
    // }

    // //On regarde si le déplacement vertical est possible
    // public boolean deplaceV(int x1, int y1, int x2){
    //     if (x1<x2){
    //         for (int i = x1+1; i < x2; i++) {
    //             if (!estVide(i, y1)){
    //                 return false;
    //             }
    //         }
    //         return true;
    //     }
    //     else{
    //         for (int i = x1-1; i > x2; i--) {
    //             if (!estVide(i, y1)){
    //                 return false;
    //             }
    //         }
    //         return true;
    //     }
    // }

    // //On regarde si le déplacement est possible
    // public boolean deplace(int x1, int y1, int x2, int y2){
    //     if (x1 == x2){
    //         return deplaceH(x1, y1, y2);
    //     }
    //     else if (y1 == y2){
    //         return deplaceV(x1, y1, x2);
    //     }
    //     else{
    //         return false;
    //     }
    // }

    // //On regarde si la case est une forteresse
    // public boolean estFortresse(int x, int y){
    //     if (x == 0 || x == 8){
    //         if (y == 0 || y == 8){
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // //On regarde si la case est un konakis
    // public boolean estKonakis(int x, int y){
    //     if (x==4 && y==4){
    //         return true;
    //     }
    //     return false;
    // }

    // public int[][] depl(int x1, int y1){
    //     //on regarde si le pion est bien un pion
    //     if(estBlanc(x1, Unnamedy1) || estNoir(x1, y1)){
    //         //on liste les déplacements possibles
    //         int[][] depl = new int[8][2];
    //         int i = 0;
    //         //on regarde si le pion peut se déplacer a l'horizontale
    //         for (int j = 0; j < taille; j++) {
    //             //on verifie que la case n'est pas une fortresse ou un konakis
    //             if (deplaceH(x1, y1, j) && !estFortresse(x1, j) && !estKonakis(x1, j)){
    //                 depl[i][0] = x1;
    //                 depl[i][1] = j;
    //                 i++;
    //             }
    //         }
    //         //on regarde si le pion peut se déplacer a la verticale
    //         for (int j = 0; j < taille; j++) {
    //             //on verifie que la case n'est pas une fortresse ou un konakis
    //             if (deplaceV(x1, y1, j) && !estFortresse(j, y1) && !estKonakis(j, y1)){
    //                 depl[i][0] = j;
    //                 depl[i][1] = y1;
    //                 i++;
    //             }
    //         }
    //         //on renvoie les déplacements possibles
    //         return depl;
    //     }
    //     else if(estRoi(x1, y1)){
    //         //on liste les déplacements possibles
    //         int[][] depl = new int[8][2];
    //         int i = 0;
    //         //on regarde si le pion peut se déplacer a l'horizontale
    //         for (int j = 0; j < taille; j++) {
    //             //on verifie que la case n'a pas de pion sur le chemin
    //             if (deplaceH(x1, y1, j)){
    //                 depl[i][0] = x1;
    //                 depl[i][1] = j;
    //                 i++;
    //             }
    //         }
    //         //on regarde si le pion peut se déplacer a la verticale
    //         for (int j = 0; j < taille; j++) {
    //             //on verifie que la case n'a pas de pion sur le chemin
    //             if (deplaceV(x1, y1, j) ){
    //                 depl[i][0] = j;
    //                 depl[i][1] = y1;
    //                 i++;
    //             }
    //         }
    //         //on renvoie les déplacements possibles
    //         return depl;
    //     }
    //     //si ce n'est pas un pion on renvoie null
    //     else{
    //         System.out.println("Ce n'est pas un pion");
    //         return null;
    //     }
    // }

}
