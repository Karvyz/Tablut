package Controlleur;

import Vues.*;

public class AnimationDemarrage extends Animation {
    private final ControlleurMediateur c;
    private boolean termine;

    public AnimationDemarrage(int l, ControlleurMediateur c) {
        super(l);
        this.c = c;
    }

    @Override
    public void miseAJour() {
        c.vues.afficherMenuPrincipal();
        Theme.instance();
        termine = true;
    }

    @Override
    public boolean terminee() {
        return termine;
    }
}
