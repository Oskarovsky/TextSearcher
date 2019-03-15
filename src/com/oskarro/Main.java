package com.oskarro;

// awt libs
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// swing libs
import javax.swing.*;

import java.io.File;
import java.util.List;


public class Main {

    JFrame frame;

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
    private JButton chooseFileButton;

    //fileChooser
    private JFileChooser fileChooser;
    private File file;
    private JLabel choosedFileLabel;

    // main app panel creating
    public Main() {
        frame = new JFrame("Licznik wyrazów");
        fileChooser = new JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(windowMaker(), BorderLayout.NORTH);
        frame.add(textAreaMaker(), BorderLayout.CENTER);
        frame.add(buttonsMaker(), BorderLayout.SOUTH);
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
        choosedFileLabel = new JLabel("");

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

        window.add(choosedFileLabel);

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

    private JPanel buttonsMaker() {
        JPanel panel = new JPanel();
        startButton = new JButton("Startuj");
        clearButton = new JButton("Clearuj");
        chooseFileButton = new JButton("Wybierz plik");
        panel.add(startButton);
        panel.add(clearButton);
        panel.add(chooseFileButton);
        startButton.addActionListener(new StartListener());
        clearButton.addActionListener(new ListenerCleaner());
        chooseFileButton.addActionListener((new ListenerChooseFile()));
        return panel;
    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(file==null) {
                JOptionPane.showMessageDialog(frame, "Najpierw wybierz plik!", "Brak pliku", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!firstTextField.getText().equals("")) {
                new Thread(new Counter(firstTextField.getText(), textArea,file)).start();
            }

            if (!secondTextField.getText().equals("")) {
                new Thread(new Counter(secondTextField.getText(), textArea,file)).start();
            }

            if (!thirdTextField.getText().equals("")) {
                new Thread(new Counter(thirdTextField.getText(), textArea,file)).start();
            }

            if (!fourTextField.getText().equals("")) {
                new Thread(new Counter(fourTextField.getText(), textArea,file)).start();
            }

            if (!fifthTextField.getText().equals("")) {
                new Thread(new Counter(fifthTextField.getText(), textArea,file)).start();
            }

            if (!sixTextField.getText().equals("")) {
                new Thread(new Counter(sixTextField.getText(), textArea,file)).start();
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

    public class ListenerChooseFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            fileChooser.showOpenDialog(frame);
            file= fileChooser.getSelectedFile();
            choosedFileLabel.setText("Plik: "+file.getName());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
