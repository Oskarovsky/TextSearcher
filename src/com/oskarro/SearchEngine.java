package com.oskarro;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Array;
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

    public SearchEngine(List<String> _texts, File _file, int _threadsNum){
        texts=_texts;
        file=_file;
        threadsNum=_threadsNum;
    }

    private List<Integer> search(List<String> _texts, File _file, int _threadsNum) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNum);

        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        for(int i=0;i<texts.size();i++){
            futures.add(executor.submit(new Counter(texts.get(i), file)));
        }

        List<Integer> results = new ArrayList<Integer>();

        for (Future<Integer> future:futures) {

            try{
                results.add(future.get());

            }
            catch (InterruptedException | java.util.concurrent.ExecutionException ex)
            {
                 ex.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public List<Integer> call(){
        return search(texts,file,threadsNum);
    }
}
