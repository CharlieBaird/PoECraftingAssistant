/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import poeitem.Modifier;
import static poeitem.Modifier.AllExplicitModifiers;

public class Utility {
   
    public static void delay(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(PoECraftingAssistant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String copy() throws AWTException, UnsupportedFlavorException, IOException
    {
        Robot bot = new Robot();
        try {
            bot.keyPress(Settings.singleton.ctrlKey);
        } catch (IllegalArgumentException ex) {
            if (Settings.singleton.ctrlKey == KeyEvent.VK_CONTROL)
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "Unable to fire the CTRL key.\nTry changing to the ALTGR key in settings.", "Failure", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "Unable to fire the ALTGR key.\nTry changing to the CTRL key in settings.", "Failure", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
        delay(10);
        bot.keyPress(KeyEvent.VK_C); 
        delay(5);
        bot.keyRelease(KeyEvent.VK_C); 
        bot.keyRelease(Settings.singleton.ctrlKey); 
        delay(5);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        bot = null;
        String cc = null;
        try {
            cc = (String) c.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException | IllegalStateException e) {
            System.out.println(e);
        }
        return cc;
    }
    
    public static String getResourcesPath()
    {
        String path = System.getProperty("user.dir");
        path = path.replace("\\target", "");
        return path;
    }
    
    public static void playHitSound()
    {
        File clipFile = new File(Settings.singleton.pathToSound);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            playPrebuiltSound();
            JOptionPane.showMessageDialog(Main.mainFrame, "An error occurred with your custom sound."
                    + "\nThe default sound was played instead.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(clipFile);
        } catch (UnsupportedAudioFileException ex) {
            playPrebuiltSound();
            JOptionPane.showMessageDialog(Main.mainFrame, "An error occurred. The audio file you have selected is in the wrong format. Please use the .wav format."
                    + "\nThe default sound was played instead.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException ex) {
            playPrebuiltSound();
            JOptionPane.showMessageDialog(Main.mainFrame, "Your sound file could not found. Please check the path again."
                    + "\nThe default sound was played instead.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//            gainControl.setValue((float) ((float) Settings.singleton.volume-50f)/8.3f);
            double volToSet = map(0,100,gainControl.getMinimum(),gainControl.getMaximum(),Settings.singleton.volume);
//            System.out.println("volToSet: " + volToSet);
            gainControl.setValue((float) volToSet);
            

//            System.out.println(gainControl.getMaximum());
//            System.out.println(gainControl.getMinimum());
//            System.out.println(gainControl.getPrecision());


            clip.loop(0);
            clip.start();
        } catch (LineUnavailableException | NullPointerException | IOException ex) {
            playPrebuiltSound();
            JOptionPane.showMessageDialog(Main.mainFrame, "An error occurred, and the sound could not be played. The file might be corrupted."
                    + "\nThe default sound was played instead.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static double map(double bMin, double bMax, double eMin, double eMax, double val)
    {
        return ((val-bMin)/(bMax-bMin)) * (eMax-eMin) + eMin;
    }
    
    private static void playPrebuiltSound()
    {
        InputStream is = Main.mainFrame.getClass().getResourceAsStream("/resources/HitSFX1.wav");
        InputStream bufferedIn = new BufferedInputStream(is);
        
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            System.out.println(ex);
        }
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(bufferedIn);
        } catch (UnsupportedAudioFileException | IOException ex) {
            System.out.println(ex);
        }
        try {
            clip.open(ais);
            clip.loop(0);
            clip.start();
        } catch (LineUnavailableException | IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void SortExplicitModifiers()
    {
        String[] priorityMods = new String[] {
            "+# to maximum Life",
            "#% increased maximum Energy Shield",
            "+# to maximum Energy Shield",
            "+#% total Elemental Resistance",
            "+#% total Resistance",
            "Energy Shield: #",
            "# Empty Suffix Modifiers",
            "# Empty Prefix Modifiers",
            "#% increased Movement Speed",
            "+# to Dexterity",
            "+# to Intelligence",
            "+# to Strength",
            "+#% to Fire Resistance",
            "+#% to Cold Resistance",
            "+#% to Lightning Resistance",
            "+#% to Chaos Resistance"
        };
        
        for (int j=priorityMods.length-1; j>=0; j--)
        {
            for (int i=0; i<AllExplicitModifiers.size(); i++)
            {
                if (AllExplicitModifiers.get(i).getStr().equals(priorityMods[j]))
                {
                    pushToFront(i);
                    break;
                }
            }
        }
    }
    
    private static void pushToFront(int index)
    {
        Modifier m = AllExplicitModifiers.get(index);
        AllExplicitModifiers.remove(m);
        AllExplicitModifiers.add(0, m);
    }
}