package Modele;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public abstract class Coup implements Serializable {
    private final Niveau n;
    private final Joueurs joueur;
    private final Coordonne pion;
    private final Deque<Effet> etats;
    private boolean coupJoue;

    Coup(Niveau n, Joueurs j, int pionL, int pionC) {
        this.n = n;
        joueur = j;
        pion = new Coordonne(pionL, pionC);
        etats = new ArrayDeque<>();
    }

    Niveau niveau() {
        return n;
    }

    Joueurs joueur() {
        return joueur;
    }

    /**
     * Position actuelle du pion sélectionné jouant le coup
     * @return La case où se situe actuellement le pion
     */
    Coordonne pion() {
        return pion;
    }

    protected void verifierCoupCree(String message) {
        if (etats.isEmpty()) {
            throw new IllegalStateException(message + " : aucun coup créé");
        }
    }

    /**
     * Case sur laquelle la pièce jouée se situe au départ du coup
     * @return La case de départ du coup
     */
    abstract public Coordonne depart();

    /**
     * Case sur laquelle la pièce jouée se retrouve à la fin du coup
     * @return La case d'arrivée du coup
     */
    abstract public Coordonne arrivee();

    public boolean estMouvement() {
        verifierCoupCree("Impossible de vérifier si le coup est un mouvement");
        return etats.element().pion().getType() == TypePion.DEFENSEUR || etats.element().pion().getType() == TypePion.ATTAQUANT || etats.element().pion().getType() == TypePion.ROI;
    }

    protected void deplacer(Pion p, int departL, int departC, int arriveeL, int arriveeC) {
        etats.add(new Effet(p, new Coordonne(departL, departC), new Coordonne(arriveeL, arriveeC)));
    }

    protected void ajouter(Pion p, int l, int c) {
        etats.add(new Effet(p, null, new Coordonne(l, c)));
    }

    protected void supprimer(Pion p, int l, int c) {
        etats.add(new Effet(p, new Coordonne(l, c), null));
    }

    protected void verifierAucunCoupCree() {
        if (!etats.isEmpty()) {
            throw new IllegalStateException("Impossible de créer un nouveau coup : un coup a déjà été créé");
        }
    }

    public abstract boolean creer(int destL, int destC);

    public void jouer(Coordonne depart, Coordonne arrive){
        int i;

        i = n.deplace_pion(depart, arrive);

        System.out.println("Le joueur " + joueur + " a déplacé le piont Noir de (" + depart.getX() +"," + depart.getY() + ") en (" + arrive.getX() + "," + arrive.getY() +")");
        if (i > 0){
            if (i == 1)
                System.out.println("PARTIE FINI CAR ROI CAPTURE");
            else
                System.out.println("PARTIE FINI CAR ROI EVADE");
        }

        // TODO test si une partie est finie;

    }

    /*
    public void annuler() {
        if (etats.isEmpty()) {
            throw new IllegalStateException("Impossible d'annuler le coup : aucun coup créé");
        }
        if (!coupJoue) {
            throw new IllegalStateException("Impossible d'annuler le coup : le coup n'a pas encore été joué");
        }
        coupJoue = false;

        for (Effet q : etats) {
            if (q.arrivee() != null) {
                // On supprime la pièce
                n.supprimer(q.arrivee().ligne(), q.arrivee().colonne(), q.arrivee().epoque(), q.piece());
            }

            // On remet la pièce sur la case de départ
            if (q.depart() != null) {
                n.ajouter(q.depart().ligne(), q.depart().colonne(), q.depart().epoque(), q.piece());
            }
        }
    }

     */
}
