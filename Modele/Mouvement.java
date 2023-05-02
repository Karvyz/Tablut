package Modele;

public class Mouvement extends Coup {
    private final Coordonne departPion;
    private Coordonne arriveePion;
    private boolean positionPionChangee;

    public Mouvement(Niveau n, Joueurs j, int pionL, int pionC) {
        super(n, j, pionL, pionC);
        departPion = pion();
    }

    static boolean estCorrect(int dL, int dC) {
        return estDeplacement(dL, dC);
    }

    static boolean estDeplacement(int dL, int dC) {
        return Math.abs(dL) + Math.abs(dC) < 2 && dL + dC != 0;
    }

    @Override
    public Coordonne pion() {
        if (positionPionChangee) {
            if (niveau().plateau[departPion.getX()][departPion.getY()] != null) {
                return arriveePion;
            }
            return null;
        }
        return super.pion();
    }

    @Override
    public Coordonne depart() {
        verifierCoupCree("Impossible de récupérer la case de départ");
        return departPion;
    }

    @Override
    public Coordonne arrivee() {
        verifierCoupCree("Impossible de récupérer la case d'arrivée");
        return arriveePion;
    }

    @Override
    public boolean creer(int destL, int destC) {
        verifierAucunCoupCree();

        int dL = destL - pion().getX();
        int dC = destC - pion().getY();

        // Si la case d'arrivée est hors du plateau, si elle est occupée, si c'est le kinakis et qu'on est pas le roi
        if (niveau().plateau[destL][destC] != null || destL > 7 || destC > 7 || (niveau().plateau[pion().getX()][pion().getY()].getType() != TypePion.ROI && destL == 4 && destC == 4)) {
            return false;
        }
        arriveePion = new Coordonne(destL, destC);

        if (estDeplacement(dL, dC)) {
            return creerDeplacementPion(pion().getX(), pion().getY(), destL, destC);
        }
        return false;
    }

    private boolean creerDeplacementPion(int departL, int departC, int arriveeL, int arriveeC) {
        int dL = arriveeL - departL;
        int dC = arriveeC - departC;
        Pion pion = recupererPion(departL, departC);

        // Si le pion en tue un autre avec une forteresse, etc.
        // TODO : vérifier les conséquences de son déplacement
        if (niveau().plateau[arriveeL][arriveeC] != null) {
            niveau().plateau[departL][departC] = null;
            return true;
        } else {
            throw new IllegalStateException("Impossible de créer le déplacement (" + departL + ", " + departC + ") -> (" +
                    arriveeL + ", " + arriveeC + ")" + " : déplacement du pion invalide");
        }
    }

    private Pion recupererPion(int l, int c) {
        if (niveau().plateau[l][c] == null) {
            throw new IllegalStateException("Aucun pion trouvé sur la case (" + l + ", " + c + ")");
        }
        return niveau().plateau[l][c];
    }

    @Override
    public void jouer(Coordonne depart, Coordonne arrivee) {
        super.jouer(depart, arrivee);
        // On change la position du pion
        positionPionChangee = true;
    }

    /*
    @Override
    public void annuler() {
        super.annuler();
        // On change remet la position de départ du pion
        positionPionChangee = false;
    }
     */

    @Override
    public String toString() {
        return "Mouvement : depart = " + depart() + " - arrivee = " + arrivee();
    }
}
