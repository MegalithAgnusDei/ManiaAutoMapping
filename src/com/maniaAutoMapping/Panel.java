package com.maniaAutoMapping;

import com.maniaAutoMapping.bpmAnalyzer.BPMAnalyzer;
import com.maniaAutoMapping.mapBuilder.DumpBuilder;
import com.maniaAutoMapping.mapBuilder.JacksBuilder;
import com.maniaAutoMapping.mapBuilder.MapFactory;
import com.maniaAutoMapping.mapBuilder.PatternsBuilder;
import it.sauronsoftware.jave.EncoderException;

public class Panel
{
    DataParser dataParser;
    BPMAnalyzer analyzer;
    MapFactory mapFactory = null;

    public Panel()
    {

    }

    public void generateMap() throws Exception
    {
        dataParser = new DataParser(Settings.GeneralSettings.FILE_LOCATION);

        switch (Settings.GeneralSettings.ALGO_TYPE)
        {
            case 0:
            {
                mapFactory = new JacksBuilder();
                break;
            }

            case 1:
            {
                mapFactory = new PatternsBuilder();
                break;
            }

            case 2:
            {
                mapFactory = new DumpBuilder();
                break;
            }
        }

        mapFactory.build(dataParser.getAudioDataByBmp());
    }

    public void analyzeBpm()
    {
        try {
            analyzer = new BPMAnalyzer(Settings.GeneralSettings.FILE_LOCATION);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        analyzer.start();
    }

    public void checkExtension()
    {
        if(Settings.GeneralSettings.FILE_LOCATION.endsWith(".mp3")) {
            try {
                Settings.GeneralSettings.FILE_LOCATION = new com.maniaAutoMapping.Encoder().mp3ToWav(Settings.GeneralSettings.FILE_LOCATION);
                Settings.GeneralSettings.IS_ENCODED = true;
            } catch (EncoderException e1) {
                e1.printStackTrace();
            }
        }
    }

    public String mapFactory_getRaw()
    {
        return mapFactory.getRaw();
    }

    public double dataParser_getDuration()
    {
        return dataParser.getDuration();
    }

    public double analyzer_getMostSuitableBPM()
    {
        return analyzer.mostSuitable.bpm;
    }

    public int analyzer_getMostSuitableOffset()
    {
        return analyzer.mostSuitable.offset;
    }

    public BPMAnalyzer getAnalyzer()
    {
        return analyzer;
    }
}
