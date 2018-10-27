package com.maniaAutoMapping;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties
{
    public Properties()
    {

    }
    public static void save() throws IOException {
        // Save Settings

        java.util.Properties saveProps = new java.util.Properties();
        saveProps.setProperty("path1", "/somethingpath1");
        saveProps.setProperty("path2", "/somethingpath2");
        saveProps.storeToXML(new FileOutputStream("settings.xml"), "");

        // Load Settings
        java.util.Properties loadProps = new java.util.Properties();
        loadProps.loadFromXML(new FileInputStream("settings.xml"));
        String path1 = loadProps.getProperty("path1");
        String path2 = loadProps.getProperty("path2");
    }
}
