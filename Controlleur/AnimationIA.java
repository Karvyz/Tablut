package Controlleur;

public class AnimationIA extends Animation {
    private final IA ia;

    public AnimationIA(int l, IA ia) {
        super(l);
        this.ia = ia;
    }

    @Override
    public void miseAJour() {

        System.out.println("fuck tom");
    }
}