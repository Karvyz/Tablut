package Modele;

import java.io.Serializable;

public class ConfigurationJeu implements Serializable {
    private boolean RegicideTrone;
    private boolean RegicideForteresse;
    private boolean RegicideMur;
    private boolean WinTousCote;
    private boolean SuicideAutorise;
    private boolean PionForteresse;



    public ConfigurationJeu() {
        RegicideTrone = true; //A true on peut capturer le roi avec le trone
        RegicideForteresse = true; //A true on peut capturer le roi avec une forteresse
        RegicideMur = true; //On peut se servir d'un mur pour capturer le roi
        WinTousCote = false; //A true on peut faire évader le roi sur tous les bords
        SuicideAutorise = false; //A true, le joueur peut se suicider entre deux pions
        PionForteresse = true; //A true, on peut tuer un pion entre un pion adverse et une forteresse
    }

    public boolean isPF(){
        return PionForteresse;
    }

    public void setPF(boolean PionForteresse){
        this.PionForteresse = PionForteresse;
    }

    public boolean isSA(){
        return SuicideAutorise;
    }

    public void setSA(boolean SuicideAutorise){
        this.SuicideAutorise = SuicideAutorise;
    }

    public boolean isRT() {
        return RegicideTrone;
    }

    public void setRT(boolean RegicideTrone) {
        this.RegicideTrone = RegicideTrone;
    }

    public boolean isRF() {
        return RegicideForteresse;
    }

    public void setRF(boolean RegicideForteresse) {
        this.RegicideForteresse = RegicideForteresse;
    }

    public boolean isRM() {
        return RegicideMur;
    }

    public void setRM(boolean RegicideMur) {
        this.RegicideMur = RegicideMur;
    }

    public boolean isWinTousCote() {
        return WinTousCote;
    }

    public void setWinTousCote(boolean WinTousCote) {
        this.WinTousCote = WinTousCote;
    }

}