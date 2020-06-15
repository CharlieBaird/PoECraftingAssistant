/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting;

import crafting.UI.NumFieldKeyListener;
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
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(singleton);
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int delay = 50;
    public String pathToSound = Utility.getResourcesPath() + "/src/resources/HitSFX1.wav";
    public int volume = 80;
    public int ctrlKey = KeyEvent.VK_CONTROL; // left control
    public boolean invertTool = false;
    
    public void OpenSettings()
    {
        PoECraftingAssistant.stop();
        
        JTextField delayField = new JTextField();
        delayField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        delayField.setText(String.valueOf(Settings.singleton.delay));
        delayField.addKeyListener(new NumFieldKeyListener(false));
        
        JTextField pathToSoundField = new JTextField();
        pathToSoundField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        pathToSoundField.setText(Settings.singleton.pathToSound);
        
        JTextField volumeField = new JTextField();
        volumeField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        volumeField.setText(String.valueOf(Settings.singleton.volume));
        volumeField.addKeyListener(new NumFieldKeyListener(false));
        
//        JCheckBox useAltGr = new JCheckBox();
//        useAltGr.setSelected(ctrlKey != KeyEvent.VK_CONTROL);

        JCheckBox invertToolBox = new JCheckBox();
        invertToolBox.setSelected(invertTool);
        
        Object[] message = {
            "Delay after left click to run Ctrl+C:", delayField,
            "Sound to play on filter hit:", pathToSoundField,
            "Volume: (0-100)", volumeField,
            "Invert tool to ping on not hitting the filter", invertToolBox
//            "Use \"AltGr\" instead of \"Ctrl\" (For non-UK/American keyboards)", useAltGr
        };

        int n = JOptionPane.showConfirmDialog(Main.mainFrame, message, "Settings", JOptionPane.OK_CANCEL_OPTION);
        if (n == JOptionPane.OK_OPTION)
        {
            Settings.singleton.delay = Integer.valueOf(delayField.getText());
            Settings.singleton.pathToSound = pathToSoundField.getText();
            if (Integer.valueOf(volumeField.getText()) <= 100 && Integer.valueOf(volumeField.getText()) >= 0)
                Settings.singleton.volume = Integer.valueOf(volumeField.getText());
            else
                Settings.singleton.volume = 80;
            Settings.singleton.invertTool = invertToolBox.isSelected();
            
//            if (useAltGr.isSelected())
//                Settings.singleton.ctrlKey = KeyEvent.ctrl
//            else
//                Settings.singleton.ctrlKey = KeyEvent.VK_CONTROL;

            Settings.save();
        }
    }
}
