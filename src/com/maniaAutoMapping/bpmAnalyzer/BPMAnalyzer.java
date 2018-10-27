package com.maniaAutoMapping.bpmAnalyzer;

import com.maniaAutoMapping.AudioWaveform;
import com.maniaAutoMapping.DiagramsConstructor;
import com.maniaAutoMapping.Settings;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import static com.maniaAutoMapping.Settings.BPMAnalyzerSettings.*;

public class BPMAnalyzer
{
    //ArrayList<double[]> timings = new ArrayList<>();
    AudioWaveform awc;
    public ArrayList<BPMData> data;
    public BPMData mostSuitable = new BPMData(0,0,0);

    public BPMAnalyzer(String fileName) throws Exception {
        String waveformFilename = null;

        awc = new AudioWaveform(fileName, waveformFilename);
        awc.createAudioInputStream();
    }

    public void start()
    {
        data = new ArrayList<>();

        //in milliseconds
        int pivot = 0;//new Random().nextInt((int)awc.duration) * 1000; //sluchaynaya posicia v treke s kotoroi nachnetsa analyze

        //beat per MINUTES
        double[] bpmAnalyzePull = {bpmPullFrom, bpmPullTo};
        double[] oneBeatMS = {1 / bpmAnalyzePull[0] * 60000, 1 / bpmAnalyzePull[1] * 60000};
        double[] oneBeatS = {1 / bpmAnalyzePull[0] * 60, 1 / bpmAnalyzePull[1] * 60};

        //beat
        double bpmStep = Settings.BPMAnalyzerSettings.bpmStep;

        //44000 in 1 sec
        int frameSize = Settings.BPMAnalyzerSettings.frameSize;

        //ms
        int offsetStep = Settings.BPMAnalyzerSettings.offsetStep;

        int hz = (int)(awc.audioData.length / awc.duration );
        double hzInMS = (double)hz / 1000;

        int totalSteps = (int) ((bpmAnalyzePull[1] - bpmAnalyzePull[0] ) / bpmStep);

        for(int k = 0; k < totalSteps; k+=1) // По пуллу бпма
        {
            //                      (замеряемое время в мс     /      милисекунды на один бит)
            int totalBeats = (int)((awc.duration*1000 - pivot) / (1 / (bpmAnalyzePull[0] + k*bpmStep) * 60000));
            int totalOffsetSteps = (int) ((1 / (bpmAnalyzePull[0] + k*bpmStep) * 60000) / offsetStep);
            for(int l = 0; l < totalOffsetSteps; l+=1) { // По оффсету
                long sum = 0;
                for (int j = 0; j < totalBeats; j++) { // По количеству битов
                    long frameSum = 0;
                    for (int i = 0; i < frameSize; i++) { // По размеру окна
                        //                              [откуда начинается + номер бита умноженный на милисекунды за один бит  +  окно]
                        int index = (int) ((pivot + l*offsetStep) * hzInMS + j * (1 / (bpmAnalyzePull[0] + k*bpmStep) * 60000) * hzInMS) + (-frameSize / 2 + i);
                        if(index < 0) index = 0;
                        frameSum += Math.abs(awc.audioData[index]);
                    }
                    int frameAv = (int) (frameSum / frameSize);
                    sum += frameAv;
                }
                int av = (int) (sum/totalBeats);
                data.add(new BPMData(pivot + l*offsetStep,bpmAnalyzePull[0] + k*bpmStep,av));
            }
        }
        findMostSuitable();

        /*
        analyzePart(awc.audioData, 0, awc.audioData.length-1);
        return null;*/
    }

    public void findMostSuitable()
    {
        for(int i = 0; i < data.size(); i++)
        {
            if(data.get(i).value > mostSuitable.value)
            {
                mostSuitable = data.get(i);
            }
        }
    }

    //MULTIPLIE BPM DETECTION BUT NOT WORKING//
    /*private void analyzePart(int[] data, int startIdx, int endIdx)
    {
        ArrayList<BPMData> result = new ArrayList<>();

        //in milliseconds
        int pivot = startIdx;//new Random().nextInt((int)awc.duration) * 1000; //sluchaynaya posicia v treke s kotoroi nachnetsa analyze

        //beat per MINUTES
        double[] bpmAnalyzePull = {bpmPullFrom, bpmPullTo};
        double[] oneBeatMS = {1 / bpmAnalyzePull[0] * 60000, 1 / bpmAnalyzePull[1] * 60000};
        double[] oneBeatS = {1 / bpmAnalyzePull[0] * 60, 1 / bpmAnalyzePull[1] * 60};

        //beat
        double bpmStep = Settings.BPMAnalyzerSettings.bpmStep;

        //44000 in 1 sec
        int frameSize = Settings.BPMAnalyzerSettings.frameSize;

        //ms
        int offsetStep = Settings.BPMAnalyzerSettings.offsetStep;

        int hz = (int)(awc.audioData.length / awc.duration );
        double hzInMS = (double)hz / 1000;

        int totalSteps = (int) ((bpmAnalyzePull[1] - bpmAnalyzePull[0] ) / bpmStep);

        for(int k = 0; k < totalSteps; k+=1) // По пуллу бпма
        {
            //                      (замеряемое время в мс     /      милисекунды на один бит)
            int totalBeats = (int)(((endIdx - startIdx)/hzInMS) / (1 / (bpmAnalyzePull[0] + k*bpmStep) * 60000));
            int totalOffsetSteps = (int) ((1 / (bpmAnalyzePull[0] + k*bpmStep) * 60000) / offsetStep);
            for(int l = 0; l < totalOffsetSteps; l+=1) { // По оффсету
                long sum = 0;
                for (int j = 0; j < totalBeats; j++) { // По количеству битов
                    long frameSum = 0;
                    for (int i = 0; i < frameSize; i++) { // По размеру окна
                        //                              [откуда начинается + номер бита умноженный на милисекунды за один бит  +  окно]
                        int index = (int) ((pivot/hzInMS + l*offsetStep) * hzInMS + j * (1 / (bpmAnalyzePull[0] + k*bpmStep) * 60000) * hzInMS) + (-frameSize / 2 + i);
                        if(index < startIdx) index = startIdx;
                        if(index > endIdx) index = endIdx;
                        frameSum += Math.abs(awc.audioData[index]);
                    }
                    int frameAv = (int) (frameSum / frameSize);
                    sum += frameAv;
                }
                int av = (int) (sum/totalBeats);
                result.add(new BPMData((int) (pivot/hzInMS + l*offsetStep),bpmAnalyzePull[0] + k*bpmStep,av));
            }
        }
        Thread plotThread = new Thread(() -> {
            new SwingWrapper( new DiagramsConstructor().buildBpmDiagram(result)).displayChart();
        });
        plotThread.start();

        long sumVal = 0;
        for (int i = 0; i < result.size(); i++)
        {
            sumVal += result.get(i).value;
        }
        int avgVal = (int) (sumVal/result.size());

        BPMData bestTiming = findMostSuitable(result);

        if(bestTiming.value > avgVal * 1.35)
        {
            timings.add(new double[]{bestTiming.offset, bestTiming.bpm});
        }
        else if((int)(((endIdx - startIdx)/hzInMS) / (1 / bpmAnalyzePull[1] * 60000)) >= 4)
        {
            analyzePart(data,startIdx,endIdx - (endIdx-startIdx)/2);
            analyzePart(data,startIdx + (endIdx-startIdx)/2,endIdx);
        }
        else
        {
            System.out.println("Cant determine");
        }
    }*/

    public ArrayList<BPMData> getData()
    {
        return data;
    }
}
