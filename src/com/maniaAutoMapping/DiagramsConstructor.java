package com.maniaAutoMapping;

import com.maniaAutoMapping.bpmAnalyzer.BPMData;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;

public class DiagramsConstructor
{
    public DiagramsConstructor()
    {

    }

    public org.knowm.xchart.XYChart buildBpmDiagram(ArrayList<BPMData> data)
    {
        ArrayList<Double> bpms = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        double tempBpm = data.get(0).bpm;
        int tempValue = 0;
        for(int i = 0; i < data.size(); i++)
        {
            if(data.get(i).bpm != tempBpm || i == data.size()-1)
            {
                values.add((double) tempValue);
                bpms.add(tempBpm);
                tempBpm = data.get(i).bpm;
                tempValue = 0;
            }
            if(tempValue < data.get(i).value)
            {
                tempValue = data.get(i).value;
            }

        }
        XYChart chart = new XYChart(500, 300);
        chart.setTitle("");
        chart.setXAxisTitle("BPM");
        chart.setYAxisTitle("Value");
        XYSeries series = chart.addSeries("-", bpms, values);
        series.setMarker(SeriesMarkers.NONE);

        return chart;
    }
}
