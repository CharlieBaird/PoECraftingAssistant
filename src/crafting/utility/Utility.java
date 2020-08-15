package crafting.utility;

import crafting.PoECraftingAssistant;
import crafting.persistence.Settings;
import crafting.UI.Main;
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
        return getClipboard();
    }
    
    public static String getClipboard()
    {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        String cc = null;
        try {
            cc = (String) cb.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException | IllegalStateException e) {
            JOptionPane.showMessageDialog(Main.mainFrame, "An error occurred while trying to access the clipboard.", "Failure", JOptionPane.ERROR_MESSAGE);
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
            double volToSet = map(0,100,gainControl.getMinimum(),gainControl.getMaximum(),Settings.singleton.volume);
            gainControl.setValue((float) volToSet);


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
    
    /**
   * Calculates the similarity (a number within 0 and 1) between two strings.
   */
    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
        longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        /* // If you have Apache Commons Text, you can use it to calculate the edit distance:
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                      int newValue = costs[j - 1];
                      if (s1.charAt(i - 1) != s2.charAt(j - 1))
                        newValue = Math.min(Math.min(newValue, lastValue),
                            costs[j]) + 1;
                      costs[j - 1] = lastValue;
                      lastValue = newValue;
                    }
                }
            }
        if (i > 0)
            costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}