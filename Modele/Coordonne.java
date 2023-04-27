package Modele;

public class Coordonne implements Cloneable{
    int x;
    int y;

    public Coordonne(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordonne(Coordonne coordonne){
        this.x = coordonne.x;
        this.y = coordonne.y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
    
}
