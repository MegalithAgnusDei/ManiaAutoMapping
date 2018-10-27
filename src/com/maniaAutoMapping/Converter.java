package com.maniaAutoMapping;

import it.sauronsoftware.jave.EncoderException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.maniaAutoMapping.Settings.GeneralSettings.*;

public class Converter
{
    public Converter()
    {

    }

    public String rawToOsu(String raw)
    {
        String[] rawMas = raw.split("\n");
        String output = new String();
        for(int i = 0; i < rawMas.length; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(rawMas[i].charAt(j) == '1')
                {
                    switch (j){
                        case 0:
                        {
                            output += "64,";
                            break;
                        }
                        case 1:
                        {
                            output += "192,";
                            break;
                        }
                        case 2:
                        {
                            output += "320,";
                            break;
                        }
                        case 3:
                        {
                            output += "448,";
                            break;
                        }
                    }

                    output += "192,";

                    output += (int)(OFFSET + i * TICKS_PER_BPM / BPM * 60000) + ",";

                    output += "1,0,0:0:0:0:\n";
                }
            }
        }

        return output;
    }

    public void saveAsOsz(String raw) throws IOException, EncoderException {
        String creator = "nuze";
        String difName = "automap";
        String source = "";
        String tags = "";

        String data = "";
        data += "osu file format v14\n\n";
        data += "[General]\n";
        data += "AudioFilename: audio.mp3\n";
        data += "AudioLeadIn: 0\n";
        data += "PreviewTime: -1\n";
        data += "Countdown: 0\n";
        data += "SampleSet: Normal\n";
        data += "StackLeniency: 0.7\n";
        data += "Mode: 3\n";
        data += "LetterboxInBreaks: 0\n";
        data += "SpecialStyle: 0\n";
        data += "WidescreenStoryboard: 0\n\n";

        data += "[Editor]\n";
        data += "DistanceSpacing: 2\n";
        data += "BeatDivisor: 8\n";
        data += "GridSize: 16\n";
        data += "TimelineZoom: 1\n\n";

        data += "[Metadata]\n";
        data += "Title:"+Settings.GeneralSettings.SONG_TITLE+"\n";
        data += "TitleUnicode:"+Settings.GeneralSettings.SONG_TITLE+"\n";
        data += "Artist:"+Settings.GeneralSettings.SONG_ARTIST+"\n";
        data += "ArtistUnicode:"+Settings.GeneralSettings.SONG_ARTIST+"\n";
        data += "Creator:"+creator+"\n";
        data += "Version:"+difName+"\n";
        data += "Source:"+source+"\n";
        data += "Tags:"+tags+"\n";
        data += "BeatmapID:0\n";
        data += "BeatmapSetID:-1\n\n";

        data += "[Difficulty]\n";
        data += "HPDrainRate:8\n";
        data += "CircleSize:4\n";
        data += "OverallDifficulty:8\n";
        data += "ApproachRate:5\n";
        data += "SliderMultiplier:1.4\n";
        data += "SliderTickRate:1\n\n";


        data += "[Events]\n\n";

        data += "[TimingPoints]\n";
        data += (int)Settings.GeneralSettings.OFFSET+","+(1/Settings.GeneralSettings.BPM*60000)+",4,1,0,100,1,0\n\n";

        data += "[HitObjects]\n";
        data += rawToOsu(raw);



        File file = new File("temp/"+Settings.GeneralSettings.SONG_TITLE + " - " + Settings.GeneralSettings.SONG_ARTIST + " ("+ creator + ") ["+ difName+"].osu");
        FileWriter stringWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        File song = new File(Settings.GeneralSettings.FILE_LOCATION);
        Encoder encoder = new Encoder();
        encoder.wavToMp3(FILE_LOCATION, "temp/audio.mp3");

        List<String> srcFiles = Arrays.asList(file.getPath(), "temp/audio.mp3");
        String oszFileLoc = "temp/"+Settings.GeneralSettings.SONG_TITLE + " - " + Settings.GeneralSettings.SONG_ARTIST + " ("+ creator + ") ["+ difName+"].osz";
        FileOutputStream fos = new FileOutputStream(oszFileLoc);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();

        String newLocation = Settings.GeneralSettings.LAST_FOLDER+"\\"+Settings.GeneralSettings.SONG_TITLE + " - " + Settings.GeneralSettings.SONG_ARTIST + " ("+ creator + ") ["+ difName+"].osz";
        new File(oszFileLoc).renameTo(new File(newLocation));

        /*

        File song = new File(Settings.GeneralSettings.FILE_LOCATION);
        Encoder encoder = new Encoder();
        encoder.wavToMp3(FILE_LOCATION, "temp/audio.mp3");
        String[] paths = new String[]{file.getPath(), "temp/audio.mp3"};
        ZipEntry[] entry=new ZipEntry[]{new ZipEntry(file.getName()), new ZipEntry("audio.mp3")};
        for(int i = 0; i < entry.length ; i++)
        {
            try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("temp/"+Settings.GeneralSettings.SONG_TITLE + " - " + Settings.GeneralSettings.SONG_ARTIST + " ("+ creator + ") ["+ difName+"].zip"));
                FileInputStream fis= new FileInputStream(paths[i]);)
            {
                zout.putNextEntry(entry[i]);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
            catch(Exception ex){

                System.out.println(ex.getMessage());
            }
        }
*/
    }
}
