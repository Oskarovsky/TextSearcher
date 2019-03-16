package com.oskarro;

// swing libs
import javax.swing.*;

public class Main {

    public Main() {
        GUI gui = new GUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
