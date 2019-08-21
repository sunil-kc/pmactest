package com.pmac.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class Soccer {

    private static final int COL_TEAM_ID = 1;
    private static final int COL_TEAM_NAME = 2;
    private static final int COL_FOR = 7;
    private static final int COL_AGAINST = 9;

    private static final String fileName = "target/classes/soccer.dat";

    private static Integer getIntValue(String string){
        StringBuilder sb = new StringBuilder();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if(Character.isDigit(c)){
                sb.append(c);
            }
        }
        return sb.length() == 0 ? null : Integer.valueOf(sb.toString());
    }

    private static Map<Integer, String> teamMap = new HashMap<>();
    private static Map<Integer,int[]> teamScoreMap = new HashMap<>();

    public static void main(String[] args) {


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(s -> {
                if(!s.isEmpty()) {
                    String[] strArray = s.split("\\s+");
                    if (strArray.length > 10 ) {
                        int teamId = Integer.valueOf(getIntValue(strArray[COL_TEAM_ID]));
                        String team = strArray[COL_TEAM_NAME].trim();
                        int fGoals = Integer.valueOf(getIntValue(strArray[COL_FOR]));
                        int aGaols = Integer.valueOf(getIntValue(strArray[COL_AGAINST]));
                        teamScoreMap.put(teamId, new int[]{fGoals, aGaols});
                        teamMap.put(teamId,team);
                    }
                }
            });
            int spreadMin = teamScoreMap.values().stream()
                    .mapToInt(arr -> arr[0] - arr[1]) /* Not sure if we need  Math.abs() here */
                    .min().getAsInt();
            List<String> spreadMinTeams = new ArrayList<>();
            for(Map.Entry<Integer,int[]> e : teamScoreMap.entrySet()){
                int[] arr = e.getValue();
                if(arr[0] - arr[1] == spreadMin) /* Not sure if we need Math.abs() here */
                    spreadMinTeams.add(teamMap.get(e.getKey()));
            }
            System.out.println("spreadMinTeams: " + spreadMinTeams +", spreadMin: " + spreadMin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
