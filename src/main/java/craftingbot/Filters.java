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
import java.util.ArrayList;

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
    
    public Filters(String name) throws IOException
    {
        Filter f = new Filter();
        singleton.name = name;
        singleton.filters.clear();
        singleton.filters.add(f);
        
        saveFilters("C:\\CB\\dev\\PoE\\CraftingBot\\PurposefulHarbinger.txt");
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
        
        for (Filter f : singleton.filters)
        {
            if (f.checkIfHit(mods)) return true;
        }
        
        return false;
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
        FileOutputStream f = new FileOutputStream(new File(path));
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        o.writeObject(singleton);

        o.close();
        f.close();
    }
}

// C:\CB\dev\PoE\CraftingBot\test.txt