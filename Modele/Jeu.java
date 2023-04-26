package Modele;

public class Jeu {

    Niveau n;
    int joueurCourant;
    public boolean enCours = false;

    public Jeu(){
        nouvellePartie();
        System.out.println(n);
    }
    
    /**
	 * Crée une nouvelle partie de taille par défaut
	 */
	public void nouvellePartie() {
		this.n = new Niveau();
        joueurCourant = 0;
        enCours = true;
	}

    public boolean enCours(){
        return enCours;
    }
}
