package com.maniaAutoMapping.Beatmap;

import java.util.Iterator;

public class NoteLine
{
    private Note[] notes;

    public NoteLine(int size)
    {
        notes = new Note[size];
    }

    public Note get(int i)
    {
        return notes[i];
    }

    public void set(int i, Note note)
    {
        notes[i] = note;
    }

    public int getSize() { return notes.length;}

    @Override
    public String toString() {
        String line = new String();
        for(int i = 0; i < getSize(); i++)
        {
            if(get(i) == null)
                line += "0";
            else
                line += "1";                //TODO: различные типы нот (В класе NOTE)
        }

        return line;
    }
}
