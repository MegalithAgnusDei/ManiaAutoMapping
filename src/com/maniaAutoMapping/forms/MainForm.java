package com.maniaAutoMapping.forms;

import com.maniaAutoMapping.*;
import com.maniaAutoMapping.Additional.DataCalculator;
import it.sauronsoftware.jave.EncoderException;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainForm extends JFrame
{
    private JPanel panel1;
    private JLabel waveformLabel;
    private JScrollPane graphsScrollPane;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextField fileLocation;
    private JTextField bpmField;
    private JTextField offsetField;
    private JComboBox rateCBox;
    private JComboBox algoType;
    private JSlider divSlider;
    private JSlider defSlider;
    private JEditorPane previewPane;
    private JScrollPane previewScrollPane;
    private JButton startButton;
    private JButton openFileButton;
    private JLabel logLabel;
    private JLabel addinfo_totalNotes;
    private JButton convertButton1;
    private JProgressBar progressBar;
    private JSlider sleepSlider;
    private JCheckBox showPreviewCheckBox;
    private JCheckBox specialDividerFor1CheckBox;
    private JSlider specalDivivder;
    private JButton analyzeButton;
    private JButton analyzeBPMButton;
    private JTextField bpmPullFrom;
    private JTextField bpmPullTo;
    private JTextField bpmStep;
    private JTextField offsetStep;
    private JTextField frameSize;
    private JButton saveAsOszButton;
    private JPanel bpmDiagramPanel;
    private JPanel graphsTab;
    private JSlider sqrtSlider;
    private JTextPane textPane1;
    private JComboBox sqrtCBox;

    ///////////////////////////////////////////////
    boolean debug = false;
    ///////////////////////////////////////////////
    Panel panel = new Panel();

    public MainForm()
    {
        setContentPane(panel1);
        setSize(800,600);
        setLocation(300,100);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loadListeners();

        if(debug)
        {
            loadDebug();
        }
    }

    private void generate()
    {
        try {
            panel.generateMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        textArea1.setText(panel.mapFactory_getRaw());

        if(showPreviewCheckBox.isSelected()) {
            loadPreview(panel.mapFactory_getRaw());
        }
    }

    private void showAddInfo()
    {
        String text = "" +
                "Total notes: "+    DataCalculator.getTotalNotes(panel.mapFactory_getRaw()) + "\n" +
                "Jumps: " +         DataCalculator.getJumps(panel.mapFactory_getRaw())+"\n" +
                "Jacks: " +         DataCalculator.getJacks(panel.mapFactory_getRaw()) + "\n" +
                "----------------------------------------------\n"+
                "Average NPS: " +   (double)Math.round((DataCalculator.getTotalNotes(panel.mapFactory_getRaw()) / panel.dataParser_getDuration())*1000)/1000 + "\n";
        textPane1.setText(text);
    }

    private void loadPreview(String raw)
    {
        previewPane.setEditorKit(new HTMLEditorKit());
        String[] rawMas = raw.split("\n");
        String html = new String();
        html += "<table style=\"background:black; \">";///no) no)) no no nooo))))
        for(int i = rawMas.length-1; i >= 0; i--) {
            final int percent = 100*(rawMas.length-1 - i)/(rawMas.length-1);
            html += "<tr>";
            for (int j = 0; j < 4; j++) {
                if(rawMas[i].charAt(j) == '1'){
                    html += "<td style=\"width:40px; height:10px; background:white; \"></td>";
                }
                else
                {
                    html += "<td style=\"width:40px; height:10px; background:black;\"></td>";
                }
            }
            html += "</tr>";
        }
        html += "</table>";

        previewPane.setText(html);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        previewScrollPane.getVerticalScrollBar().setValue( previewScrollPane.getVerticalScrollBar().getMaximum() );
    }

    private void buildBpmPlot()
    {
        org.knowm.xchart.XYChart xyChart = new DiagramsConstructor().buildBpmDiagram(panel.getAnalyzer().data);

        for(int i = 0; i < bpmDiagramPanel.getComponentCount(); i++) {
            bpmDiagramPanel.remove(i);
        }

        JPanel fdd = new JPanel();
        JPanel bpmDiagramPanel1 = new XChartPanel(xyChart);
        fdd.add(bpmDiagramPanel1);
        bpmDiagramPanel.add(fdd);
        bpmDiagramPanel.validate();
    }

    private void loadListeners()
    {
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc;
                if(Settings.GeneralSettings.LAST_FOLDER == null)
                    jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                else
                    jfc = new JFileChooser(new File(Settings.GeneralSettings.LAST_FOLDER));

                jfc.setDialogTitle("Select a song");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("wav, mp3", "wav","mp3");
                jfc.addChoosableFileFilter(filter);

                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    fileLocation.setText(jfc.getSelectedFile().getPath());
                    Settings.GeneralSettings.IS_ENCODED = false;
                    Settings.GeneralSettings.LAST_FOLDER = jfc.getSelectedFile().getParent();
                    Settings.GeneralSettings.SONG_TITLE = jfc.getSelectedFile().getName();
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseSettings();
                checkExtension();
                generate();
                showAddInfo();
            }
        });

        analyzeBPMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseSettings();
                checkExtension();
                parseBPMSettings();

                panel.analyzeBpm();
                bpmField.setText(String.valueOf(panel.analyzer_getMostSuitableBPM()));
                offsetField.setText(String.valueOf(panel.analyzer_getMostSuitableOffset()));


                buildBpmPlot();
            }
        });

        convertButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea2.setText(new Converter().rawToOsu(panel.mapFactory_getRaw()));
            }
        });

        saveAsOszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Converter().saveAsOsz(panel.mapFactory_getRaw());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (EncoderException e1) {
                    e1.printStackTrace();
                }
            }
        });

        specialDividerFor1CheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                specalDivivder.setEnabled(specialDividerFor1CheckBox.isSelected());
            }
        });
    }

    private void checkExtension()
    {
        panel.checkExtension();
    }

    private void parseSettings()
    {
        if(bpmField.getText() != null && bpmField.getText() .length() > 0)
            Settings.GeneralSettings.BPM = Double.parseDouble(bpmField.getText());
        if(offsetField.getText() != null && offsetField.getText() .length() > 0)
            Settings.GeneralSettings.OFFSET = Double.parseDouble(offsetField.getText());
        Settings.GeneralSettings.ALGO_TYPE = algoType.getSelectedIndex();
        Settings.GeneralSettings.DIVIDER = divSlider.getValue();
        Settings.GeneralSettings.DEFINITION = defSlider.getValue();
        if(sqrtCBox.getSelectedIndex() == 0)
            Settings.GeneralSettings.SQRT = sqrtSlider.getValue();
        else
            Settings.GeneralSettings.SQRT = (double)5/(double)(4+sqrtSlider.getValue());
        if(!Settings.GeneralSettings.IS_ENCODED)
            Settings.GeneralSettings.FILE_LOCATION = fileLocation.getText();
        Settings.GeneralSettings.SLEEP = sleepSlider.getValue();
        Settings.GeneralSettings.IS_SPECIAL_DIVIDER_USED = specialDividerFor1CheckBox.isSelected();
        Settings.GeneralSettings.SPECIAL_DIVIDER = specalDivivder.getValue();
        switch (rateCBox.getSelectedIndex())
        {
            case 0:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 32;
                break;
            }
            case 1:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 24;
                break;
            }
            case 2:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 16;
                break;
            }
            case 3:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 12;
                break;
            }
            case 4:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 8;
                break;
            }
            case 5:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 6;
                break;
            }
            case 6:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 4;
                break;
            }
            case 7:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 3;
                break;
            }
            case 8:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = (double)1 / (double) 2;
                break;
            }
            case 9:
            {
                Settings.GeneralSettings.TICKS_PER_BPM = 1;
                break;
            }
        }
        int a = 0;
    }

    private void parseBPMSettings()
    {
        Settings.BPMAnalyzerSettings.bpmPullFrom = Double.parseDouble(bpmPullFrom.getText());
        Settings.BPMAnalyzerSettings.bpmPullTo = Double.parseDouble(bpmPullTo.getText());
        Settings.BPMAnalyzerSettings.bpmStep = Double.parseDouble(bpmStep.getText());
        Settings.BPMAnalyzerSettings.frameSize = Integer.parseInt(frameSize.getText());
        Settings.BPMAnalyzerSettings.offsetStep = Integer.parseInt(offsetStep.getText());
    }

    private void loadDebug()
    {

    }


    private void createUIComponents() {
        bpmDiagramPanel = new JPanel();//new XChartPanel<org.knowm.xchart.XYChart>(new org.knowm.xchart.XYChart(300,200));

    }
}
