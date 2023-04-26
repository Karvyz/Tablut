
import Modele.Coordonne;
import Modele.Jeu;
import Modele.Niveau;
import Controlleur.ControlleurMediateur;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import java.security.InvalidParameterException;
import java.util.Iterator;

public class Tablut{
    public static void main(String[] args) {
        Jeu j = new Jeu();
        CollecteurEvenements control = new ControlleurMediateur(j);
        InterfaceGraphique.demarrer(j, control);



        // Niveau n = new Niveau();
        // System.out.println(n);
        // Coordonne dep = new Coordonne(0, 3);
        // Coordonne dst = new Coordonne(2, 3);
        // n.deplace_pion(dep, dst);
        // System.out.println(n);

        //Jeu j = new Jeu();
        //CollecteurEvenements control = new ControlleurMediateur(j);
        //InterfaceGraphique.demarrer(j, control);
    }
}
 