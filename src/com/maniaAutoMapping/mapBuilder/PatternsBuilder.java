package com.maniaAutoMapping.mapBuilder;

import java.util.LinkedList;
import java.util.Random;

import static com.maniaAutoMapping.Settings.GeneralSettings.*;

public class PatternsBuilder extends MapFactory
{
    private String type2LastPattern = "";

    @Override
    public void build(int[] data) {
        String str = new String();
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

            int num = (int) Math.pow(
                    Math.pow(Math.abs(data[i]),(double)1/(double)SQRT) / (int) Math.pow(divider,(double)1/(double)SQRT)
                    ,SQRT);///

            if(num > 4) num = 4;
            if(num < 0) num = 0;
            str += genPatternsLine(num) + "\n";

        }
        beatmap.addTimingPoint(OFFSET, BPM, TICKS_PER_BPM, str);
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
}
