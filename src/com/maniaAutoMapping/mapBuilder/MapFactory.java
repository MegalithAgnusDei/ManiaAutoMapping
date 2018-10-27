package com.maniaAutoMapping.mapBuilder;

import com.maniaAutoMapping.Beatmap.Beatmap;

public abstract class MapFactory
{
    Beatmap beatmap = new Beatmap();

    public String getRaw()
    {
        return beatmap.getRaw();
    }

    public abstract void build(int[] data);
}
