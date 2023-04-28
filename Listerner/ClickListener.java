package Listerner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Controlleur.ControlleurMediateur;
import Modele.Jeu;
import Vues.CollecteurEvenements;

public class ClickListener implements ActionListener {
    private int interfaceNum;
    private JButton button;
    ControlleurMediateur cm ;
    Jeu j;

    public ClickListener(int interfaceNum, JButton button, CollecteurEvenements control, Jeu jeu) {
        this.interfaceNum = interfaceNum;
        this.button = button;
        this.cm = (ControlleurMediateur) control;
        this.j = jeu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (interfaceNum){
            case 0: //FenetrePlateau
                if (button.getName().equals("btnValide")) {
                    if (cm.isPionSelec() && cm.isDeplSelec()){
                        //cm.setCoupValide(true);
                        cm.changeJoueur();
                        j.joueurSuivant();
                        cm.setPionSelec(false);
                        cm.setDeplSelec(false);
                    }
                    else{
                        System.out.println("Aucun déplacement choisis");
                    }


                } 
                // Ajouter d'autres conditions pour chaque bouton sur la même fenêtre

        }
    }
}