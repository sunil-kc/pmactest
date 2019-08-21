package com.pmac.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class Weather {

    private static final int COL_DAY = 1;
    private static final int COL_MAX = 2;
    private static final int COL_MIN = 3;

    private static final String fileName = "target/classes/w_data.dat";

    private static Map<Integer,int[]> dayMap = new HashMap<>();

    public static void main(String[] args) {


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(s -> {
                if(!s.isEmpty()) {
                    String[] strArray = s.split("\\s+");
                    if (strArray.length > 3){
                        Integer day = Util.getIntValue(strArray[COL_DAY].trim());
                        if(day != null) {
                            int max = Integer.valueOf(Util.getIntValue(strArray[COL_MAX]));
                            int min = Integer.valueOf(Util.getIntValue(strArray[COL_MIN]));
                            dayMap.put(day,new int[]{max, min});
                        }
                    }
                }
            });
            int spreadMin = dayMap.values().stream().mapToInt(arr -> arr[0] - arr[1]).min().getAsInt();
            List<Integer> spreadMinDays = new ArrayList<>();
            for(Map.Entry<Integer, int[]> e : dayMap.entrySet()){
                int[] arr = e.getValue();
                if(arr[0] - arr[1] == spreadMin)
                    spreadMinDays.add(e.getKey());
            }
            System.out.println("spreadMinDay: " + spreadMinDays +", spreadMin: " + spreadMin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
