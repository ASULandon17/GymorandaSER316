package main.java.memoranda.ui;
import main.java.memoranda.PersistentClass;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
public class NewclassPopup extends JFrame {
    private JTextField classNameField;
    private JTextField classLengthField;
    private JTextField maxClassSizeField;
    private JTextField classIdField;
    private JTextField classDateYearField;
    private JTextField classDateMonthField;
    private JTextField classDateDayField;
    private JTextField classDateHourField;
    private JCheckBox classIsPublicCheckBox;
    private JButton submitButton;
    //DOES NOT CURRENTLY ALLOW YOU TO ADD WITH A TRAINER AS TRAINER FUNCTIONALITY HAS NOT BEEN IMPLEMENTED YET

    private ClassPanel classPanelRef;
    public NewclassPopup(ClassPanel classPanelRef){
        super("Add New Class");
        this.classPanelRef = classPanelRef;
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();

        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        classNameField = new JTextField();
        classLengthField = new JTextField();
        maxClassSizeField = new JTextField();
        classIdField = new JTextField();
        classDateYearField = new JTextField();
        classDateMonthField = new JTextField();
        classDateDayField = new JTextField();
        classDateHourField = new JTextField();
        
        classIsPublicCheckBox = new JCheckBox("Yes, make public");

        formPanel.add(new JLabel("Class Name:"));
        formPanel.add(classNameField);
        formPanel.add(new JLabel("Class Length (hours):"));
        formPanel.add(classLengthField);
        formPanel.add(new JLabel("Max Class Size:"));
        formPanel.add(maxClassSizeField);
        formPanel.add(new JLabel("Class ID:"));
        formPanel.add(classIdField);
        formPanel.add(new JLabel("Class Year: "));
        formPanel.add(classDateYearField);
        formPanel.add(new JLabel("Class Month: "));
        formPanel.add(classDateMonthField);
        formPanel.add(new JLabel("Class Day: "));
        formPanel.add(classDateDayField);
        formPanel.add(new JLabel("Class Hour: "));
        formPanel.add(classDateHourField);
        formPanel.add(new JLabel("Public Class:"));
        formPanel.add(classIsPublicCheckBox);

        // Submit button at the bottom
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitClass();
            }
        });

        // Add the form panel to the main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        // Add the submit button to the main panel, aligned to the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the JFrame
        add(mainPanel);
    }

    private void submitClass(){
        try {
            String className = classNameField.getText();
            int classLength = Integer.parseInt(classLengthField.getText());
            int maxClassSize = Integer.parseInt(maxClassSizeField.getText());
            int classId = Integer.parseInt(classIdField.getText());
            int classYear = Integer.parseInt(classDateYearField.getText());
            int classMonth = Integer.parseInt(classDateMonthField.getText());
            int classDay = Integer.parseInt(classDateDayField.getText());
            int classHour = Integer.parseInt(classDateHourField.getText());
            boolean classIsPublic = classIsPublicCheckBox.isSelected();

            // placeholder until class builder UI updated

            PersistentClass.addNewClass(className, classLength, maxClassSize, classId, classIsPublic, classYear, classMonth, classDay, classHour, false);
            JOptionPane.showMessageDialog(this, "Class added succesfully");
            clearForm();
            if(classPanelRef != null){
                classPanelRef.refreshCards();
            }
            this.dispose();
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Please check your inputs. Make sure numerical fields are correct.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error adding class: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    };
    private void clearForm() {
        classNameField.setText("");
        classLengthField.setText("");
        maxClassSizeField.setText("");
        classIdField.setText("");
        classDateYearField.setText("");
        classDateMonthField.setText("");
        classDateDayField.setText("");
        classDateHourField.setText("");
        classIsPublicCheckBox.setSelected(false);
    }
}
