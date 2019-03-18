package com.oskarro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchEngine implements Callable<List<Integer>> {

    private List<String> texts;
    private File file;
    private int threadsNum;

    SearchEngine(List<String> texts, File file, int threadsNum) {
        this.texts = texts;
        this.file = file;
        this.threadsNum = threadsNum;
    }

    private List<Integer> search(List<String> _texts, File _file, int _threadsNum) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        List<Future<Integer>> futures = new ArrayList<>();

        for (String text : texts) {
            futures.add(executor.submit(new Counter(text, file)));
        }

        List<Integer> results = new ArrayList<>();

        for (Future<Integer> future:futures) {
            try {
                results.add(future.get());

            } catch (InterruptedException | java.util.concurrent.ExecutionException ex) {
                 ex.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public List<Integer> call() {
        return search(texts,file,threadsNum);
    }
}
