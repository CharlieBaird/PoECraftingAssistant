/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

//import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import craftingbot.modlist.FileScanner;
//import craftingbot.Modifier;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
//import craftingbot.modlist.ModList;

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
    
    public static void pullModsFromAPI() throws Exception
    {        
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\json";
        File file = new File(path);
        File[] fileNames = file.listFiles();
        int numFiles = fileNames.length;
        JsonParser parser = new JsonParser();
        
        for (int i=0; i<fileNames.length; i++)
        {
            if (fileNames[i] == null)
            {
                i--;
                continue;
            }
                        
            String string = FileScanner.readFromFile(fileNames[i].getCanonicalPath());
            
            JsonObject object = parser.parse(string).getAsJsonObject();
            
            String[] influences = new String[] {"normal", "elder", "shaper", "crusader", "redeemer", "hunter", "warlord"};
            
            for (String influence : influences)
            {
                JsonElement normalElement = object.get(influence);
            
                Modifier m = null;

                if (normalElement.isJsonArray())
                {
                    JsonArray normal = normalElement.getAsJsonArray();
                    for (int j=0; j<normal.size(); j++)
                    {
                        JsonObject obj = normal.get(j).getAsJsonObject();

                        String ModGenerationTypeID = obj.get("ModGenerationTypeID").getAsString();
                        String CorrectGroup = obj.get("CorrectGroup").getAsString();
                        String str = obj.get("str").getAsString();

                        m = new Modifier(ModGenerationTypeID, CorrectGroup, str);
                    }
                }
                else if (normalElement.isJsonObject())
                {
                    JsonObject normal = normalElement.getAsJsonObject();
                    Set<String> keysSet = normal.keySet();
                    for (String s : keysSet)
                    {
                        JsonObject obj = normal.get(s).getAsJsonObject();

                        String ModGenerationTypeID = obj.get("ModGenerationTypeID").getAsString();
                        String CorrectGroup = obj.get("CorrectGroup").getAsString();
                        String str = obj.get("str").getAsString();

                        m = new Modifier(ModGenerationTypeID, CorrectGroup, str);
                    }
                }
            } // done with json
        }
        
        String clustersPath = System.getProperty("user.dir") + "\\src\\main\\resources\\clusternotables.txt";
        String notables = FileScanner.readFromFile(clustersPath);
        System.out.println(notables);

        String[] specNotable = notables.split("[.]+");
        specNotable = removeDuplicates(specNotable);
//
//        String updateStr = "";
        for (String s : specNotable)
        {
                System.out.println("'" + s + "'");
//            updateStr += ("_" + s + System.getProperty("line.separator"));
        }
//
//        System.out.println(updateStr);
//
//        FileScanner.writeToFile(clustersPath, updateStr);
        
//        for (Modifier m : Modifier.all)
//        {
//            m.print();
//        }
//        
//        System.out.println("Done");
    }
    
    private static String[] removeDuplicates(String[] input)
    {
        ArrayList<String> arr = new ArrayList<String>();
        for (String s : input)
            if (!arr.contains(s))
                arr.add(s);
        
        String[] output = new String[arr.size()];
        for (int i=0; i<output.length; i++)
        {
            output[i] = arr.get(i);
        }
        
        return output;
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