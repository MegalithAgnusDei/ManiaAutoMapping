package com.maniaAutoMapping.Additional;

public class DataCalculator
{
    public static int getTotalNotes(String raw)
    {
        int result = 0;

        String[] rawArr = raw.split("\n");
        for(int i = 0; i < raw.length(); i++)
        {
            if(raw.charAt(i) == '1')
            {
                result += 1;
            }
        }

        return result;
    }

    public static int getJumps(String raw)
    {
        int result = 0;

        String[] rawArr = raw.split("\n");
        for(int i = 0; i < rawArr.length; i++)
        {
            if(rawArr[i].replaceAll("0", "").length() >= 2)
            {
                result+=rawArr[i].replaceAll("0", "").length();
            }
        }

        return result;
    }

    public static int getJacks(String raw)
    {
        int result = 0;

        String[] rawArr = raw.split("\n");
        String last = "";
        for(int i = 1; i < rawArr.length; i++)
        {
            for(int j = 0; j < rawArr[i].length(); j++)
            {
                if(rawArr[i].charAt(j) == '1' && rawArr[i-1].charAt(j) == '1')
                {
                    result += 1;
                }
            }
        }

        return result;
    }

}
