package Listerner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Controlleur.ControlleurMediateur;
import Modele.Jeu;
import Vues.CollecteurEvenements;
import Vues.FenetrePlateau;
import Vues.InterfaceGraphique;

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
        String fichier = "test_sauvegarde.save";
        switch (interfaceNum){
            case 0: //FenetrePlateau
                if (button.getName().equals("btnAnnule")) {//faire des fonctions pour chaque bouton
                     if (cm.restaurer_niveau()){
                        System.out.println("Coup annule");
                     }
                     else{
                        System.out.println("Aucun coup a annulé");
                     }
                } 
                else if (button.getName().equals("btnRefaire")) {//faire des fonctions pour chaque bouton
                    if (cm.refaire_coup()){
                        System.out.println("Coup refait");
                    }
                    else{
                        System.out.println("Aucun coup n'a ete annulé");
                    } 
                }
                else if (button.getName().equals("btnSauvegarde")) {//faire des fonctions pour chaque bouton
                    if (j.save(fichier)){
                        System.out.println("Jeu actuelle sauvegardé dans le fichier: "+fichier);
                    }
                    else{
                        System.out.println("Impossible de sauvegarder le jeu dans le fichier:" +fichier);
                    } 
                }
                else if (button.getName().equals("btnCharger")) {//faire des fonctions pour chaque bouton
                    j.nouvellePartie(fichier);
                    
                }
                break;
            default :
                System.out.println("Interface inconnu");
        }
    }
}