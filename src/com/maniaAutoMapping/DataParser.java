package com.maniaAutoMapping;

import static com.maniaAutoMapping.Settings.GeneralSettings.*;

public class DataParser
{
    AudioWaveform awc;
    public DataParser(String fileName) throws Exception
    {
        String waveformFilename = null;

        awc = new AudioWaveform(fileName, waveformFilename);
        awc.createAudioInputStream();
    }

    public int[] getAudioDataByBmp()
    {
        double totalTicks = (awc.duration * 1000 - OFFSET) / 1000 / 60 * BPM / TICKS_PER_BPM;
        int[] data = new int[(int)totalTicks];

        int m = awc.audioData.length/data.length;
        double b = ( awc.audioData.length / awc.duration / 1000 * OFFSET);
        int[] dow = new int[(int) DEFINITION];
        for(int i = 0; i < data.length; i++)
        {
            int sum = 0;
            for (int j = 0; j < dow.length; j++) {
                sum += Math.abs(
                        awc.audioData[
                                (int)(b +i*m - dow.length / 2 + j) < 0 ?
                                    0 : (int)(b +i*m - dow.length / 2 + j) > awc.audioData.length-1 ?
                                    awc.audioData.length-1 : (int)(b +i*m - dow.length / 2 + j)]
                );
            }
            data[i] = (int) (sum/DEFINITION);
           // data[i] = awc.audioData[i * m];
        }
        int s = 9;
      /*ArrayList<Integer> list = new ArrayList<Integer>();
        int[] dow = new int[10000];

        for(int i = 0; i < awc.audioData.length; i++){
            loop:
            {
                for (int j = 0; j < dow.length; j++) {
                    dow[j] = awc.audioData[(i - dow.length / 2 + j) < 0 ? 0 : (i - dow.length / 2 + j) > awc.audioData.length-1 ? awc.audioData.length-1 : (i - dow.length / 2 + j)];
                }
                for (int j = 0; j < dow.length; j++) {
                    if (awc.audioData[i] < dow[j]) {
                        break loop;
                    }
                }
                 list.add(awc.audioData[i]);
            }
        }
        list.get(0);*/
        return data;
    }

    public double getDuration()
    {
        return awc.duration;
    }

}
