package com.oskarro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

// MULTI THREADING
public class Counter implements Runnable {

    private String word;
    private JTextArea textArea;
    private int count = 0;
    private File f;

    public Counter(String word, JTextArea textArea, File file) {
        this.word = word;
        this.textArea = textArea;
        f = file;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext())
            {
                String str = scanner.next();
                if (str.equals(word))
                    count++;
            }
            SwingUtilities.invokeLater(() -> {
                textArea.append("Wyraz '" + word + "' wystąpił: " + count + " razy\n");
            });
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
