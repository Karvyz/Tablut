package Controlleur.heuristiques;

import Modele.Niveau;
import Modele.TypePion;

public class HeuristiqueFusion extends Heuristique {
    double lltk;
    double lrcc;
    double MP;
    double AR;
    public HeuristiqueFusion(double lltk, double lrcc, double MP, double AR) {
        this.lltk = lltk;
        this.lrcc = lrcc;
        this.MP = MP;
        this.AR = AR;
    }
    @Override
    public double evaluation(Niveau n, TypePion typePion) {
        return lltk * new HeuristiqueLongLiveTheKing().evaluation(n, typePion)
                + lrcc * new HeuristiqueLeRoiCCiao().evaluation(n, typePion)
                + MP * new HeuristiqueMassacrePion().evaluation(n, typePion)
                + AR * new HeuristiqueAttaqueRoi().evaluation(n, typePion);
    }

    @Override
    public String toString() {
        return "HeuristiqueFusion{" +
                "lltk=" + lltk +
                ", lrcc=" + lrcc +
                ", MP=" + MP +
                ", AR=" + AR +
                '}';
    }
}