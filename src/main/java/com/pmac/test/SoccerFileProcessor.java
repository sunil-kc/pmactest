package com.pmac.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SoccerFileProcessor extends FileProcessor {

    private static final String FOR = "F";
    private static final String AGAINST = "A";
    private static final String TEAM = "Team";

    public SoccerFileProcessor(String fileName, int colsForValidRow) {
        super(fileName, colsForValidRow);
    }

    protected void getDataRow(String s){
        if(s == null || s.trim().isEmpty())
            return;
        String[] strArray = s.split("\\s+");
        int col = 0;
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < strArray.length; i++) {
            if(i == 0 || i == 1 || i == 8)
                continue;
            else {
                map.put(headers.get(col), strArray[i]);
                col++;
            }
        }
        rows.add(map);
    }

    private int getValue(Map<String, String> map){
        return Util.getIntValue(map.get(FOR)) - Util.getIntValue(map.get(AGAINST));
    }

    protected void readFile(){
        super.readFile();
        int spreadMin = rows.stream()
                .mapToInt(row -> getValue(row))
                .min().getAsInt();
        List<String> spreadMinTeams = new ArrayList<>();
        for(Map<String, String> row : rows){
            if(getValue(row) == spreadMin)
                spreadMinTeams.add(row.get(TEAM));
        }
        System.out.println("spreadMinTeams: " + spreadMinTeams +", spreadMin: " + spreadMin);
    }

    public static void main(String[] args) {

        SoccerFileProcessor soccerFileProcessor
                = new SoccerFileProcessor("target/classes/soccer.dat", 8);
        soccerFileProcessor.readFile();

    }
}
