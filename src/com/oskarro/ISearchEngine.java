package com.oskarro;

import javax.swing.*;
import java.io.File;
import  java.util.List;
import java.util.concurrent.Callable;

public interface ISearchEngine{
    public List<Integer> search(List<String> texts, File file, int threadsNum);
}
