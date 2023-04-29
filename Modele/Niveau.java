package Modele;
import java.util.ArrayList;


public class Niveau {
    public static final int NOIR = 0;
    public static final int BLANC = 1;
    public static final int ROI = 2;
    private int taille = 9;

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
    


    // Crée une copie profonde de l'objet Niveau
    public Niveau copy() {
        Niveau copiedNiveau = new Niveau();
        copiedNiveau.taille = this.taille;

        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille; j++) {
                if (this.plateau[i][j] != null) {
                    if (this.plateau[i][j] instanceof Roi) {
                        Roi copiedRoi = new Roi(this.plateau[i][j].getX(), this.plateau[i][j].getY());
                        copiedNiveau.plateau[i][j] = copiedRoi;
                    } else {
                        Pion copiedPion = new Pion(this.plateau[i][j].getX(), this.plateau[i][j].getY(), this.plateau[i][j].getType());
                        copiedNiveau.plateau[i][j] = copiedPion;
                    }
                } else {
                    copiedNiveau.plateau[i][j] = null;
                }
            }
        }
        return copiedNiveau;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Niveau other = (Niveau) obj;

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Pion p1 = this.plateau[i][j];
                Pion p2 = other.plateau[i][j];

                if (p1 == null && p2 != null || p1 != null && p2 == null) {
                    return false;
                }
                if (p1 != null && !p1.equals(p2)) {
                    return false;
                }
            }
        }
        return true;
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



    //int = 0 coup joué , 1 noir on gagné, 2 blanc on gagné
    public int deplace_pion(Coordonne depart, Coordonne dst){

        Pion p = plateau[depart.x][depart.y];
        setVide(depart.x, depart.y);
        plateau[dst.x][dst.y] = p;

        p.setCoordonne(dst) ;
        AMangerPion(p);
        if(estAttaquant(p)){
            if( AMangerRoi(dst))
                return 1;
        }
        if (estRoi(p)){
            if (estFortresse(dst.x, dst.y))
            return 2;
        }
       //}
        return 0;

    }

    public void AMangerPion(Pion p){
        if (estAttaquant(p)){
            if (estDefenseur(p.getX()+1,p.getY())){
                if(estAttaquant(p.getX()+2,p.getY())||estFortresse(p.getX()+2, p.getY())){
                    setVide(p.getX()+1,p.getY());
                  
                }
            }
            if (estDefenseur(p.getX()-1, p.getY())){
                if(estAttaquant(p.getX()-2,p.getY())||estFortresse(p.getX()-2, p.getY())){
                    setVide(p.getX()-1,p.getY());
                    
                }
            }
            if (estDefenseur(p.getX(),p.getY()+1)){
                if(estAttaquant(p.getX(),p.getY()+2)||estFortresse(p.getX(), p.getY()+2)){
                    setVide(p.getX(),p.getY()+1);
                    
                }
            }
            if (estDefenseur(p.getX(),p.getY()-1)){
                if(estAttaquant(p.getX(),p.getY()-2) || estFortresse(p.getX(), p.getY()-2)){
                    setVide(p.getX(),p.getY()-1);
          
                }
            }  
        }    
        else if (estDefenseur(p)){
            if(estAttaquant(p.getX()+1,p.getY())){
                if(estDefenseur(p.getX()+2,p.getY()) || estFortresse(p.getX()+2, p.getY())){
                    setVide(p.getX()+1,p.getY());
                    
                }
            }
            if (estAttaquant(p.getX()-1,p.getY())){
                if(estDefenseur(p.getX()-2,p.getY()) || estFortresse(p.getX()-2, p.getY())){
                    setVide(p.getX()-1,p.getY());
                    
                }
            }
            if (estAttaquant(p.getX(),p.getY()+1)){
                if(estDefenseur(p.getX(),p.getY()+2) || estFortresse(p.getX(), p.getY()+2)){
                    setVide(p.getX(),p.getY()+1);
          
                }
            }
            if (estAttaquant(p.getX(),p.getY()-1)){
                if(estDefenseur(p.getX(),p.getY()-2) || estFortresse(p.getX(), p.getY()-2)){
                    setVide(p.getX(),p.getY()-1);
                    
                }
            }      
        }         
        
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
    
    //On regarde si le pion est contre une forteresse
    public boolean estContreFortresse(int x, int y){
        if (x==0 && y==1 || x==0 && y==7 || x==1 && y==0 || x==1 && y==8 || x==7 && y==0 || x==7 && y==8 || x==8 && y==1 || x==8 && y==7){
            return true;
        }
        return false;
    }
    
    public boolean check_clic_selection_dest(Pion selec, int x, int y){
        ArrayList<Coordonne> liste_depl = selec.getDeplacement(plateau);
		if (liste_depl.isEmpty()){ //Aucun coup possible pour ce pion
			return false;
		}
		Coordonne arrive = new Coordonne(x, y);
		if(liste_depl.contains(arrive)){
            return true;
		}
		return false;
	}
    
    public boolean check_clic_selection_pion(Pion p, int JC) { 
        if (p != null){
            ArrayList<Pion> pions_dispo = getPionsDispo(JC); 
			return pions_dispo.contains(p);
		}
		//}
		return false;
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
    //Renvoi la liste des pions disponibles au joueur courant
    public ArrayList<Pion> getPionsDispo(int JC){
		ArrayList<Pion> liste ;
        TypePion t = typePion_JC(JC);

		liste = getPions(t);

        if (t == TypePion.DEFENSEUR){
			TypePion t1 = TypePion.ROI;
            ArrayList<Pion> liste2 = getPions(t1);
            liste.addAll(liste2); // concaténation de list2 à la fin de list1        
        }

		return liste;
	}

    public boolean PlusdePion(int JC){
		return getPionsDispo(JC).isEmpty();
	}

    public TypePion typePion_JC(int JC){
		switch (JC){
			case 0:
				return TypePion.ATTAQUANT;
			case 1:
				return TypePion.DEFENSEUR;
			default:
				System.out.println("Joueur courant inconnu");
				return null;
		}
	}

    
    //On regarde si il est contre le trone
    public int estContreTrone(int x, int y){
        if (x==4 && y==3 ){
            System.out.println("trone 3");
            return 3;
        }
        else if (x==4 && y==5){
            System.out.println("trone 4");
            return 4;
        }
        else if (x==3 && y==4){
            System.out.println("trone 1");
            return 1;
        }
        else if (x==5 && y==4){
            System.out.println("trone 2");
            return 2;
        }
        return 0;
    }

    //On regarde si on a mangé le roi
    public boolean AMangerRoi(Coordonne dplc){
        if(estRoi(dplc.x+1,dplc.y)){
            if(estContreBord(dplc.x+1,dplc.y)){
                if( (estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y-1)) || ((estContreFortresse(dplc.x+1, dplc.y)&&(estAttaquant(dplc.x+1,dplc.y+1) || estAttaquant(dplc.x+1,dplc.y-1))))){
                    return true;
                }
            }
            else if (estAttaquant(dplc.x+2,dplc.y) && estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
            else if(estContreTrone(dplc.x+1,dplc.y)!=0){
                if(estContreTrone(dplc.x+1,dplc.y)==1 && estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y-1)){
                    return true;
                }
                if(estContreTrone(dplc.x+1,dplc.y)==3 && estAttaquant(dplc.x+2,dplc.y) && estAttaquant(dplc.x+1,dplc.y-1)){
                    return true;
                }
                if(estContreTrone(dplc.x+1,dplc.y)==4 && estAttaquant(dplc.x+2,dplc.y) && estAttaquant(dplc.x+1,dplc.y+1)){
                    return true;
                }
            }
        }
        else if (estRoi(dplc.x-1,dplc.y)){
            if(estContreBord(dplc.x-1,dplc.y)){
                if( (estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y-1)) || (estContreFortresse(dplc.x-1, dplc.y)&&(estAttaquant(dplc.x-1,dplc.y+1) || estAttaquant(dplc.x-1,dplc.y-1)))){
                    return true;
                }
                return true;
            }
            else if (estAttaquant(dplc.x-2,dplc.y) && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y-1)){
                return true;
            }
            else if(estContreTrone(dplc.x-1,dplc.y)!=0){
                if(estContreTrone(dplc.x-1,dplc.y)==2 && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y-1)){
                    return true;
                }
                if(estContreTrone(dplc.x-1,dplc.y)==3 && estAttaquant(dplc.x-2,dplc.y) && estAttaquant(dplc.x-1,dplc.y-1)){
                    return true;
                }
                if(estContreTrone(dplc.x-1,dplc.y)==4 && estAttaquant(dplc.x-2,dplc.y) && estAttaquant(dplc.x-1,dplc.y+1)){
                    return true;
                }
            }

        }
        else if (estRoi(dplc.x,dplc.y+1)){
            if(estContreBord(dplc.x,dplc.y+1)){
                if( (estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x-1,dplc.y+1)) || (estContreFortresse(dplc.x, dplc.y+1)&&(estAttaquant(dplc.x+1,dplc.y+1) || estAttaquant(dplc.x-1,dplc.y+1)))){
                    return true;
                }
            }
            else if (estAttaquant(dplc.x,dplc.y+2) && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y+1)){
                return true;
            }
            else if(estContreTrone(dplc.x,dplc.y+1)!=0){
                if(estContreTrone(dplc.x,dplc.y+1)==1 && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x,dplc.y+2)){
                    return true;
                }
                if(estContreTrone(dplc.x,dplc.y+1)==2 && estAttaquant(dplc.x+1,dplc.y+1) && estAttaquant(dplc.x,dplc.y+2)){
                    return true;
                }
                if(estContreTrone(dplc.x,dplc.y+1)==3 && estAttaquant(dplc.x-1,dplc.y+1) && estAttaquant(dplc.x+1,dplc.y+1)){
                    return true;
                }
            }
        }
        else if (estRoi(dplc.x,dplc.y-1)){
            if(estContreBord(dplc.x,dplc.y-1)){
                if( (estAttaquant(dplc.x+1,dplc.y-1) && estAttaquant(dplc.x-1,dplc.y-1)) || (estContreFortresse(dplc.x, dplc.y-1)&&(estAttaquant(dplc.x+1,dplc.y-1) || estAttaquant(dplc.x-1,dplc.y-1)))){
                    return true;
                }
            }
            else if (estAttaquant(dplc.x,dplc.y-2) && estAttaquant(dplc.x-1,dplc.y-1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
            else if (estAttaquant(dplc.x,dplc.y-2) && estAttaquant(dplc.x-1,dplc.y-1) && estAttaquant(dplc.x+1,dplc.y-1)){
                return true;
            }
            else if (estContreTrone(dplc.x,dplc.y-1)!=0){
                if(estContreTrone(dplc.x,dplc.y-1)==1 && estAttaquant(dplc.x,dplc.y-2) && estAttaquant(dplc.x-1,dplc.y-1)){
                    return true;
                }
                if(estContreTrone(dplc.x,dplc.y-1)==2 && estAttaquant(dplc.x,dplc.y-2) && estAttaquant(dplc.x+1,dplc.y-1)){
                    return true;
                }
                if(estContreTrone(dplc.x,dplc.y-1)==4 && estAttaquant(dplc.x-1,dplc.y-1) && estAttaquant(dplc.x+1,dplc.y-1)){
                    return true;
                }
            }
        }
        return false;
    }

}
