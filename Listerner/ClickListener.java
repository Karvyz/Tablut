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
                if (button.getName().equals("btnValide")) {//faire des fonctions pour chaque bouton
                    if (cm.isPionSelec() && cm.isDeplSelec()){
                        //cm.setCoupValide(true);
                        cm.changeJoueur();
                        j.joueurSuivant();
                        cm.setPionSelec(false);
                        cm.setDeplSelec(false);
                        System.out.println("Coup validé");
                    }
                    else{
                        System.out.println("Aucun déplacement choisis");
                    } 
                }
                else if (button.getName().equals("btnAnnule")) {//faire des fonctions pour chaque bouton
                     if (cm.restaurer_niveau()){
                        System.out.println("Coup annule");
                     }
                     else{
                        System.out.println("Aucun coup a annulé");
                     }
                } 
                // else if (button.getName().equals("btnRefaire")) {//faire des fonctions pour chaque bouton
                //     if (//test pile refaire vide){
                //     }
                //     else{
                //         System.out.println("Aucun coup n'a ete annulé");
                // } 
                // Ajouter d'autres conditions pour chaque bouton sur la même fenêtre

        }
    }
}