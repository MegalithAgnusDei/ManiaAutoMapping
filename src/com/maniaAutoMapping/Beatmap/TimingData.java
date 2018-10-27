package com.maniaAutoMapping.Beatmap;

public class TimingData
{
    TimingPoint timingPoint;
    NoteLine[] noteLines;

    public TimingData(TimingPoint tp, NoteLine[] nls)
    {
        timingPoint = tp;
        noteLines = nls;
    }
}
