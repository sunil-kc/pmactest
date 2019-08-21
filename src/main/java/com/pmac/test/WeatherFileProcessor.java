package com.pmac.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class WeatherFileProcessor extends FileProcessor {

    private static final String DAY = "Dy";
    private static final String MAX = "MxT";
    private static final String MIN = "MnT";

    protected void getDataRow(String s){
        if(s == null || s.trim().isEmpty())
            return;
        String[] strArray = s.split("\\s+");
        int col = 0;
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < strArray.length; i++) {
            if(i == 0)
                continue;
            else {
                map.put(headers.get(col), strArray[i]);
                col++;
            }
        }
        rows.add(map);
    }

    private int getValue(Map<String, String> map){
        return Util.getIntValue(map.get(MAX)) - Util.getIntValue(map.get(MIN));
    }

    protected void readFile(){
        super.readFile();
        int spreadMin = rows.stream()
                .mapToInt(row -> getValue(row))
                .min().getAsInt();
        List<String> spreadMinDays = new ArrayList<>();
        for(Map<String, String> row : rows){
            if(getValue(row) == spreadMin)
                spreadMinDays.add(row.get(DAY));
        }
        System.out.println("spreadMinDay: " + spreadMinDays +", spreadMin: " + spreadMin);
    }

    public WeatherFileProcessor(String fileName, int colsForValidRow) {
        super(fileName, colsForValidRow);
    }

    public static void main(String[] args) {

        WeatherFileProcessor weatherFileProcessor
                = new WeatherFileProcessor("target/classes/w_data.dat", 15);
        weatherFileProcessor.readFile();

    }

}
