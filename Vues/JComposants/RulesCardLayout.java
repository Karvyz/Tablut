package Vues.JComposants;

import Vues.Imager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RulesCardLayout extends JFrame {

    final private CardLayout cardLayout;
    final private JPanel cards;
    private int currentImageIndex = 0;

    private final JButton previousButton;
    private final JButton nextButton;

    public RulesCardLayout() {
        setTitle("Rules Card Layout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Ouvrir au centre de la fenêtre en calculant la position
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = height * 1000 / 1412;
        System.out.println(width + " " + height);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2);
        setSize(width, height - 150);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        previousButton = new CButton("Précédent");
        nextButton = new CButton("Suivant");

        previousButton.addActionListener(e -> showPreviousCard());

        nextButton.addActionListener(e -> showNextCard());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        getContentPane().add(cards, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        updateButtonStates(); // Met a jour l'état des boutons

        // Si on ferme la fenêtre, on ferme uniquement la fenêtre et non l'application
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBackground(new Color(84, 80, 80, 255));
    }

    public void addRulesCards(List<ImageIcon> images) {
        for (ImageIcon image : images) {
            JPanel card = createCard(image);
            cards.add(card);
        }
    }

    private JPanel createCard(ImageIcon image) {
        JPanel card = new JPanel(new BorderLayout());
        JLabel label = new JLabel(image);
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private static ImageIcon resizeImage(ImageIcon imageIcon, Dimension size) {
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void showPreviousCard() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
            cardLayout.previous(cards);
        }
        updateButtonStates();
    }

    private void showNextCard() {
        if (currentImageIndex < cards.getComponentCount() - 1) {
            currentImageIndex++;
            cardLayout.next(cards);
        }
        updateButtonStates();
    }

    private void updateButtonStates() {
        int numCards = cards.getComponentCount();
        // Vérifier si le bouton précédent doit être verrouillé
        previousButton.setEnabled(currentImageIndex != 0);
        // Vérifier si le bouton suivant doit être verrouillé
        nextButton.setEnabled(currentImageIndex != numCards - 1);
    }

    public static List<ImageIcon> getImages() {
        List<ImageIcon> images = new ArrayList<>();
        ImageIcon regle1, regle2, regle3, regle4;
        regle1 = Imager.getImageIcon("regles1.png");
        regle2 = Imager.getImageIcon("regles2.png");
        regle3 = Imager.getImageIcon("regles3.png");
        regle4 = Imager.getImageIcon("regles4.png");
        regle1 = resizeImage(regle1, new Dimension(637, 900));
        regle2 = resizeImage(regle2, new Dimension(637, 900));
        regle3 = resizeImage(regle3, new Dimension(637, 900));
        regle4 = resizeImage(regle4, new Dimension(637, 900));
        images.add(regle1);
        images.add(regle2);
        images.add(regle3);
        images.add(regle4);
        return images;
    }
}
