package Controlleur;

import Global.Configuration;
import Modele.*;
import Vues.*;
import Vues.CollecteurEvenements;

import java.io.*;
import java.util.Random;

public class ControlleurMediateur implements CollecteurEvenements{

    Vues vues;

    Animation animDemarrage;

    public Jeu jeu;
    final int lenteurAttente = 50;
    int decompte;
    public boolean Stop;

    private boolean horsJeu;



    private boolean consulter;




    public ControlleurMediateur() {
        //on utilise fixeJeu
    }

    public String toString() {
        if(jeu == null)
            return "";
        return "Jeu {" +
                "niveau: " + jeu.n +
                "}\njoueur 1 = " + jeu.getJoueur1() +
                "\njoueur 2 = " + jeu.getJoueur2() +
                "\n jeu().enCours() +\n";

    }

    public void fixeJeu(Jeu j) {
        this.jeu = j;
    }

    @Override
    public Jeu jeu() {
        verifierJeu("Impossible de renvoyer un jeu");
        return jeu;
    }

    /**
     * Méthode en rapport avec le lancement d'une partie
     */
    @Override
    public void nouvellePartie(String nomJ1, TypeJoueur typeJ1, TypePion roleJ1, String nomJ2, TypeJoueur typeJ2, TypePion roleJ2) {
        verifierMediateurVues("Impossible de créer une nouvelle partie");
        System.out.println("Type des joueurs choisis pour la partie J0: " + typeJ1 + ", J1: " + typeJ2);
        jeu.supprimeTousObservateurs();
        jeu.nouveauJoueur(nomJ1, typeJ1, roleJ1); //Initialisation des joueurs
        jeu.nouveauJoueur(nomJ2, typeJ2, roleJ2);
        jeu.nouvellePartie();
        vues.nouvellePartie();
        Stop = false;
        setConsulter(false);

    }

    @Override
    public void nouvelleQuickPartie() {
        jeu.supprimeTousObservateurs();
        Random rand = new Random();
        int randomValue = rand.nextInt(2);
        if (randomValue == 0) {
            jeu.nouveauJoueur("Vous", TypeJoueur.HUMAIN, TypePion.ATTAQUANT);
            jeu.nouveauJoueur("", TypeJoueur.IA_DIFFICILE, TypePion.DEFENSEUR);
        } else {
            jeu.nouveauJoueur("Vous", TypeJoueur.HUMAIN, TypePion.DEFENSEUR);
            jeu.nouveauJoueur("", TypeJoueur.IA_DIFFICILE, TypePion.ATTAQUANT);

        }
        jeu.nouvellePartie();
        vues.nouvellePartie();
        Stop = false;
        setConsulter(false);
    }

    @Override
    public void partieSuivante() {
        verifierJeu("Impossible de passer à la partie suivante");
        jeu.nouvellePartie();
        vues.nouvellePartie();
        afficherJeu();
        Stop = false;
        setConsulter(false);
    }

    public void restaurePartie() {
        jeu.setDebutPartie(true); //Pour éviter l'affichage de la fleche du coup de l'IA si une partie IA vs humain a ete joue
        jeu.setEnCours(true);
        vues.restaurePartie();
        Stop = false;
        setConsulter(false);
    }

