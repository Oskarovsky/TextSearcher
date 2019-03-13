package com.oskarro;

// awt libs
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// swing libs
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Main {

    // text fields
    private JTextField firstTextField;
    private JTextField secondTextField;
    private JTextField thirdTextField;
    private JTextField fourTextField;
    private JTextField fifthTextField;
    private JTextField sixTextField;
    private JTextArea textArea;

    // buttons
    private JButton startButton;
    private JButton clearButton;

    // main app panel creating
    public Main() {
        JFrame frame = new JFrame("Licznik wyrazów");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(windowMaker(), BorderLayout.NORTH);
        frame.add(textAreaMaker(), BorderLayout.CENTER);
        frame.add(buttonMaker(), BorderLayout.SOUTH);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    // adding elements to the panel
    private JPanel windowMaker() {
        JPanel window = new JPanel();
        window.setLayout(new GridLayout(7, 4));
        firstTextField = new JTextField(10);
        secondTextField = new JTextField(10);
        thirdTextField = new JTextField(10);
        fourTextField = new JTextField(10);
        fifthTextField = new JTextField(10);
        sixTextField = new JTextField(10);

        window.add(new JLabel("Podaj slowo:"));
        window.add(firstTextField);

        window.add(new JLabel("Podaj slowo:"));
        window.add(secondTextField);

        window.add(new JLabel("Podaj slowo:"));
        window.add(thirdTextField);

        window.add(new JLabel("Podaj slowo:"));
        window.add(fourTextField);

        window.add(new JLabel("Podaj slowo:"));
        window.add(fifthTextField);

        window.add(new JLabel("Podaj slowo:"));
        window.add(sixTextField);

        return window;
    }

    private JScrollPane textAreaMaker() {
        JPanel panel = new JPanel();
        textArea = new JTextArea();
        panel.setLayout(new BorderLayout());
        panel.add(textArea);
        JScrollPane scrollPane = new JScrollPane(panel);
        return scrollPane;
    }

    private JPanel buttonMaker() {
        JPanel panel = new JPanel();
        startButton = new JButton("Startuj");
        clearButton = new JButton("Clearuj");
        panel.add(startButton);
        panel.add(clearButton);
        startButton.addActionListener(new StartListener());
        clearButton.addActionListener(new ListenerCleaner());
        return panel;
    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!firstTextField.getText().equals("")) {
                new Thread(new Counter(firstTextField.getText(), textArea)).start();
            }

            if (!secondTextField.getText().equals("")) {
                new Thread(new Counter(secondTextField.getText(), textArea)).start();
            }

            if (!thirdTextField.getText().equals("")) {
                new Thread(new Counter(thirdTextField.getText(), textArea)).start();
            }

            if (!fourTextField.getText().equals("")) {
                new Thread(new Counter(fourTextField.getText(), textArea)).start();
            }

            if (!fifthTextField.getText().equals("")) {
                new Thread(new Counter(fifthTextField.getText(), textArea)).start();
            }

            if (!sixTextField.getText().equals("")) {
                new Thread(new Counter(sixTextField.getText(), textArea)).start();
            }
        }
    }

    public class ListenerCleaner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            firstTextField.setText("");
            secondTextField.setText("");
            thirdTextField.setText("");
            fourTextField.setText("");
            fifthTextField.setText("");
            sixTextField.setText("");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
