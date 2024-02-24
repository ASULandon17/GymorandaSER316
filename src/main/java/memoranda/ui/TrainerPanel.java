package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import main.java.memoranda.Trainer;
import main.java.memoranda.TrainerList;
import main.java.memoranda.User;
import main.java.memoranda.UserType;
import main.java.memoranda.ui.gymoranda.LookAndFeel;

/**
 * TrainerPanel creates a panel displaying the current trainers' data.
 */
public class TrainerPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar eventsToolBar = new JToolBar();
    JScrollPane scrollPane = new JScrollPane();
    private final JPanel cardsPanel = new JPanel();

    JButton newTrainer = new JButton();

    /**
     * TrainerPanel constructor for creating a new trainer panel.
     */
    public TrainerPanel() {
        try {
            jbInit();
            initCardsPanel();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    /**
     * Takes a button and sets the necessary areas the values they need.
     *
     * @param button the button to be given values
     */
    private void newTrainerButtonHelper(JButton button) {
        LookAndFeel.gymButtonHelper(button);
    }

    /**
     * Builds the trainer panel.
     */
    void jbInit() throws Exception {
        eventsToolBar.setFloatable(false);

        newTrainer.setText("Add a trainer");
        if (User.getUserType() == UserType.OWNER) {
            newTrainer.setVisible(true);
        } else {
            newTrainer.setVisible(false);
        }
        newTrainerButtonHelper(newTrainer);
        newTrainer.setToolTipText("Add a new trainer");


        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);

        this.add(scrollPane, BorderLayout.CENTER);

        eventsToolBar.addSeparator(new Dimension(8, 24));


        eventsToolBar.addSeparator(new Dimension(8, 24));

        eventsToolBar.add(newTrainer, null);
        this.add(eventsToolBar, BorderLayout.NORTH);


    }

    /**
     * Creates a card for all the trainers that are found available.
     */
    void initCardsPanel() {
        cardsPanel.removeAll();
        TrainerList trainerList = new TrainerList();
        Vector<Trainer> trainers = trainerList.getTrainers();

        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Trainer trainer : trainers) {
            JPanel card = createTrainerCard(trainer);
            cardsPanel.add(card);
        }

        scrollPane.setViewportView(cardsPanel);
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    /**
     * Creates a card given a trainer.
     *
     * @param trainer the trainer to be used for the card
     * @return a card that displays the trainer's details
     */
    private JPanel createTrainerCard(Trainer trainer) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        Border roundedLineBorder = new LineBorder(Color.BLACK, 1, true);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        CompoundBorder compoundBorder = new CompoundBorder(roundedLineBorder, paddingBorder);
        card.setBorder(compoundBorder);


        JLabel trainerNameLabel = new JLabel("Trainer: " + trainer.getTrainerName());
        JLabel trainerRankLabel = new JLabel("Training Rank: " + trainer.getTrainingRank());
        JLabel trainerBeltLabel = new JLabel("Belt Rank: " + trainer.getBeltRank());

        // Create a string based on the availability of the Trainer
        JLabel trainerAvailabilityLabel = buildAvailabilityLabel(trainer);


        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        infoPanel.add(trainerNameLabel);
        infoPanel.add(trainerRankLabel);
        infoPanel.add(trainerBeltLabel);
        infoPanel.add(trainerAvailabilityLabel);

        card.add(infoPanel, BorderLayout.CENTER);


        return card;
    }

    private static JLabel buildAvailabilityLabel(Trainer trainer) {
        int start = trainer.getStartAvailability();
        int end = trainer.getEndAvailability();
        String availability = "";
        if (start < 12) {
            availability = start + ":00 a.m. to ";
        } else {
            if (start != 12) {
                start = start % 12;  //Change from 24hr to 12hr clock
            }
            availability = start + ":00 p.m. to ";
        }
        if (end < 12) {
            availability += end + ":00 a.m.";
        } else {
            if (end != 12) {
                end = end % 12;  //Change from 24hr to 12hr clock
            }
            availability += end + ":00 p.m.";
        }

        return new JLabel("Availability: " + availability);
    }
}