    public boolean sauvegarderPartie(String fichier) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fichier);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            System.out.println("Sauvegarde du jeu dans le fichier: " + fichier);
            Data_Niveau data_niveau = new Data_Niveau(jeu.config, jeu.n, jeu.coup_annule, jeu.coup_a_refaire, jeu.pileIA_annule, jeu.pileIA_refaire, jeu.get_num_JoueurCourant(), jeu.joueurs[0], jeu.joueurs[1], jeu.enCours());

            objectOut.writeObject(data_niveau);
            objectOut.close();
            fileOut.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean chargerPartie(String fichier) {
        Data_Niveau data_niveau;

        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            data_niveau = (Data_Niveau) objectIn.readObject();
            jeu.n = data_niveau.niveau;
            jeu.coup_annule = data_niveau.coup_annule;
            jeu.coup_a_refaire = data_niveau.coup_a_refaire;
            jeu.pileIA_annule = data_niveau.pileIA_annule;
            jeu.pileIA_refaire = data_niveau.pileIA_refaire;
            jeu.set_num_JoueurCourant(data_niveau.get_JC());
            jeu.joueurs[0] = data_niveau.attaquant;
            jeu.joueurs[1] = data_niveau.defenseur;
            jeu.config = data_niveau.config;
            jeu.setEnCours(data_niveau.enCours);
            jeu.setDebutPartie(true);
            jeu.joueurs[0].fixeJeuJoueur(jeu);
            jeu.joueurs[1].fixeJeuJoueur(jeu);

            objectIn.close();
            fileIn.close();

            System.out.println("Le jeu a été chargé.");

        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + fichier);
            return false;
        } catch (EOFException | InvalidClassException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + fichier);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Classe Data_Niveau introuvable");
            return false;
        }
        return true;
    }

    /**
     * Méthode en rapport avec l'interaction HommeMachine
     *
     * @return
     */
    @Override//Deplacement en Drag&Drop
    public boolean dragANDdrop(Coordonne src, Coordonne dst) {
        if (jeu.joueurs[jeu().get_num_JoueurCourant()].jeu(src, dst)) {// MODIF de jeu.n ici
            changeJoueur();
            return true;
        }
        return false;
    }

    @Override
    public boolean clicSouris(Pion selectionne, int l, int c) {
        // Lors d'un clic sur un pion, on affiche ses déplacements possibles

        Coordonne depart = new Coordonne(selectionne.getX(), selectionne.getY());
        Coordonne arrive = new Coordonne(l, c);
        if (jeu.joueurs[jeu.get_num_JoueurCourant()].jeu(depart, arrive)) {
            changeJoueur();
            return true;
        }
        return false;
    }

    @Override
    public void toucheClavier(String touche) {
        if (jeu().partieTerminee()) {
            return;
        }
        switch (touche) {
            case "Annuler":
                jeu.annuler();
                break;
            case "Refaire":
                jeu.refaire();
                break;
            default:
                Configuration.instance().logger().info("Touche inconnue : " + touche);
        }
    }

    public void tictac2(){
        if (animDemarrage == null) {
            int lenteurAnimation = Integer.parseInt(Configuration.instance().lirePropriete("LenteurAnimationDemarrage"));
            animDemarrage = new AnimationDemarrage(lenteurAnimation, this);
        }
        if (!animDemarrage.terminee()) {
            animDemarrage.temps();
        }
    }
    /**
     * Méthode permettant la permutation des joueurs
     */
    public void tictac() {

        if (jeu.enCours()) {
            if(jeu().debutPartie()){
                jeu.setDebutPartie(false);
            }
            //System.out.println(jeu);
            if (jeu == null || jeu().partieTerminee()) {
                return;
            }

            if (jeu().n.PlusdePion(jeu().get_num_JoueurCourant())) {
                jeu().setEnCours(false);
                System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
            }
            //TODO ici l'IA joue instanténément donc problème pour annuler coup en IA vs Humain
            else if (jeu.joueurs[jeu.get_num_JoueurCourant()].tempsEcoule()) { //Un humain renvoi tjr false, une IA renvoi vrai lorsquelle a joué(jeu effectué dans tempsEcoule())
                if(Stop == true){
                    return;
                }
                changeJoueur();
            }
            else if (decompte == 0) {
                if (jeu.joueurs[jeu.get_num_JoueurCourant()].estHumain() && jeu.joueurs[jeu.get_num_JoueurCourant()].aPionsNoirs())
                    System.out.println("C'est a vous de jouer : L'ATTAQUANT ");
                else
                    System.out.println("C'est a vous de jouer : LE DEFENSEUR");
                decompte = lenteurAttente;
            } else {
                decompte--;
            }
        }
    }

    void changeJoueur() {
        decompte = lenteurAttente;
    }


    /**
     * Méthode en rapport avec l'IHM
     */
    @Override
    public void toClose() {
        vues.close();
        System.exit(0);
    }

    @Override
    public void afficherRegles() {
        vues.afficherR();
    }

    @Override
    public void fixerMediateurVues(Vues v) {
        vues = v;
    }

    private void verifierMediateurVues(String message) {
        if (vues == null) {
            throw new IllegalStateException(message + " : médiateur de vues non fixé");
        }
    }

    private void verifierJeu(String message) {
        if (jeu == null) {
            throw new IllegalStateException(message + " : aucune partie commencée");
        }
    }

    @Override
    public void afficherDemarrage() {
        verifierMediateurVues("Impossible d'afficher le démarrage");
        vues.afficherDemarrage();
    }

    @Override
    public void afficherMenuPrincipal() {
        verifierMediateurVues("Impossible d'afficher le menu principal");
        vues.afficherMenuPrincipal();
    }

    @Override
    public void afficherMenuNouvellePartie() {
        verifierMediateurVues("Impossible d'afficher le menu nouvelle partie");
        vues.afficherMenuNouvellePartie();
    }

    @Override
    public void afficherJeu() {
        verifierMediateurVues("Impossible d'afficher le jeu");
        vues.afficherJeu();
    }

    @Override
    public void afficherMenuChargerPartie() {
        verifierMediateurVues("Impossible d'afficher le menu des parties sauvegardées");
        vues.afficherMenuChargerPartie();
    }

    @Override
    public void afficherQuickPartie() {
        verifierMediateurVues("Impossible d'afficher le jeu");
        vues.afficherJeu();
    }

    public boolean getStop(){
        return Stop;
    }


    public boolean getConsulter() {
        return consulter;
    }

    public void setConsulter(boolean consulter) {
        this.consulter = consulter;
    }
}
