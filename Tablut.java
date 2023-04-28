
import Modele.Jeu;

import Controlleur.ControlleurMediateur;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;



public class Tablut{
    public static void main(String[] args) {
        Jeu j = new Jeu();
        System.out.println(j.enCours);
        CollecteurEvenements control = new ControlleurMediateur(j);
        //System.out.println(control.pionSelec);
        InterfaceGraphique.demarrer(j, control);

    }
}
 