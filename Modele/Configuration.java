package Modele;

public class Configuration {
    private boolean RegicideTrone;
    private boolean RegicideFortresse;
    private boolean RegicideMur;
    private boolean WinTousCote;
    private boolean RegicideDef;
    private boolean SuicideAutorise;
    private boolean PionForteresse;



    public Configuration() {
        RegicideTrone = true; //A true on peut capturer le roi avec le trone
        RegicideFortresse = true; //A true on peut capturer le roi avec une forteresse
        RegicideMur = true; //On peut se servir d'un mur pour capturer le roi
        WinTousCote = false; //A true on peut faire évader le roi sur tous les bords
        RegicideDef = false;
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
        return RegicideFortresse;
    }

    public void setRF(boolean RegicideFortresse) {
        this.RegicideFortresse = RegicideFortresse;
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

    public boolean isRegicideDef() {
        return RegicideDef;
    }

    public void setRegicideDef(boolean RegicideDef) {
        this.RegicideDef = RegicideDef;
    }
}
