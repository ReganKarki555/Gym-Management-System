import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

public class GymGUI extends JFrame {
    private ArrayList<GymMember> members = new ArrayList<>();
    private JTextField idField, nameField, locationField, phoneField, emailField,
            referralSourceField, paidAmountField, removalReasonField, trainerNameField;
    private JTextField regularPlanPriceField, premiumPlanChargeField, discountAmountField;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JComboBox<String> planComboBox, dayComboBox, monthComboBox, yearComboBox,
            membershipDayComboBox, membershipMonthComboBox, membershipYearComboBox;
    private JButton addRegularButton, addPremiumButton, activateButton, deactivateButton, markAttendanceButton,
            upgradePlanButton, calculateDiscountButton, revertRegularButton, revertPremiumButton, payDueButton,
            displayButton, clearButton, saveButton, readButton;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private static final Calendar TODAY = Calendar.getInstance();

    static {
        TODAY.set(2025, Calendar.MAY, 14); // Set today's date to May 14, 2025
    }

    public GymGUI() {
        setTitle("Gym Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Left panel for input fields
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(5, 5, 5, 5);
        gbcInput.fill = GridBagConstraints.HORIZONTAL;

        // Initialize text fields
        idField = new JTextField(20);
        nameField = new JTextField(20);
        locationField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        referralSourceField = new JTextField(20);
        paidAmountField = new JTextField(20);
        removalReasonField = new JTextField(20);
        trainerNameField = new JTextField(20);

        // Non-editable fields
        regularPlanPriceField = new JTextField("6500", 20);
        regularPlanPriceField.setEditable(false);
        premiumPlanChargeField = new JTextField("50000", 20);
        premiumPlanChargeField.setEditable(false);
        discountAmountField = new JTextField("0", 20);
        discountAmountField.setEditable(false);

        // Gender radio buttons
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);

        // DOB dropdowns
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i-1] = String.format("%02d", i);
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) months[i-1] = String.format("%02d", i);
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) years[i] = String.valueOf(2025 - i);

        dayComboBox = new JComboBox<>(days);
        monthComboBox = new JComboBox<>(months);
        yearComboBox = new JComboBox<>(years);
        JPanel dobPanel = new JPanel();
        dobPanel.add(dayComboBox);
        dobPanel.add(new JLabel("-"));
        dobPanel.add(monthComboBox);
        dobPanel.add(new JLabel("-"));
        dobPanel.add(yearComboBox);

        // Membership Start Date dropdowns
        membershipDayComboBox = new JComboBox<>(days);
        membershipMonthComboBox = new JComboBox<>(months);
        membershipYearComboBox = new JComboBox<>(years);
        JPanel membershipDatePanel = new JPanel();
        membershipDatePanel.add(membershipDayComboBox);
        membershipDatePanel.add(new JLabel("-"));
        membershipDatePanel.add(membershipMonthComboBox);
        membershipDatePanel.add(new JLabel("-"));
        membershipDatePanel.add(membershipYearComboBox);

        // Plan combo box
        planComboBox = new JComboBox<>(new String[]{"basic", "standard", "deluxe"});

        // Initialize buttons
        addRegularButton = new JButton("Add Regular Member");
        addPremiumButton = new JButton("Add Premium Member");
        activateButton = new JButton("Activate Membership");
        deactivateButton = new JButton("Deactivate Membership");
        markAttendanceButton = new JButton("Mark Attendance");
        upgradePlanButton = new JButton("Upgrade Plan");
        calculateDiscountButton = new JButton("Calculate Discount");
        revertRegularButton = new JButton("Revert Regular Member");
        revertPremiumButton = new JButton("Revert Premium Member");
        payDueButton = new JButton("Pay Due Amount");
        displayButton = new JButton("Display");
        clearButton = new JButton("Clear");
        saveButton = new JButton("Save to File");
        readButton = new JButton("Read from File");

        // Add input fields to input panel
        int row = 0;
        addLabelAndField(inputPanel, gbcInput, "ID:", idField, row++);
        addLabelAndField(inputPanel, gbcInput, "Name:", nameField, row++);
        addLabelAndField(inputPanel, gbcInput, "Location:", locationField, row++);
        addLabelAndField(inputPanel, gbcInput, "Phone:", phoneField, row++);
        addLabelAndField(inputPanel, gbcInput, "Email:", emailField, row++);
        addLabelAndField(inputPanel, gbcInput, "Gender:", genderPanel, row++);
        addLabelAndField(inputPanel, gbcInput, "DOB:", dobPanel, row++);
        addLabelAndField(inputPanel, gbcInput, "Membership Start Date:", membershipDatePanel, row++);
        addLabelAndField(inputPanel, gbcInput, "Referral Source:", referralSourceField, row++);
        addLabelAndField(inputPanel, gbcInput, "Paid Amount:", paidAmountField, row++);
        addLabelAndField(inputPanel, gbcInput, "Removal Reason:", removalReasonField, row++);
        addLabelAndField(inputPanel, gbcInput, "Trainer's Name:", trainerNameField, row++);
        addLabelAndField(inputPanel, gbcInput, "Regular Plan Price:", regularPlanPriceField, row++);
        addLabelAndField(inputPanel, gbcInput, "Premium Plan Charge:", premiumPlanChargeField, row++);
        addLabelAndField(inputPanel, gbcInput, "Discount Amount:", discountAmountField, row++);
        addLabelAndField(inputPanel, gbcInput, "Plan:", planComboBox, row++);

        // Add buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        buttonPanel.add(addRegularButton);
        buttonPanel.add(addPremiumButton);
        buttonPanel.add(activateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(markAttendanceButton);
        buttonPanel.add(upgradePlanButton);
        buttonPanel.add(calculateDiscountButton);
        buttonPanel.add(revertRegularButton);
        buttonPanel.add(revertPremiumButton);
        buttonPanel.add(payDueButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(readButton);

        gbcInput.gridx = 0;
        gbcInput.gridy = row;
        gbcInput.gridwidth = 2;
        inputPanel.add(buttonPanel, gbcInput);

        // Right panel for table
        String[] columnNames = {"ID", "Name", "Location", "Phone", "Email", "Membership Type", "Gender", "Attendance"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 7:
                        return Integer.class; // ID and Attendance are integers
                    default:
                        return String.class;    // Others are strings
                }
            }
        };
        memberTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 400));

        // Add panels to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(inputPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        mainPanel.add(tableScrollPane, gbc);

        add(mainPanel);

        // Add action listeners
        addRegularButton.addActionListener(e -> addRegularMember());
        addPremiumButton.addActionListener(e -> addPremiumMember());
        activateButton.addActionListener(e -> activateMembership());
        deactivateButton.addActionListener(e -> deactivateMembership());
        markAttendanceButton.addActionListener(e -> markAttendance());
        upgradePlanButton.addActionListener(e -> upgradePlan());
        calculateDiscountButton.addActionListener(e -> calculateDiscount());
        revertRegularButton.addActionListener(e -> revertRegularMember());
        revertPremiumButton.addActionListener(e -> revertPremiumMember());
        payDueButton.addActionListener(e -> payDueAmount());
        displayButton.addActionListener(e -> displayMembers());
        clearButton.addActionListener(e -> clearFields());
        saveButton.addActionListener(e -> saveToFile());
        readButton.addActionListener(e -> readFromFile());

        // Update table initially
        updateTable();
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String label, Component field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private boolean isMembershipDateValid(String day, String month, String year) {
        try {
            int selectedDay = Integer.parseInt(day);
            int selectedMonth = Integer.parseInt(month) - 1; // Calendar months are 0-based
            int selectedYear = Integer.parseInt(year);

            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);

            // Compare with today's date (May 14, 2025)
            return !selectedDate.after(TODAY);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void addRegularMember() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            if (isIdUnique(id)) {
                String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "";
                if (gender.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String membershipDay = (String) membershipDayComboBox.getSelectedItem();
                String membershipMonth = (String) membershipMonthComboBox.getSelectedItem();
                String membershipYear = (String) membershipYearComboBox.getSelectedItem();

                // Validate membership start date
                if (!isMembershipDateValid(membershipDay, membershipMonth, membershipYear)) {
                    JOptionPane.showMessageDialog(this, "Membership Start Date cannot be in the future (after May 14, 2025)!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dob = dayComboBox.getSelectedItem() + "-" + monthComboBox.getSelectedItem() + "-" + yearComboBox.getSelectedItem();
                String membershipStartDate = membershipDay + "-" + membershipMonth + "-" + membershipYear;
                RegularMember member = new RegularMember(id, nameField.getText().trim(), locationField.getText().trim(),
                        phoneField.getText().trim(), emailField.getText().trim(), gender, dob,
                        membershipStartDate, referralSourceField.getText().trim());
                members.add(member);
                regularPlanPriceField.setText(String.valueOf(member.getPrice()));
                updateTable();
                JOptionPane.showMessageDialog(this, "Regular Member added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPremiumMember() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            if (isIdUnique(id)) {
                String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "";
                if (gender.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String membershipDay = (String) membershipDayComboBox.getSelectedItem();
                String membershipMonth = (String) membershipMonthComboBox.getSelectedItem();
                String membershipYear = (String) membershipYearComboBox.getSelectedItem();

                // Validate membership start date
                if (!isMembershipDateValid(membershipDay, membershipMonth, membershipYear)) {
                    JOptionPane.showMessageDialog(this, "Membership Start Date cannot be in the future (after May 14, 2025)!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dob = dayComboBox.getSelectedItem() + "-" + monthComboBox.getSelectedItem() + "-" + yearComboBox.getSelectedItem();
                String membershipStartDate = membershipDay + "-" + membershipMonth + "-" + membershipYear;
                PremiumMember member = new PremiumMember(id, nameField.getText().trim(), locationField.getText().trim(),
                        phoneField.getText().trim(), emailField.getText().trim(), gender, dob,
                        membershipStartDate, trainerNameField.getText().trim());
                members.add(member);
                updateTable();
                JOptionPane.showMessageDialog(this, "Premium Member added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isIdUnique(int id) {
        return members.stream().noneMatch(member -> member.getId() == id);
    }

    private void activateMembership() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member != null) {
                member.activateMembership();
                updateTable();
                JOptionPane.showMessageDialog(this, "Membership activated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Member ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deactivateMembership() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member != null) {
                if (member.getActiveStatus()) {
                    member.deactivateMembership();
                    updateTable();
                    JOptionPane.showMessageDialog(this, "Membership deactivated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Membership is already deactivated!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markAttendance() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member != null) {
                if (member.getActiveStatus()) {
                    member.markAttendance();
                    updateTable();
                    JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Member is not active!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void upgradePlan() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                if (member.getActiveStatus()) {
                    String newPlan = (String) planComboBox.getSelectedItem();
                    String message = regularMember.upgradePlan(newPlan);
                    regularPlanPriceField.setText(String.valueOf(regularMember.getPrice()));
                    updateTable();
                    JOptionPane.showMessageDialog(this, message);
                } else {
                    JOptionPane.showMessageDialog(this, "Member is not active!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member is not a Regular Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateDiscount() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                String message = premiumMember.calculateDiscount();
                discountAmountField.setText(String.valueOf(premiumMember.getDiscountAmount()));
                updateTable();
                JOptionPane.showMessageDialog(this, message);
            } else {
                JOptionPane.showMessageDialog(this, "Member is not a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void revertRegularMember() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                regularMember.revertRegularMember(removalReasonField.getText().trim());
                regularPlanPriceField.setText(String.valueOf(regularMember.getPrice()));
                updateTable();
                JOptionPane.showMessageDialog(this, "Regular Member reverted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Member is not a Regular Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void revertPremiumMember() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            GymMember member = findMember(id);
            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                premiumMember.revertPremiumMember();
                discountAmountField.setText("0");
                updateTable();
                JOptionPane.showMessageDialog(this, "Premium Member reverted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Member is not a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void payDueAmount() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            double amount = Double.parseDouble(paidAmountField.getText().trim());
            if (amount < 0) {
                JOptionPane.showMessageDialog(this, "Paid amount cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GymMember member = findMember(id);
            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                String message = premiumMember.payDueAmount(amount);
                updateTable();
                JOptionPane.showMessageDialog(this, message);
            } else {
                JOptionPane.showMessageDialog(this, "Member is not a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID or amount format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayMembers() {
        JFrame displayFrame = new JFrame("Member Details");
        displayFrame.setSize(600, 400);
        displayFrame.setLocationRelativeTo(this);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        for (GymMember member : members) {
            textArea.append(getMemberDetails(member));
            textArea.append("------------------------\n");
        }
        displayFrame.add(new JScrollPane(textArea));
        displayFrame.setVisible(true);
    }

    private String getMemberDetails(GymMember member) {
        StringBuilder details = new StringBuilder();
        details.append("ID: ").append(member.getId()).append("\n")
               .append("Name: ").append(member.getName()).append("\n")
               .append("Location: ").append(member.getLocation()).append("\n")
               .append("Phone: ").append(member.getPhone()).append("\n")
               .append("Email: ").append(member.getEmail()).append("\n")
               .append("Gender: ").append(member.getGender()).append("\n")
               .append("DOB: ").append(member.getDOB()).append("\n")
               .append("Membership Start Date: ").append(member.getMembershipStartDate()).append("\n")
               .append("Attendance: ").append(member.getAttendance()).append("\n")
               .append("Loyalty Points: ").append(member.getLoyaltyPoints()).append("\n")
               .append("Active Status: ").append(member.getActiveStatus()).append("\n");
        if (member instanceof RegularMember) {
            RegularMember regularMember = (RegularMember) member;
            details.append("Plan: ").append(regularMember.getPlan()).append("\n")
                   .append("Price: ").append(regularMember.getPrice()).append("\n")
                   .append("Referral Source: ").append(regularMember.getReferralSource()).append("\n");
            if (!regularMember.getRemovalReason().isEmpty()) {
                details.append("Removal Reason: ").append(regularMember.getRemovalReason()).append("\n");
            }
        } else if (member instanceof PremiumMember) {
            PremiumMember premiumMember = (PremiumMember) member;
            details.append("Personal Trainer: ").append(premiumMember.getPersonalTrainer()).append("\n")
                   .append("Paid Amount: ").append(premiumMember.getPaidAmount()).append("\n")
                   .append("Is Full Payment: ").append(premiumMember.getIsFullPayment()).append("\n")
                   .append("Remaining Amount: ").append(premiumMember.getPremiumCharge() - premiumMember.getPaidAmount()).append("\n");
            if (premiumMember.getIsFullPayment()) {
                details.append("Discount Amount: ").append(premiumMember.getDiscountAmount()).append("\n");
            }
        }
        return details.toString();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        genderGroup.clearSelection();
        dayComboBox.setSelectedIndex(0);
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
        membershipDayComboBox.setSelectedIndex(0);
        membershipMonthComboBox.setSelectedIndex(0);
        membershipYearComboBox.setSelectedIndex(0);
        referralSourceField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        trainerNameField.setText("");
        planComboBox.setSelectedIndex(0);
        regularPlanPriceField.setText("6500");
        discountAmountField.setText("0");
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MemberDetails.txt"))) {
            // Write header
            writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n",
                    "ID", "Name", "Location", "Phone", "Email", "Membership Start Date", "Plan", "Price",
                    "Attendance", "Loyalty Points", "Active Status", "Full Payment", "Discount Amount", "Net Amount Paid"));
            writer.write("------------------------------------------------------------------------------------------------------------\n");

            // Write member details
            for (GymMember member : members) {
                String plan = member instanceof RegularMember ? ((RegularMember) member).getPlan() : "N/A";
                String price = member instanceof RegularMember ? String.valueOf(((RegularMember) member).getPrice()) : "N/A";
                String fullPayment = member instanceof PremiumMember ? String.valueOf(((PremiumMember) member).getIsFullPayment()) : "N/A";
                String discountAmount = member instanceof PremiumMember && ((PremiumMember) member).getIsFullPayment()
                        ? String.valueOf(((PremiumMember) member).getDiscountAmount()) : "0";
                String netAmountPaid = member instanceof PremiumMember
                        ? String.valueOf(((PremiumMember) member).getPaidAmount()) : "N/A";

                writer.write(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10d %-15d %-10b %-15s %-15s %-15s\n",
                        member.getId(), member.getName(), member.getLocation(), member.getPhone(), member.getEmail(),
                        member.getMembershipStartDate(), plan, price, member.getAttendance(), member.getLoyaltyPoints(),
                        member.getActiveStatus(), fullPayment, discountAmount, netAmountPaid));
            }
            JOptionPane.showMessageDialog(this, "Data saved to MemberDetails.txt successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readFromFile() {
        JFrame readFrame = new JFrame("Read Member Details");
        readFrame.setSize(600, 400);
        readFrame.setLocationRelativeTo(this);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data read from MemberDetails.txt successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        readFrame.add(new JScrollPane(textArea));
        readFrame.setVisible(true);
    }

    private GymMember findMember(int id) {
        return members.stream().filter(member -> member.getId() == id).findFirst().orElse(null);
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (GymMember member : members) {
            String membershipType = member instanceof RegularMember ? "Regular" : "Premium";
            Object[] row = {
                member.getId(),
                member.getName() != null ? member.getName() : "",
                member.getLocation() != null ? member.getLocation() : "",
                member.getPhone() != null ? member.getPhone() : "",
                member.getEmail() != null ? member.getEmail() : "",
                membershipType,
                member.getGender() != null ? member.getGender() : "",
                member.getAttendance()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymGUI().setVisible(true));
    }
}