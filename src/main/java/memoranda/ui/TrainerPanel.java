package main.java.memoranda.ui;

import main.java.memoranda.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Vector;

public class TrainerPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar eventsToolBar = new JToolBar();
    JScrollPane scrollPane = new JScrollPane();
    private JPanel cardsPanel = new JPanel();

    JButton newClass = new JButton();

    public TrainerPanel() {
        try {
            jbInit();
            initCardsPanel();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    //This is used in NewclassPopup so that on adding a new class, the UI refreshes.


    void newClassButtonHelper(JButton button){
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));
    }

    void jbInit() throws Exception {
        eventsToolBar.setFloatable(false);

        newClass.setText("Add a class");
        if(User.getUserType() == UserType.OWNER){
            newClass.setVisible(true);
        } else {
            newClass.setVisible(false);
        }
        newClassButtonHelper(newClass);
        newClass.setToolTipText("Add a new class");


        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);

        this.add(scrollPane, BorderLayout.CENTER);

        eventsToolBar.addSeparator(new Dimension(8, 24));


        eventsToolBar.addSeparator(new Dimension(8, 24));

        eventsToolBar.add(newClass, null);
        this.add(eventsToolBar, BorderLayout.NORTH);


    }

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


        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        infoPanel.add(trainerNameLabel);
        infoPanel.add(trainerRankLabel);
        infoPanel.add(trainerBeltLabel);

        card.add(infoPanel, BorderLayout.CENTER);




        return card;
    }
}
