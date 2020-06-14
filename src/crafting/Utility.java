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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
        bot.keyPress(KeyEvent.VK_CONTROL);
        delay(10);
        bot.keyPress(KeyEvent.VK_C); 
        delay(5);
        bot.keyRelease(KeyEvent.VK_C); 
        bot.keyRelease(KeyEvent.VK_CONTROL); 
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
        /* Old method of doing it, stores the default one the .jar
        InputStream is = Main.mainFrame.getClass().getResourceAsStream("/resources/HitSFX.wav");
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
        */
        
        File clipFile = new File(Settings.singleton.pathToSound);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            System.out.println(ex);
        }
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(clipFile);
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