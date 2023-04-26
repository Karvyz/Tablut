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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Coordonne)) return false;
        
        Coordonne other = (Coordonne) obj;
        return this.x == other.x && this.y == other.y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
    
}
