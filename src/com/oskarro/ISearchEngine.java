package com.oskarro;

import javax.swing.*;
import java.io.File;
import  java.util.List;

public interface ISearchEngine {
    public void search(JFrame frame, List<String> texts, File file);
}
