package com.pmac.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FileProcessor {


    protected List<Map<String, String>> rows = new ArrayList<>();
    protected List<String> headers = new ArrayList<>();

    private int colsForValidRow = 0;
    private String fileName = null;

    public FileProcessor(String fileName, int colsForValidRow){
        this.fileName = fileName;
        this.colsForValidRow = colsForValidRow;
    }

    protected void getHeaderRow(String s){
        if(s == null || s.trim().isEmpty())
            return;
        headers = Pattern.compile("\\s+")
                .splitAsStream(s.trim())
                .collect(Collectors.toList());
    }

    protected boolean isHeaderRow(String s){
        if(s == null || s.trim().isEmpty())
            return false;
        return !Character.isDigit(s.trim().charAt(0)) ;
    }

    protected boolean isValidRow(String s){
        return s != null && !s.trim().isEmpty()
                && s.trim().split("\\s+").length >= colsForValidRow;
    }

    protected abstract void getDataRow(String s);

    protected void readFile() {

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(s -> {
                if(isValidRow(s) ) {
                    if(isHeaderRow(s)){
                        getHeaderRow(s);
                    }else {
                        getDataRow(s);
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
