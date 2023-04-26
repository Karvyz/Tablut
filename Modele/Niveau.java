public class Niveau {
public static final int VIDE = 0;
public static final int NOIR = 1;
public static final int BLANC = 2;
public static final int ROI = 3;
int taille = 9;

int[][] plateau = new int[taille][taille];

//On creer le plateau de jeu
public Niveau() {
    init_Niveau();
}

//On initialise le plateau de jeu
public void init_Niveau() {
    for (int i = 0; i < taille; i++) {
        for (int j = 0; j < taille; j++) {
            switch(i){
                case 0:
                    switch(j){
                        case 0:
                        case 1:
                        case 2:
                        case 6:
                        case 7:
                        case 8:
                            plateau[i][j] = VIDE;
                           break;
                        case 3:
                        case 4:
                        case 5:
                            plateau[i][j] = NOIR;
                            break;
                        
                    }
                    break;
                case 1:
                case 7:
                    switch(j){
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 5:
                        case 6:
                        case 7:
                            plateau[i][j] = VIDE;
                            break;
                        case 4:
                            plateau[i][j] = NOIR;
                            break;
                    }
                    break;
                case 2:
                case 6:
                    switch(j){
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 5:
                        case 6:
                        case 7: 
                            plateau[i][j] = VIDE;
                            break;
                        case 4:
                            plateau[i][j] = BLANC;
                            break;
                    }
                    break;
                case 3:
                case 5:
                    switch(j){
                        case 0:
                        case 8:
                            plateau[i][j] = NOIR;
                            break;
                        case 1:
                        case 7:
                        case 2:
                        case 6:
                            plateau[i][j] = VIDE;
                            break;
                        case 4:
                            plateau[i][j] = BLANC;
                            break;
                    }
                    break;
                case 4:
                    switch(j){
                        case 0:
                        case 8:
                        case 1:
                        case 7:
                            plateau[i][j] = NOIR;
                            break;
                        case 2:
                        case 6:
                        case 3:
                        case 5:
                            plateau[i][j] = BLANC;
                            break;
                        case 4:
                            plateau[i][j] = ROI;
                            break;
                    }
                    break;
                }
            }
        }
    }

    //On regarde la taille du plateau
    public int getTaille() {
        return taille;
    }

    //On regarde si la case est vide
    public boolean estVide(int x, int y) {
        return plateau[x][y] == VIDE;
    }

    //On regarde si la case est noire
    public boolean estNoir(int x, int y) {
        return plateau[x][y] == NOIR;
    }

    //On regarde si la case est blanche
    public boolean estBlanc(int x, int y) {
        return plateau[x][y] == BLANC;
    }

    //On regarde si la case est le roi
    public boolean estRoi(int x, int y) {
        return plateau[x][y] == ROI;
    }

    //On place une case vide sur le plateau aux coordonnées x et y
    public void setVide(int x, int y) {
        plateau[x][y] = VIDE;
    }

    //On place le pion noir sur le plateau aux coordonnées x et y
    public void setNoir(int x, int y) {
        plateau[x][y] = NOIR;
    }

    //On place le pion blanc sur le plateau aux coordonnées x et y
    public void setBlanc(int x, int y) {
        plateau[x][y] = BLANC;
    }

    //On place le roi sur le plateau aux coordonnées x et y
    public void setRoi(int x, int y) {
        plateau[x][y] = ROI;
    }

    //On regarde si le déplacement horizontal est possible
    public boolean deplaceH(int x1, int y1, int y2){
        if (y1<y2){
            for (int i = y1+1; i < y2; i++) {
                if (!estVide(x1, i)){
                    return false;
                }
            }
            return true;
        }
        else{
            for (int i = y1-1; i > y2; i--) {
                if (!estVide(x1, i)){
                    return false;
                }
            }
            return true;
        }
    }

    //On regarde si le déplacement vertical est possible
    public boolean deplaceV(int x1, int y1, int x2){
        if (x1<x2){
            for (int i = x1+1; i < x2; i++) {
                if (!estVide(i, y1)){
                    return false;
                }
            }
            return true;
        }
        else{
            for (int i = x1-1; i > x2; i--) {
                if (!estVide(i, y1)){
                    return false;
                }
            }
            return true;
        }
    }

    //On regarde si le déplacement est possible
    public boolean deplace(int x1, int y1, int x2, int y2){
        if (x1 == x2){
            return deplaceH(x1, y1, y2);
        }
        else if (y1 == y2){
            return deplaceV(x1, y1, x2);
        }
        else{
            return false;
        }
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

    public int[][] depl(int x1, int y1){
        //on regarde si le pion est bien un pion
        if(estBlanc(x1, y1) || estNoir(x1, y1)){
            //on liste les déplacements possibles
            int[][] depl = new int[8][2];
            int i = 0;
            //on regarde si le pion peut se déplacer a l'horizontale
            for (int j = 0; j < taille; j++) {
                //on verifie que la case n'est pas une fortresse ou un konakis
                if (deplaceH(x1, y1, j) && !estFortresse(x1, j) && !estKonakis(x1, j)){
                    depl[i][0] = x1;
                    depl[i][1] = j;
                    i++;
                }
            }
            //on regarde si le pion peut se déplacer a la verticale
            for (int j = 0; j < taille; j++) {
                //on verifie que la case n'est pas une fortresse ou un konakis
                if (deplaceV(x1, y1, j) && !estFortresse(j, y1) && !estKonakis(j, y1)){
                    depl[i][0] = j;
                    depl[i][1] = y1;
                    i++;
                }
            }
            //on renvoie les déplacements possibles
            return depl;
        }
        else if(estRoi(x1, y1)){
            //on liste les déplacements possibles
            int[][] depl = new int[8][2];
            int i = 0;
            //on regarde si le pion peut se déplacer a l'horizontale
            for (int j = 0; j < taille; j++) {
                //on verifie que la case n'a pas de pion sur le chemin
                if (deplaceH(x1, y1, j)){
                    depl[i][0] = x1;
                    depl[i][1] = j;
                    i++;
                }
            }
            //on regarde si le pion peut se déplacer a la verticale
            for (int j = 0; j < taille; j++) {
                //on verifie que la case n'a pas de pion sur le chemin
                if (deplaceV(x1, y1, j) ){
                    depl[i][0] = j;
                    depl[i][1] = y1;
                    i++;
                }
            }
            //on renvoie les déplacements possibles
            return depl;
        }
        //si ce n'est pas un pion on renvoie null
        else{
            System.out.println("Ce n'est pas un pion");
            return null;
        }
    }

}
