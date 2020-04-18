/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modlist.Converter;
import modlist.FileScanner;
import modlist.ModList;

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
    
    public static void lclick() throws AWTException
    {
        Robot bot = new Robot();  
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
        delay(50);
        bot.keyPress(KeyEvent.VK_C); 
        delay(50);
        bot.keyRelease(KeyEvent.VK_C); 
        delay(50);
        bot.keyRelease(KeyEvent.VK_CONTROL); 
        delay(50);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        return (String) c.getData(DataFlavor.stringFlavor);
    }
    
    public static ModList pullModsFromAPI() throws Exception
    {
        String modStr = FileScanner.readFromFile("C:\\CB\\school\\10\\cs\\CraftingBot_0.1\\resources\\modlist.json");
        
        ModList modlist = Converter.fromJsonString(modStr);
        
        return modlist;
    }
    
    public static String[] getModFormat(String str)
    {
        Pattern p;
        Matcher m;
        
        p = Pattern.compile("^([+])([0-9]+)([ to ][a-zA-Z ]+)$");
        m = p.matcher(str);
        if      (m.find())
        {
            String[] r = new String[] {m.group(2)};
            return r;
        }
        p = Pattern.compile("^([0-9]+)([%][ increased ][a-zA-Z ]+)$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(1)};
            return r;
        }
        p = Pattern.compile("^([+])([0-9]+)([%][ to ].+)$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(2)};
            return r;
        }
        p = Pattern.compile("^([a-zA-Z ]+)([:])([ ])([0-9]+)([^%]+)$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(4)};
            return r;
        }
        p = Pattern.compile("^([a-zA-Z ]+)([:])([ ])([+])([0-9]+)(.*)$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(5)};
            return r;
        }
        p = Pattern.compile("^([a-zA-Z ]+)([0-9]+)([ ][t][o][ ])([0-9]+)([a-zA-Z ]+)$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(2), m.group(4)};
            return r;
        }
        p = Pattern.compile("^([0-9]+)([ ])(added passive skill is )([a-zA-Z ]*)$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(1)};
            return r;
        }
        p = Pattern.compile("^.*$");
        m = p.matcher(str);
        if (m.find())
        {
            String[] r = new String[] {m.group(0), "", ""};
            return r;
        }
        return null;
    }
}