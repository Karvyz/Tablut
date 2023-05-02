package Controlleur;

public class AnimationIA extends Animation {
    private final IA2 ia;

    public AnimationIA(int l, IA2 ia) {
        super(l);
        this.ia = ia;
    }

    @Override
    public void miseAJour() {
        ia.jouer();
    }
}