package Vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurTemps implements ActionListener {
    CollecteurEvenements control;

    int decompte_start=0; //Permet a l'IA d'attendre que le plateau soit affiché avant de jouer

    AdaptateurTemps(CollecteurEvenements c) {
        control = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(control.getStop()){
            decompte_start = 0;
            return;
        }else if(control.jeu().enCours() && !control.jeu().getJoueur1().estHumain() && decompte_start != 20){ //Permet de laisser le temps de voir le plateau avant que l'IA joue
            decompte_start +=1;
        }
        else if (control.jeu() != null && control.jeu().n != null)
            control.tictac();
        else{
            control.tictac2();
        }
    }
}
