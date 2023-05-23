package Vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurTemps implements ActionListener {
    CollecteurEvenements control;

    AdaptateurTemps(CollecteurEvenements c) {
        control = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(control.getStop()){
            return;
        }
        else if (control.jeu() != null && control.jeu().n != null)
            control.tictac();
        else{
            control.tictac2();
        }
    }
}
