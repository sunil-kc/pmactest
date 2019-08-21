package com.pmac.test;

public class Util {
    public static Integer getIntValue(String string){
        StringBuilder sb = new StringBuilder();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if(Character.isDigit(c)){
                sb.append(c);
            }
        }
        return sb.length() == 0 ? null : Integer.valueOf(sb.toString());
    }


}
