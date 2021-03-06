package com.oskarro;

// awt libs
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// swing libs
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class GUI {

    private static int startWordsNumber = 8;

    private JFrame frame;
    private java.util.List<JTextField> textFields;
    private java.util.List<JLabel> labels;

    private JTextArea textArea;
    private JScrollPane scrollPane;

    //file chooser
    private  JFileChooser fileChooser;
    private  File file;
    private JLabel chosenFileLabel;
    private JSpinner spinner;

    private JPanel panel;

    GUI() {
        frame = new JFrame("Licznik wyrazów");

        textFields = new ArrayList<>();
        labels = new ArrayList<>();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panelMaker(startWordsNumber);
        frame.add(panel,BorderLayout.NORTH);
        this.textAreaMaker();
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(this.buttonsMaker(),BorderLayout.SOUTH);
        frame.setSize(500,500);

        frame.setVisible(true);
    }

    private void panelMaker(int textFieldsNumber) {
        panel = new JPanel();

        textFields.clear();
        labels.clear();
        panel.setLayout(new GridLayout(textFieldsNumber+1,5));

        for (int i=0; i<textFieldsNumber; i++){
            JTextField textField = new JTextField(10);
            textFields.add(textField);
            JLabel label = new JLabel("     Podaj słowo numer " + (i+1) + " :");
            labels.add(label);
        }

        for (int i=0;i<textFields.size() && i<labels.size();i++) {
            panel.add(labels.get(i));
            panel.add(textFields.get(i));
        }

        if (file==null) {
            chosenFileLabel = new JLabel("     Wybrany Plik: ...");
        } else {
            chosenFileLabel.setText("      Wybrany Plik: " + file.getName());
        }

        panel.add(chosenFileLabel);
    }

    private void textAreaMaker() {
        JPanel panel = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        panel.setLayout(new BorderLayout());
        panel.add(textArea);
        scrollPane = new JScrollPane(panel);
    }

    private  JPanel buttonsMaker() {
        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JButton clearButton = new JButton("Wyczyść");
        JButton chooseFileButton = new JButton("Wybierz plik");
        JLabel numberLabel = new JLabel("  Liczba wyrazów:");

        panel.add(startButton);
        panel.add(clearButton);
        panel.add(chooseFileButton);
        panel.add(numberLabel);

        startButton.addActionListener(new StartListener());
        clearButton.addActionListener(new ClearListener());

        chooseFileButton.addActionListener(new ChooseFileListener());

        //add spinner for words number
        JSpinner localSpinner = new JSpinner(new SpinnerNumberModel(startWordsNumber,1,10,1));
        JFormattedTextField tf = ((JSpinner.DefaultEditor) localSpinner.getEditor()).getTextField();
        tf.setEditable(false);
        DefaultFormatter formatter = (DefaultFormatter) tf.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        localSpinner.addChangeListener(new NumberChangedListener());

        spinner = localSpinner;
        panel.add(spinner);
        return  panel;
    }

    private class NumberChangedListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent changeEvent){
            frame.remove(panel);

            panelMaker((int)spinner.getValue());

            frame.add(panel,BorderLayout.NORTH);
            frame.validate();
            frame.repaint();
        }
    }

    private  class StartListener implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){

            if (file==null) {
                JOptionPane.showMessageDialog(frame, "Najpierw wybierz plik!",
                        "Brak pliku", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StartSearch startSearch = new StartSearch();
            Thread t = new Thread(startSearch);
            t.start();
        }
    }

    private class ClearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            for (JTextField textField:textFields) {
                textField.setText("");
            }
        }
    }

    private class  ChooseFileListener implements ActionListener{
        @Override
        public  void actionPerformed(ActionEvent e){
            try {
                fileChooser.showOpenDialog(frame);
                file = fileChooser.getSelectedFile();
                chosenFileLabel.setText("      Wybrany Plik: " + file.getName());
            } catch (NullPointerException ex) {
                chosenFileLabel.setText("     Wybrany Plik: ...");
            }
        }
    }

    private class StartSearch implements Runnable {
        @Override
        public void run(){

            textArea.append("\nWyszukiwanie rozpoczęte . . .\n");

            List<String> textsList = new ArrayList<>();

            for (JTextField textField:textFields) {
                textsList.add(textField.getText());
            }

            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<List<Integer>> future = executor.submit(new SearchEngine(textsList,file, textsList.size()));

            List<Integer> counters = new ArrayList<>();
            try {
                counters = future.get();
            } catch (InterruptedException | java.util.concurrent.ExecutionException ex) {
                ex.printStackTrace();
            }

            for (int i = 0; i<counters.size()&&i< textsList.size(); i++){
                textArea.append("Słowo '" + textsList.get(i) + "' wystąpiło " + counters.get(i) +" razy.\n");
            }
            textArea.append("___________________________________________________\n");
        }
    }

}
