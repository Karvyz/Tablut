package Controlleur;

import Modele.Data_Niveau;

import javax.swing.*;
import java.io.*;

public class GestionnaireSauvegarde_Chargement {
    private ControlleurMediateur cm;

    public GestionnaireSauvegarde_Chargement(ControlleurMediateur controleurMediateur) {
        this.cm = controleurMediateur;
    }
    public void saveGame() {
        String fileName = null;
        while (true) {
            fileName = JOptionPane.showInputDialog(null, "Entrez le nom du fichier de sauvegarde (max 18 caractères) :", "Sauvegarde", JOptionPane.PLAIN_MESSAGE);

            if (fileName == null) {
                // L'utilisateur a appuyé sur Annuler, sortir de la méthode
                return;
            }

            fileName = fileName.trim();
            if (fileName.isEmpty()) {
                handleSaveError("Le nom de fichier ne peut pas être vide");
                continue;
            }

            if (fileName.length() > 18) {
                handleSaveError("Le nom de fichier ne peut pas dépasser 18 caractères");
                continue;
            }

            break;
        }

        String directoryPath = "Resources/save/";
        File directory = new File(directoryPath);
        if (!directory.exists()) { //Verifie si le dossier existe ou le crée
            if (!directory.mkdirs()) {
                handleSaveError("Échec de la création du dossier de sauvegarde");
                return;
            }
        } else if (!directory.isDirectory() || !directory.canWrite()) {
            handleSaveError("Impossible d'écrire dans le dossier de sauvegarde");
            return;
        }
        fileName = directoryPath + fileName + ".save";
        File file = new File(fileName);

        while (file.exists()) {
            handleSaveError("Ce nom de fichier existe déjà. Veuillez en choisir un autre.");
            return;
        }

        if (sauvegarderPartie(fileName)) {
            JOptionPane.showMessageDialog(null, "Sauvegarde réussie", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
        } else {
            handleSaveError("Échec de la sauvegarde");
        }
    }

    private void handleSaveError(String msg) {
        JButton retryButton = new JButton("Recommencer");
        JButton cancelButton = new JButton("Annuler");


        retryButton.addActionListener(e -> {
            JOptionPane.getRootFrame().dispose(); // Ferme la boîte de dialogue d'erreur
            saveGame();
        });

        cancelButton.addActionListener(e -> {
            JOptionPane.getRootFrame().dispose(); // Ferme la boîte de dialogue d'erreur
        });

        int option = JOptionPane.showOptionDialog(null,
                msg,
                "Erreur",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new Object[]{retryButton, cancelButton},
                retryButton);

        if (option == JOptionPane.YES_OPTION) {
            saveGame();
        }
    }

    public boolean sauvegarderPartie(String fichier) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fichier);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            System.out.println("Sauvegarde du jeu dans le fichier: " + fichier);
            Data_Niveau data_niveau = new Data_Niveau(cm.jeu.config, cm.jeu.n, cm.jeu.coup_annule, cm.jeu.coup_a_refaire, cm.jeu.pileIA_annule, cm.jeu.pileIA_refaire, cm.jeu.get_num_JoueurCourant(), cm.jeu.joueurs[0], cm.jeu.joueurs[1], cm.jeu.enCours());

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
            cm.jeu.n = data_niveau.niveau;
            cm.jeu.coup_annule = data_niveau.coup_annule;
            cm.jeu.coup_a_refaire = data_niveau.coup_a_refaire;
            cm.jeu.pileIA_annule = data_niveau.pileIA_annule;
            cm.jeu.pileIA_refaire = data_niveau.pileIA_refaire;
            cm.jeu.set_num_JoueurCourant(data_niveau.get_JC());
            cm.jeu.joueurs[0] = data_niveau.attaquant;
            cm.jeu.joueurs[1] = data_niveau.defenseur;
            cm.jeu.config = data_niveau.config;
            cm.jeu.setEnCours(data_niveau.enCours);
            cm.jeu.setDebutPartie(true);
            cm.jeu.joueurs[0].fixeJeuJoueur(cm.jeu);
            cm.jeu.joueurs[1].fixeJeuJoueur(cm.jeu);

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
}
