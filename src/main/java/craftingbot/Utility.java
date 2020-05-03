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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import craftingbot.modlist.FileScanner;
import java.io.File;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Set;

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
        
        String cc = null;
        try {
            cc = (String) c.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            CraftingBot.run = false;
        }
        return cc;
    }
    
    public static String getResourcesPath()
    {
        String path = System.getProperty("user.dir");
        path = path.replace("\\target", "");
        return path;
    }
    
    public static void pullModsFromAPI() throws Exception
    {        
        Modifier.genPseudo();
        
        String path = getResourcesPath() + "\\src\\main\\resources\\json";
        File file = new File(path);
        File[] fileNames = file.listFiles();
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
                        
                        // ignore:
                        if (str != null && str.equals("1 Added Passive Skill is a Jewel Socket")) continue;
//                        System.out.println(obj.get("id").getAsString());

                        m = new Modifier(ModGenerationTypeID, CorrectGroup, str);
                    }
                }
            } // done with json
        }
        
        String clustersPath = getResourcesPath() + "\\src\\main\\resources\\clusternotables.txt";
        String notables = FileScanner.readFromFile(clustersPath);

        String[] specNotable = notables.split("[.]");
        specNotable = removeDuplicates(specNotable);

        for (String s : specNotable)
        {                
            Pattern p = Pattern.compile("([PS]{1})([_]+)([a-zA-Z ]*)");
            Matcher m = p.matcher(s);

            if (m.find())
            {
                int ps = m.group(1).equals("P") ? 1 : 2;
                String mod = "1 Added Passive Skill is " + m.group(3);

                Modifier notable = new Modifier(String.valueOf(ps), "ClusterJewelNotable", mod);
            }
        }
    }
    
    private static String[] removeDuplicates(String[] input)
    {
        ArrayList<String> arr = new ArrayList<>();
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
}