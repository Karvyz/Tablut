package Modele;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Music implements ActionListener {

    private Clip clip;
    private BooleanControl muteControl;

    public Music() {
        try {
            // Charger le fichier audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Resources/Celtic-Ritual.wav"));

            // Créer un Clip à partir du flux audio
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Obtenir le contrôle de mise en sourdine (mute)
            muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();

        if (source.getText().equals("Musique")) {
            // Activer ou désactiver la musique en fonction de l'état de la case à cocher
            boolean isMusicEnabled = source.isSelected();

            // Activer ou désactiver le son en fonction de l'état de la case à cocher
            muteControl.setValue(!isMusicEnabled);

            if (isMusicEnabled) {
                // Jouer la musique
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                // Mettre en pause la musique
                clip.stop();
            }
        }
    }
}
