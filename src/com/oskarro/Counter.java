package com.oskarro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;


// MULTI THREADING
public class Counter implements Callable<Integer> {

    private String word;
    private File file;

    Counter(String word, File file) {
        this.word = word;
        this.file = file;
    }

    @Override
    public Integer call() {
        try {
            Integer count = 0;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
            {
                String str = scanner.next();
                if (str.equals(word))
                    count++;
            }
            scanner.close();
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
