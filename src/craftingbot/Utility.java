/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.ArrayList;
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
            Logger.getLogger(CraftingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Color captureScreen(int x, int y) throws AWTException
    {
        Robot robot = new Robot();
        return robot.getPixelColor(x, y);
    }
    
    public static void lclick()
    {
        Robot bot = null;  
        try {
            bot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        delay(80);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay(80);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    public static void rclick(int x, int y) throws AWTException
    {
        Robot bot = new Robot();
        bot.mouseMove(x, y);    
        delay(80);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delay(80);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
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
        String path = getResourcesPath() + "/src/resources/HitSFX.wav";
        File clipFile = new File(path);
        
//        System.out.println(clipFile.getAbsolutePath());
        
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
//            clip.start();
        } catch (LineUnavailableException | IOException ex) {
            System.out.println(ex);
        }
        
//        System.out.println("finished");
    }
}