package com.oskarro;

import java.io.File;
import  java.util.List;

public interface ISearchEngine{
    List<Integer> search(List<String> texts, File file, int threadsNum);
}
