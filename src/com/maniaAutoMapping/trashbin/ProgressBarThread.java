package com.maniaAutoMapping.trashbin;

import javax.swing.*;
import java.util.List;

public class ProgressBarThread extends SwingWorker<Void, Integer> {
    JProgressBar progressBar;
    public ProgressBarThread(JProgressBar progressBar)
    {
        this.progressBar = progressBar;

    }

    @Override
    protected Void doInBackground() throws Exception {

            Thread.sleep(10);
            progressBar.setValue(progressBar.getValue());
            setProgress(10);
        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        int i = chunks.get(chunks.size()-1);
        progressBar.setValue(i);
    }

    public void setValue(int value)
    {
        progressBar.setValue(value);
    }


}