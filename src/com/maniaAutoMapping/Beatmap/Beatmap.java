package com.maniaAutoMapping.Beatmap;

import com.maniaAutoMapping.Additional.NOZ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Beatmap
{
    ArrayList<TimingData> timings = new ArrayList();
    double NORate = 0;

    public Beatmap()
    {

    }

    public void addTimingPoint(double offset, double bpm, double rate, NoteLine[] noteLines)
    {
        TimingPoint tp = new TimingPoint(offset, bpm, rate);
        timings.add(new TimingData(tp, noteLines));

        NORate = NOZ.NOTiming(NORate, tp.rate);
    }

    public void addTimingPoint(double offset, double bpm, double rate, String rawData)
    {
        String[] rawArr = rawData.split("\n");
        NoteLine[] noteLines = new NoteLine[rawArr.length];
        for(int i = 0; i < rawArr.length; i++)
        {
            noteLines[i] = new NoteLine(rawArr[i].length());
            for(int j = 0; j < noteLines[i].getSize();j++)
            {
                if(rawArr[i].charAt(j) == '1')
                {
                    noteLines[i].set(j, new Note());
                }
            }
        }

        TimingPoint tp = new TimingPoint(offset, bpm, rate);
        timings.add(new TimingData(tp, noteLines));

        NORate = NOZ.NOTiming(NORate, tp.rate);
    }


    public String getRaw()
    {
        String raw = new String();

        for (TimingData td: timings) {
            long space = Math.round(td.timingPoint.rate / NORate) - 1;
            //TODO: контролировать длительность
            for(int i = 0; i < td.noteLines.length; i++)
            {
                raw += td.noteLines[i].toString() + "\n";
            }
            for(int i = 0; i < space; i++)
            {
                raw += "0000";
            }
        }

        return raw;
    }

}
