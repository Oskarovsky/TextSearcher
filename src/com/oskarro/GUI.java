package com.oskarro;

// awt libs
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// swing libs
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GUI {

    private static int startWordsNumber = 8;

    public JFrame frame;
    private java.util.List<JTextField> textFields;
    private java.util.List<JLabel> labels;
    private java.util.List<String> textsList;

    private JButton startButton;
    private JButton clearButton;
    private JButton chooseFileButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    //file chooser
    private  JFileChooser fileChooser;
    private  File file;
    private JLabel choosedFileLabel;
    private JLabel numberLabel;
    private JSpinner spinner;

    private JPanel panel;

    public GUI(){
        textFields = new ArrayList<JTextField>();
        labels = new ArrayList<JLabel>();

        frame = new JFrame("Licznik wyrazów");
        fileChooser = new JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("TEXT FILES", "txt", "text");
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

    private void panelMaker(int textFieldsNumber){
        panel = new JPanel();

        textFields.clear();
        labels.clear();
        panel.setLayout(new GridLayout(textFieldsNumber+1,5));
        
        for (int i=0;i<textFieldsNumber;i++){
            JTextField textField = new JTextField(10);
            textFields.add(textField);
            JLabel label = new JLabel("     Podaj słowo numer "+(i+1)+" :");
            labels.add(label);
        }

        for (int i=0;i<textFields.size() && i<labels.size();i++)
        {
            panel.add(labels.get(i));
            panel.add(textFields.get(i));
        }

        if(file==null) {
            choosedFileLabel = new JLabel("     Wybrany Plik: ...");
        }
        else{
            choosedFileLabel.setText("      Wybrany Plik: "+file.getName());
        }
        panel.add(choosedFileLabel);

    }

    private void textAreaMaker(){
        JPanel panel = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        panel.setLayout(new BorderLayout());
        panel.add(textArea);
        scrollPane = new JScrollPane(panel);
    }

    private  JPanel buttonsMaker(){
        JPanel panel = new JPanel();
        startButton = new JButton("Start");
        clearButton = new JButton("Wyczyść");
        chooseFileButton = new JButton("Wybierz plik");
        panel.add(startButton);
        panel.add(clearButton);
        panel.add(chooseFileButton);
        startButton.addActionListener(new StartListener());
        clearButton.addActionListener(new ClearListener());
        chooseFileButton.addActionListener(new ChooseFileListener());

        numberLabel = new JLabel("  Liczba wyrazów:");
        panel.add(numberLabel);
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

            if(file==null) {
                JOptionPane.showMessageDialog(frame, "Najpierw wybierz plik!", "Brak pliku", JOptionPane.WARNING_MESSAGE);
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
            fileChooser.showOpenDialog(frame);
            file= fileChooser.getSelectedFile();
            choosedFileLabel.setText("      Wybrany Plik: "+file.getName());
        }
    }

    private class StartSearch implements Runnable {
        @Override
        public void run(){

            textArea.append("\nWyszukiwanie rozpoczęte . . .\n");

            textsList = new ArrayList<String>();

            for (JTextField textField:textFields) {
                textsList.add(textField.getText());
            }
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<List<Integer>> future = executor.submit(new SearchEngine(textsList,file,textsList.size()));

            List<Integer> counters = new ArrayList<Integer>();
            try{
                counters = future.get();
            }
            catch (InterruptedException | java.util.concurrent.ExecutionException ex)
            {
                ex.printStackTrace();
            }

            for (int i=0;i<counters.size()&&i<textsList.size();i++){
                textArea.append("Słowo '" + textsList.get(i) + "' wystąpiło " + counters.get(i) +" razy.\n");
            }
            textArea.append("___________________________________________________\n");
        }
    }

}
