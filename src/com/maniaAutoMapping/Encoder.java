package com.maniaAutoMapping;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class Encoder
{
    public Encoder()
    {

    }

    public String mp3ToWav(String path) throws EncoderException {
        File source = new File(path);
        File target = new File("temp/temp.wav");
        AudioAttributes audio = new AudioAttributes();
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        it.sauronsoftware.jave.Encoder encoder = new it.sauronsoftware.jave.Encoder();
        encoder.encode(source, target, attrs);
        return target.getPath();
    }

    public String wavToMp3(String path, String output) throws EncoderException {
        File source = new File(path);
        File target = new File(output);
        AudioAttributes audio = new AudioAttributes();
        audio.setBitRate(192);
        audio.setChannels(2);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        it.sauronsoftware.jave.Encoder encoder = new it.sauronsoftware.jave.Encoder();
        encoder.encode(source, target, attrs);
        return target.getPath();
    }
}
