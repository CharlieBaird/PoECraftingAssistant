package crafting.persistence;

import crafting.PoECraftingAssistant;
import crafting.utility.Utility;
import crafting.filters.Filter;
import crafting.UI.Main;
import crafting.UI.NumFieldKeyListener;
import crafting.UI.console.Console;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Settings implements Serializable {
    
    public static Settings singleton = new Settings();
    
    private Settings()
    {
        
    }
    
    public static void load()
    {
        singleton = new Settings();
        File settingsFile = null;
        FileInputStream fi = null;
        try {
            settingsFile = new File(Utility.getResourcesPath() + "/src/resources/settings.cbsettings");
            fi = new FileInputStream(settingsFile);
        } catch (FileNotFoundException ex) {
            save();
            load();
            return;
        }
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            singleton = (Settings) oi.readObject();
        } catch (ClassNotFoundException | InvalidClassException ex) {
            if (Console.loadingFrame != null) Console.loadingFrame.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(Main.mainFrame, "The settings configuration file could not be loaded. It has been\nautomatically recreated. You will have to reconfigure your settings. Sorry!", "Error", JOptionPane.ERROR_MESSAGE);
            if (Console.loadingFrame != null) Console.loadingFrame.setAlwaysOnTop(true);
            settingsFile.delete();
            save();
            load();
            return;
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void save()
    {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File(Utility.getResourcesPath() + "/src/resources/settings.cbsettings"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(singleton);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int delay = 50;
    public String pathToSound = Utility.getResourcesPath() + "/src/resources/HitSFX1.wav";
    public int volume = 80;
    public int ctrlKey = KeyEvent.VK_CONTROL; // left control
    public boolean showPopup = true;
    public boolean disableOnHit = true;
    public String pastebinKey = "";
    
    public void OpenSettings()
    {
        PoECraftingAssistant.stop();
        
        JTextField delayField = new JTextField()
        {
            @Override
            public void paste() {}
        };
        delayField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        delayField.setText(String.valueOf(Settings.singleton.delay));
        delayField.addKeyListener(new NumFieldKeyListener(false));
        
        JTextField pathToSoundField = new JTextField();
        pathToSoundField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        pathToSoundField.setText(Settings.singleton.pathToSound);
        
        JTextField volumeField = new JTextField()
        {
            @Override
            public void paste() {}
        };
        volumeField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        volumeField.setText(String.valueOf(Settings.singleton.volume));
        volumeField.addKeyListener(new NumFieldKeyListener(false));
        
        JCheckBox showPopupBox = new JCheckBox();
        showPopupBox.setSelected(showPopup);
        
        JCheckBox disableOnHitBox = new JCheckBox();
        disableOnHitBox.setSelected(disableOnHit);
        
        JTextField pastebinKeyField = new JTextField();
        pastebinKeyField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        pastebinKeyField.setText(Settings.singleton.pastebinKey);
        
        Object[] message = {
            "Show popup on filter hit", showPopupBox,
            "Disable on filter hit", disableOnHitBox,
            "Delay after left click to run Ctrl+C:", delayField,
            "Sound to play on filter hit:", pathToSoundField,
            "Volume: (0-100)", volumeField,
            "Pastebin developer key for exporting filters", pastebinKeyField
        };

        int n = JOptionPane.showConfirmDialog(Main.mainFrame, message, "Settings", JOptionPane.OK_CANCEL_OPTION);
        if (n == JOptionPane.OK_OPTION)
        {
            Settings.singleton.showPopup = showPopupBox.isSelected();
            Settings.singleton.disableOnHit = disableOnHitBox.isSelected();
            Settings.singleton.delay = Integer.valueOf(delayField.getText());
            Settings.singleton.pathToSound = pathToSoundField.getText();
            if (Integer.valueOf(volumeField.getText()) <= 100 && Integer.valueOf(volumeField.getText()) >= 0)
                Settings.singleton.volume = Integer.valueOf(volumeField.getText());
            else
                Settings.singleton.volume = 80;
            Settings.singleton.pastebinKey = pastebinKeyField.getText();

            Settings.save();
            Main.mainFrame.updateImportExport(Settings.singleton.pastebinKey);
        }
    }
}
