/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author charl
 */
public class Filters implements Serializable {
    private String name = "";
    private ArrayList<Filter> filters = new ArrayList<Filter>();
    
    private static Filters singleton = new Filters(false);
    
    public static String getName()
    {
        return singleton.name;
    }
    
    public Filters(boolean x)
    {
        filters.clear();
    }
    
    public Filters(ArrayList<Filter> filters)
    {
        this.filters.clear();
        for (Filter f : filters)
        {
            this.filters.add(new Filter(f));
        }
    }
    
    public static void reset()
    {
        singleton.name = "";
        singleton.filters.clear();
    }
    
    public Filters(String name) throws IOException
    {
        Filter f = new Filter();
        singleton.name = name;
        singleton.filters.clear();
        singleton.filters.add(f);
        
        saveFilters(System.getProperty("user.dir") + "/src/main/resources/filters" + "/" + name + ".txt");
    }
    
    public static void add(Filter f)
    {
        singleton.filters.add(f);
    }
    
    public static boolean checkIfHitOne() throws AWTException, UnsupportedFlavorException, IOException
    {
        String mods = Utility.copy();
        Utility.delay(40);

        mods = mods.toLowerCase();
        String modsRaw = parseMods(mods);
        System.out.println(modsRaw);
        
//        for (Filter f : singleton.filters)
//        {
//            if (f.checkIfHit(mods)) return true;
//        }
//        
//        return false;

        return true;
    }
    
    private static String parseMods(String mods)
    {
        String[] modLines = mods.split("\\R");
        
        for (int i=0; i<modLines.length; i++)
        {
            String str = modLines[i];
            
            Pattern p1a = Pattern.compile("^(\\D+)([0-9]+)(\\D+)$"); // 2
            Pattern p1b = Pattern.compile("^([0-9]+)(\\D+)$"); // 1
            Pattern p1c = Pattern.compile("^(\\D+)([0-9]+)$"); // 2
            Pattern p2  = Pattern.compile("^(\\D+)([0-9]+)(\\D+)([0-9+])(\\D+)$"); // 2, 4
            Matcher m1a = p1a.matcher(str);
            Matcher m1b = p1b.matcher(str);
            Matcher m1c = p1c.matcher(str);
            Matcher m2  = p2.matcher(str);
            
            if      (m1a.find()) str = swapHash(str, m1a.group(2));
            else if (m1b.find()) str = swapHash(str, m1b.group(1));
            else if (m1c.find()) str = swapHash(str, m1c.group(2));
            else if (m2.find())  str = swapHash(str, m2.group(2), m2.group(4));
            
            modLines[i] = str;
        }
        
        return String.join(String.valueOf(((char)10)), modLines);
    }
    
    private static String swapHash(String mod, String... keys)
    {
        for (int i=0; i<keys.length; i++)
        {
            int len = keys[i].length();
            int index = mod.indexOf(keys[i]);
            
            mod = mod.substring(0, index) + "#" + mod.substring(index+len, mod.length());
            
            // Check for other weird things
            
            index = mod.indexOf(" (augmented");
            if (index != -1)
            {
                mod = mod.substring(0, index);
            }
        }
        
        return mod;
    }
    
    public static void print()
    {
        System.out.println(singleton.name + ":");
        for (Filter f : singleton.filters)
        {
            f.print();
        }
        System.out.println("----");
    }
    
    public static String view()
    {
        String str = "";
        for (Filter f : singleton.filters)
        {
            str += f.view() + "\n";
        }
        return str;
    }
    
    public static void loadFilters(String path) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        singleton.filters.clear();
        
        FileInputStream fi = new FileInputStream(new File(path));
        ObjectInputStream oi = new ObjectInputStream(fi);
        
        singleton = (Filters) oi.readObject();
                
        fi.close();
        oi.close();
    }
    
    public static void saveFilters(String path) throws FileNotFoundException, IOException
    {
        System.out.println(path);
        FileOutputStream f = new FileOutputStream(new File(path));
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        o.writeObject(singleton);

        o.close();
        f.close();
    }
}

// C:\CB\dev\PoE\CraftingBot\test.txt