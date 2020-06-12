/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
    }
}