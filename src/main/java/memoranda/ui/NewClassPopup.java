package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.java.memoranda.PersistentClass;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewClassPopup extends JFrame {
    private JTextField classNameField;
    private JTextField classLengthField;
    private JTextField maxClassSizeField;
    private JTextField classIdField;
    private JTextField classDateYearField;
    private JTextField classDateMonthField;
    private JTextField classDateDayField;
    private JTextField classDateHourField;
    private JCheckBox classIsPublicCheckBox;
    private JCheckBox classIsAdvancedCheckBox;

    private JComboBox<String> teacherList;

    private final ClassPanel classPanelRef;

    /**
     * Constructor for new class popup window.
     */
    public NewClassPopup(ClassPanel classPanelRef) {
        super("Add New Class");
        this.classPanelRef = classPanelRef;
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUserInterface();

        setLocationRelativeTo(null);
    }


    private void initUserInterface() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        // Add labels
        formPanel.add(new JLabel("Class Name:"));
        formPanel.add(new JLabel("Class Length (hours):"));
        formPanel.add(new JLabel("Max Class Size:"));
        formPanel.add(new JLabel("Class ID:"));
        formPanel.add(new JLabel("Class Year: "));
        formPanel.add(new JLabel("Class Month: "));
        formPanel.add(new JLabel("Class Day: "));
        formPanel.add(new JLabel("Class Hour: "));
        formPanel.add(new JLabel("Public Class:"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Teacher:"));
        // Add text fields
        formPanel.add(classNameField = new JTextField());
        formPanel.add(classLengthField = new JTextField());
        formPanel.add(maxClassSizeField = new JTextField());
        formPanel.add(classIdField = new JTextField());
        formPanel.add(classDateYearField = new JTextField());
        formPanel.add(classDateMonthField = new JTextField());
        formPanel.add(classDateDayField = new JTextField());
        formPanel.add(classDateHourField = new JTextField());
        formPanel.add(classIsPublicCheckBox = new JCheckBox("Yes, make public"));
        formPanel.add(classIsAdvancedCheckBox = new JCheckBox("Make advanced class"));
        formPanel.add(teacherList = new JComboBox<>(getTeacherList()));

        // Submit button at the bottom
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitClass());

        // Add the form panel to the main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        // Add the submit button to the main panel, aligned to the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the JFrame
        add(mainPanel);
    }

    private void submitClass() {

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
            boolean classIsAdvanced = classIsAdvancedCheckBox.isSelected();
            String teacherName = String.valueOf(teacherList.getSelectedItem());


            PersistentClass.addNewClass(className, classLength, maxClassSize,
                    classId, classIsPublic, teacherName, classYear, classMonth, classDay,
                    classHour, classIsAdvanced);
            JOptionPane.showMessageDialog(this,
                    "Class added successfully");

            clearForm();

            if (classPanelRef != null) {
                classPanelRef.refreshCards();
            }
            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please check your inputs. Make sure numerical fields are correct.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error adding class: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    ;

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
        classIsAdvancedCheckBox.setSelected(false);
    }

    private String[] getTeacherList() {
        List<String> list = new ArrayList<>();

        try {
            File file = new File("users.json");
            if (!file.exists()) {
                return new String[0];
            }

            String content = Files.readString(Paths.get("users.json"));

            JSONArray usersArray = new JSONArray(content);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("userType").equals("TRAINER") && (user.getString("trainingRank").equals("BLACK2") || user.getString("trainingRank").equals("BLACK3"))) {
                    list.add(user.getString("username"));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new String[0];
        }

        return list.toArray(new String[0]);
    }
}
