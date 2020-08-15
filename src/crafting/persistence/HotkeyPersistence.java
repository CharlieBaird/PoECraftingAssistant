package crafting.persistence;

import crafting.UI.Frame;
import crafting.UI.console.Console;
import crafting.UI.hotkeys.HotkeyConfig;
import static crafting.UI.hotkeys.HotkeyConfig.instance;
import crafting.filters.Filter;
import crafting.utility.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HotkeyPersistence {
    
    public static void load()
    {
        File hotkeysFile = null;
        FileInputStream fi = null;
        try {
            hotkeysFile = new File(Utility.getResourcesPath() + "/src/resources/hotkeys.cbhotkeys");
            fi = new FileInputStream(hotkeysFile);
        } catch (FileNotFoundException ex) {
            save();
            load();
            return;
        }
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            instance = (HotkeyConfig) oi.readObject();
        } catch (ClassNotFoundException | InvalidClassException ex) {
            Console.loadingFrame.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(Frame.mainFrame, "The hotkey configuration file could not be loaded. It has been\nautomatically recreated. You will have to reconfigure the hotkeys. Sorry!", "Error", JOptionPane.ERROR_MESSAGE);
            Console.loadingFrame.setAlwaysOnTop(true);
            hotkeysFile.delete();
            save();
            load();
            return;
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void save()
    {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File(Utility.getResourcesPath() + "/src/resources/hotkeys.cbhotkeys"));
        } catch (FileNotFoundException ex) {
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(instance);
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
}
