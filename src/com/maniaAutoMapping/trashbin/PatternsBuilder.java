package com.maniaAutoMapping.trashbin;

import java.util.LinkedList;
import java.util.Random;

import static com.maniaAutoMapping.Settings.GeneralSettings.*;

public class PatternsBuilder
{
    private String raw;
    private int[] data;
    private int totalNotes = 0;
    private String type2LastPattern = "";
    private String [] type3LastPatterns = {"",""};

    public PatternsBuilder()
    {

    }

    public void buildFromData(int[] data)
    {
        this.data = data;
        String str = new String();
        switch (ALGO_TYPE)
        {
            case 0:
            {
                for(int i = 0; i < data.length; i++) {
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int divider;
                    if(IS_SPECIAL_DIVIDER_USED && i % 16 == 0)
                    {
                        divider = SPECIAL_DIVIDER;
                    }
                    else
                    {
                        divider = DIVIDER;
                    }

                    int num = (int) Math.sqrt(Math.abs(data[i])) / (int) Math.sqrt(divider); ///

                    if(num > 4) num = 4;
                    if(num < 0) num = 0;
                    str += genLine(num) + "\n";

                   /* str += genLine(
                            (int) Math.sqrt(Math.abs(data[i])) / (int) Math.sqrt(DIVIDER) > 4 ?
                                    4 : (int) Math.sqrt(Math.abs(data[i])) / (int) Math.sqrt(DIVIDER) < 0 ?
                                    0 : (int) Math.sqrt(Math.abs(data[i])) / (int) Math.sqrt(DIVIDER)) + "\n";*/
                }
                break;
            }
            case 1:
            {
                for(int i = 0; i < data.length; i++) {
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int divider;
                    if(IS_SPECIAL_DIVIDER_USED && i % 16 == 0)
                    {
                        divider = SPECIAL_DIVIDER;
                    }
                    else
                    {
                        divider = DIVIDER;
                    }

                    int num = Math.abs(data[i]) / divider; ///

                    if(num > 4) num = 4;
                    if(num < 0) num = 0;
                    str += genLine(num) + "\n";

                    /*str += genLine(
                            (int) (Math.abs(data[i])) / (int)(DIVIDER) > 4 ?
                                    4 : (int) (Math.abs(data[i])) / (int) (DIVIDER) < 0 ?
                                    0 : (int) (Math.abs(data[i])) / (int) (DIVIDER)) + "\n";*/
                }
                break;
            }
            case 2:
            {
                for(int i = 0; i < data.length; i++) {
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int divider;
                    if(IS_SPECIAL_DIVIDER_USED && i % 16 == 0)
                    {
                        divider = SPECIAL_DIVIDER;
                    }
                    else
                    {
                        divider = DIVIDER;
                    }

                    int num = (int) Math.sqrt(Math.abs(data[i])) / (int) Math.sqrt(divider); ///

                    if(num > 4) num = 4;
                    if(num < 0) num = 0;
                    str += genPatternsLine(num) + "\n";

                }
                break;
            }
            case 3:
            {
                for(int i = 0; i < data.length; i++) {
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int divider;
                    if(IS_SPECIAL_DIVIDER_USED && i % 16 == 0)
                    {
                        divider = SPECIAL_DIVIDER;
                    }
                    else
                    {
                        divider = DIVIDER;
                    }

                    int num = (int) Math.sqrt(Math.abs(data[i])) / (int) Math.sqrt(divider); ///

                    if(num > 4) num = 4;
                    if(num < 0) num = 0;
                    str += genDumpLine(num) + "\n";

                }
                break;
            }
        }

        raw = str;
    }

    private String genLine(int c)
    {
        String [][] combinations = {
                new String[] {"0000"},
                new String[] {"1000", "0100", "0010", "0001"},
                new String[] {"1100", "1010", "1001", "0110", "0101", "0011"},
                new String[] {"1110", "1101", "1011", "0111"},
                new String[] {"1111"},
        };
        totalNotes += c;
        String str = combinations[c][new Random().nextInt( combinations[c].length)];
        return str;
    }

    private String genPatternsLine(int c)
    {
        String [][] combinations = {
                new String[] {"0000"},
                new String[] {"1000", "0100", "0010", "0001"},
                new String[] {"1100", "1010", "1001", "0110", "0101", "0011"},
                new String[] {"1110", "1101", "1011", "0111"},
                new String[] {"1111"},
        };
        totalNotes += c;
        String str;
        //далее говнокод
        if(type2LastPattern.replaceAll("0", "").length() + c <= 4) {
            LinkedList<Integer> forbiddenCombinations = new LinkedList<Integer>();

            for (int i = 0; i < type2LastPattern.length(); i++) {
                if(type2LastPattern.charAt(i) == '1')
                {
                    for(int j = 0; j < combinations[c].length; j++)
                    {
                        if(combinations[c][j].charAt(i) == '1')
                        {
                            forbiddenCombinations.add(j);
                        }
                    }
                }
            }
            for(;;)
            {
                int r = new Random().nextInt( combinations[c].length);
                if(forbiddenCombinations.indexOf(r) == -1) {
                    str = combinations[c][r];
                    break;
                }
            }
        }
        else {
            str = combinations[c][new Random().nextInt(combinations[c].length)];
        }
        type2LastPattern = str;
        return str;
    }

    private String genDumpLine(int c)
    {
        String [][] combinations = {
                new String[] {"0000"},
                new String[] {"1000", "0100", "0010", "0001"},
                new String[] {"1100", "1010", "1001", "0110", "0101", "0011"},
                new String[] {"1110", "1101", "1011", "0111"},
                new String[] {"1111"},
        };
        totalNotes += c;
        String str;

        if(type3LastPatterns[0].replaceAll("0", "").length() + type3LastPatterns[1].replaceAll("0", "").length() + c <= 4) {
            LinkedList<Integer> forbiddenCombinations = new LinkedList<Integer>();

            for (int i = 0; i < Math.min(type3LastPatterns[0].length(), type3LastPatterns[1].length()); i++) {
                if(type3LastPatterns[0].charAt(i) == '1' || type3LastPatterns[1].charAt(i) == '1')
                {
                    for(int j = 0; j < combinations[c].length; j++)
                    {
                        if(combinations[c][j].charAt(i) == '1')
                        {
                            forbiddenCombinations.add(j);
                        }
                    }
                }
            }
            for(;;)
            {
                int r = new Random().nextInt( combinations[c].length);
                if(forbiddenCombinations.indexOf(r) == -1) {
                    str = combinations[c][r];
                    break;
                }
            }
        }
        else if(type3LastPatterns[0].replaceAll("0", "").length()+ c <= 4 )
        {
            LinkedList<Integer> forbiddenCombinations = new LinkedList<Integer>();

            for (int i = 0; i < type3LastPatterns[0].length(); i++) {
                if(type3LastPatterns[0].charAt(i) == '1')
                {
                    for(int j = 0; j < combinations[c].length; j++)
                    {
                        if(combinations[c][j].charAt(i) == '1')
                        {
                            forbiddenCombinations.add(j);
                        }
                    }
                }
            }
            for(;;)
            {
                int r = new Random().nextInt( combinations[c].length);
                if(forbiddenCombinations.indexOf(r) == -1) {
                    str = combinations[c][r];
                    break;
                }
            }
        }
        else {
            str = combinations[c][new Random().nextInt(combinations[c].length)];
        }
        type3LastPatterns[1] = type3LastPatterns[0];
        type3LastPatterns[0] = str;
        return str;
    }


    public String getRaw()
    {
        return raw;
    }

    public int getTotalNotes()
    {
        return totalNotes;
    }
}
