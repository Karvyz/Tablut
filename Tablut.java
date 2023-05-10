
import Controlleur.*;
import Modele.Jeu;
import Modele.TypePion;
import java.util.ArrayList;


public class Tablut{
    public static void main(String[] args) {
        ArrayList<IA> ias = new ArrayList<>();
        ias.add(new IA_difficile_Long_live_the_king("", TypePion.ATTAQUANT, new Jeu()));
        ias.add(new IA_difficile_MassacrePion("", TypePion.ATTAQUANT, new Jeu()));
        ias.add(new IA_difficile_le_roi_c_ciao("", TypePion.ATTAQUANT, new Jeu()));
        ias.add(new IA_difficile_AttaqueRoi("", TypePion.ATTAQUANT, new Jeu()));
        ias.add(new IA_expert("", TypePion.ATTAQUANT, new Jeu()));
        TournoisControlleur tournoisControlleur = new TournoisControlleur(ias, 100);
        tournoisControlleur.tournois();
    }
}
 