package Modele;

public class Coordonne {
    int x;
    int y;

    public Coordonne(int x, int y){
        this.x = x;
        this.y = y;
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
