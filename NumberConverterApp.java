/*
 * 
 * Author:  Thinh Nguyen
 * Created At: 16.01.2024
 * 
 * Title: A number converter app
 * Description: An app, that allow user to input a number in many bases and convert into another base.
 * 
 */

// Import the importants libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberConverterApp extends JFrame {

    // Declaring variables
    private JTextField inputField;
    private JTextField outputField;
    private JComboBox<String> inputBaseSelector;
    private JComboBox<String> outputBaseSelector;
    private int DEFAULT_WIDTH = 400;
    private int DEFAULT_HEIGHT = 300;

    public NumberConverterApp() {
        // Set title of the window
        setTitle("Number Converter");
        // Set window's size
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // When click on close button, the window closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Grid layout for the buttons
        setLayout(new GridLayout(3, 2));
        // Set window, that is visible to the user.
        setVisible(true);
        // Set default background to color black
        setBackground(Color.BLACK);

        // Creating default font
        Font defaultFont = new Font("Arial", Font.PLAIN, 20);

        // Assign textField to input variable
        inputField = new JTextField("0");
        // Assign font to the field
        inputField.setFont(defaultFont);
        // Assign a padding to the field text
        inputField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.getColor("")),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        inputField.setBackground(Color.getColor("#"));
        // Add it to the class
        add(inputField);

        // Assign selector to input dropdown menu
        inputBaseSelector = new JComboBox<>(new String[] { "Decimal", "Binary", "Hexadecimal" });
        inputBaseSelector.setFont(defaultFont);
        // Add it to the class
        add(inputBaseSelector);

        // Listen for change events
        inputBaseSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // If the event is triggered, update the input prefix
                updateInputFieldPrefix();
            }
        });

        // Assign textField to output
        outputField = new JTextField("0b");
        outputField.setFont(defaultFont);
        outputField.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        outputField.setBackground(Color.getColor(""));
        // Set outputField to not be editable
        outputField.setEditable(false);
        // Add it the class
        add(outputField);

        // Assign selector to input dropdown menu
        outputBaseSelector = new JComboBox<>(new String[] { "Binary", "Decimal", "Hexadecimal" });
        outputBaseSelector.setFont(defaultFont);
        // Add it to the class
        add(outputBaseSelector);

        // Assign button to clearButton, with the name "Reset"
        JButton clearButton = new JButton("Reset");
        clearButton.setFont(defaultFont);
        clearButton.setSize(DEFAULT_WIDTH, 50);
        clearButton.setMaximumSize(clearButton.getSize());
        // Add it to the class
        add(clearButton);

        // Listen for event, if the clear button is pressed
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If it is pressed, then calculate the prefix for each textField
                String selectedInputBase = (String) inputBaseSelector.getSelectedItem();
                String selectedOutputBase = (String) outputBaseSelector.getSelectedItem();

                // And then assign to them.
                if (selectedInputBase.equals("Binary")) {
                    inputField.setText("0b");
                } else if (selectedInputBase.equals("Hexadecimal")) {
                    inputField.setText("0x");
                } else {
                    inputField.setText("0");
                }

                if (selectedOutputBase.equals("Binary")) {
                    outputField.setText("0b");
                } else if (selectedOutputBase.equals("Hexadecimal")) {
                    outputField.setText("0x");
                } else {
                    outputField.setText("0");
                }
            }
        });

        // Assign button to convertButton (run button) with the name "Convert"
        JButton convertButton = new JButton("Convert");
        convertButton.setFont(defaultFont);
        convertButton.setSize(DEFAULT_WIDTH, 50);
        convertButton.setMaximumSize(convertButton.getSize());
        // Add it to the class
        add(convertButton);

        // Listen for event, if the button is pressed
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If the button is pressed, then convert the number and display.
                convertNumber();
            }
        });
    }

    // Update the input field prefix, when trigger.
    private void updateInputFieldPrefix() {
        // Get the current input base (Decimal, Binary, Hexadecimal)
        String selectedInputBase = (String) inputBaseSelector.getSelectedItem();
        // If binary change to 0b... prefix
        if (selectedInputBase.equals("Binary")) {
            inputField.setText("0b");
            // If hexadecimal change to 0x... prefix
        } else if (selectedInputBase.equals("Hexadecimal")) {
            inputField.setText("0x");
        }
        // If it is decimal, do nothing.
    }

    // Validate the input, that contains prefix
    /*
     * 
     * For example: input = "0b10101"
     * 
     * Then this function will cut the prefix and return "10101"
     * 
     */
    private String validateInput() {
        String inputNumber = inputField.getText();

        if (inputNumber.startsWith("0d")) {
            inputNumber = inputNumber.substring(2);
        } else if (inputNumber.startsWith("0b")) {
            inputNumber = inputNumber.substring(2);
        } else if (inputNumber.startsWith("0x")) {
            inputNumber = inputNumber.substring(2);
        }

        return inputNumber;
    }

    // Main function, convert the numbers
    private void convertNumber() {

        // Validate the input, to a calculateable format
        String inputNumber = validateInput();

        // Get the selected input base.
        String inputBase = (String) inputBaseSelector.getSelectedItem();
        String outputBase = (String) outputBaseSelector.getSelectedItem();

        // Move the calculation into the try{}catch{}, so when error happens, we can
        // redirect it.
        try {
            // Check if the number is negativ, if it is negative, return error.
            if (inputBase.equals("Decimal") && inputNumber.startsWith("-") && !outputBase.equals("Decimal")) {
                throw new IllegalArgumentException("Number < 0");
            }
            // If the input is decimal
            if (inputBase.equals("Decimal")) {
                // Parse the input from String to Long format
                long decimalNumber = Long.parseLong(inputNumber);
                // If output is binary, convert to binary
                if (outputBase.equals("Binary")) {
                    outputField.setText(Long.toBinaryString(decimalNumber));
                    // If output is hexadecimal, convert to hexadecimal
                } else if (outputBase.equals("Hexadecimal")) {
                    outputField.setText(Long.toHexString(decimalNumber).toUpperCase());
                    // Else, just return the number.
                } else {
                    outputField.setText(inputNumber); // Output in Decimal
                }
                // The same things.
            } else if (inputBase.equals("Binary")) {
                long decimalNumber = Long.parseLong(inputNumber, 2);
                if (outputBase.equals("Decimal")) {

                    outputField.setText(Long.toString(decimalNumber).toUpperCase());
                } else if (outputBase.equals("Hexadecimal")) {
                    outputField.setText(Long.toHexString(decimalNumber));
                } else {
                    outputField.setText(inputNumber); // Output in Binary
                }
            } else if (inputBase.equals("Hexadecimal")) {
                long decimalNumber = Long.parseLong(inputNumber, 16);
                if (outputBase.equals("Decimal")) {
                    outputField.setText(Long.toString(decimalNumber));
                } else if (outputBase.equals("Binary")) {
                    outputField.setText(Long.toBinaryString(decimalNumber));
                } else {
                    outputField.setText(inputNumber); // Output in Hexadecimal
                }
            }
            // When catch some error
        } catch (NumberFormatException err) { // This case, failed to format number
            // Then return "Invalid Input"
            outputField.setText("Invalid input");
            return;
        } catch (IllegalArgumentException err) { // If the error is something else, then return the full error.
            String error = err.getMessage();
            outputField.setText(error);
            System.out.println(error);
            return;
        }

        // Calculate the prefix for the output
        String prefix = "";
        // If it is binary, add "0b"
        if (outputBaseSelector.getSelectedItem().equals("Binary")) {
            prefix = "0b";
            // If it is hexadecimal, add "0x"
        } else if (outputBaseSelector.getSelectedItem().equals("Hexadecimal")) {
            prefix = "0x";
        }
        // If it is decimal, do nothing, so blank

        // Return output with the calculated output prefix
        outputField.setText(prefix + outputField.getText());
    }

    public static void main(String[] args) {
        // Running the Swing Class
        SwingUtilities.invokeLater(() -> new NumberConverterApp());
    }
}
