package com.oskarro;

// swing libs
import javax.swing.*;

public class Main {

    public Main() {
        SearchEngine searchEngine = new SearchEngine();

        GUI gui = new GUI(searchEngine);

        gui.frame.setVisible(true);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
