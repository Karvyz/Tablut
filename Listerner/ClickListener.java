package Listerner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Controlleur.ControlleurMediateur;
import Vues.CollecteurEvenements;

public class ClickListener implements ActionListener {
    private int interfaceNum;
    private JButton button;
    ControlleurMediateur cm ;

    public ClickListener(int interfaceNum, JButton button, CollecteurEvenements control) {
        this.interfaceNum = interfaceNum;
        this.button = button;
        this.cm = (ControlleurMediateur) control;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (interfaceNum){
            case 0: //FenetrePlateau
                if (button.getName().equals("btnValide")) {
                    System.out.println(cm.isPionSelec());


                } 
                // Ajouter d'autres conditions pour chaque bouton sur la même fenêtre

        }
    }
}