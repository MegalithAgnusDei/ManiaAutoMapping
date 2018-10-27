package com.maniaAutoMapping.bpmAnalyzer;


public class BPMData
{
    public BPMData(int offset, double bpm, int value)
    {
        this.offset = offset;
        this.bpm = bpm;
        this.value = value;
    }
    public int offset;
    public double bpm;
    public int value;
}
