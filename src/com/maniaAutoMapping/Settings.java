package com.maniaAutoMapping;

public class Settings
{
    public static class GeneralSettings {
        public static double BPM;
        public static double OFFSET;
        public static double TICKS_PER_BPM;
        public static int ALGO_TYPE;
        public static double DEFINITION;
        public static int DIVIDER;
        public static double SQRT;
        public static String LAST_FOLDER;
        public static String FILE_LOCATION;
        public static boolean IS_ENCODED;
        public static int SLEEP;
        public static boolean IS_SPECIAL_DIVIDER_USED;
        public static int SPECIAL_DIVIDER;
        public static String SONG_TITLE;
        public static String SONG_ARTIST;
    }

    public static class BPMAnalyzerSettings
    {
        public static double bpmPullFrom;
        public static double bpmPullTo;
        public static double bpmStep;
        public static int frameSize;
        public static int offsetStep;
    }
}
