/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File(Utility.getResourcesPath() + "/src/resources/settings.cbsettings"));
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
        } catch (IOException | ClassNotFoundException ex) {
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
    public int volume = 50;
    
    public void OpenSettings()
    {
        PoECraftingAssistant.stop();
        
        JTextField delay = new JTextField();
        delay.setText(String.valueOf(Settings.singleton.delay));
        delay.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() == 8 || ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                   delay.setEditable(true);
                } else {
                   delay.setEditable(false);
                }
            }
        });
        
        JTextField pathToSound = new JTextField();
        pathToSound.setText(Settings.singleton.pathToSound);
//        pathToSound.addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent ke) {
//                if (ke.getKeyChar() == 8 || ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//                   pathToSound.setEditable(true);
//                } else {
//                   pathToSound.setEditable(false);
//                }
//            }
//        });
        
        
        JTextField volume = new JTextField();
        volume.setText(String.valueOf(Settings.singleton.volume));
        volume.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() == 8 || ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                   volume.setEditable(true);
                } else {
                   volume.setEditable(false);
                }
            }
        });
        
        Object[] message = {
            "Delay:", delay,
            "Ping Sound:", pathToSound,
            "Volume: (0-100)", volume
        };

        JOptionPane.showConfirmDialog(null, message, "Settings", JOptionPane.OK_CANCEL_OPTION);
        
        Settings.singleton.delay = Integer.valueOf(delay.getText());
        Settings.singleton.pathToSound = pathToSound.getText();
        if (Integer.valueOf(volume.getText()) <= 100 && Integer.valueOf(volume.getText()) >= 0)
            Settings.singleton.volume = Integer.valueOf(volume.getText());
        else
            Settings.singleton.volume = 80;
        
        
        Settings.save();
    }
}
