/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting;

import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Filters implements Serializable {
    
    public static String testMods = null;
    
    private String name = "";

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Filter> filters = new ArrayList<>();
    
    public static Filters singleton = new Filters(false);
    
    public static String getName()
    {
        return singleton.name;
    }
    
    public Filters(boolean x)
    {
        filters.clear();
    }
    
    public void remove(String name)
    {
        for (int i=0; i<filters.size(); i++)
        {
            if (filters.get(i).name.equals(name))
            {
                filters.remove(i);
                break;
            }
        }
    }
    
    public void rename(Filter filter, String newname)
    {
        for (int i=0; i<filters.size(); i++)
        {
            if (filters.get(i).equals(filter))
            {
                filters.get(i).name = newname;
            }
        }
    }
    
    public Filters(ArrayList<Filter> filters)
    {
        this.filters.clear();
        for (Filter f : filters)
        {
            this.filters.add(f);
        }
    }
    
    public static void reset()
    {
        singleton.name = "";
        singleton.filters.clear();
    }
    
    public static void add(Filter f)
    {
        singleton.filters.add(f);
    }
    
    static String savedModsRaw = "";
    
    public static boolean checkIfHitOne(boolean debug)
    {
        String mods = null;
        if (!debug)
        {
            try {
                mods = Utility.copy();
            } catch (AWTException | UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (mods == null)
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "Failed to access clipboard", "Error", JOptionPane.ERROR_MESSAGE);
                PoECraftingAssistant.stop();
                return false;
            }
        }
        else
        {
            mods = testMods;
        }
        Item item = Item.createItem(mods);
        
        if (item == null)
        {
//            JOptionPane.showMessageDialog(null, "Item could not be copied", "Error", JOptionPane.ERROR_MESSAGE);
//            PoECraftingAssistant.stop();
            return false;
        }
        
        if (!debug)
        {
            if (savedModsRaw.equals(mods)) return false;
            
            savedModsRaw = mods;
        }
        
//        if (debug) item.print();

//        item.print();
        
        return item.hitFilters(singleton);
    }
    
    public static void prepItemLoad()
    {
        Item loadCode = Item.createItem
        (
            "Rarity: Rare\n" +
            "Woe Sanctuary\n" +
            "Assassin's Garb\n" +
            "--------\n" +
            "Quality: +20% (augmented)\n" +
            "Evasion Rating: 884 (augmented)\n" +
            "--------\n" +
            "Requirements:\n" +
            "Level: 72\n" +
            "Str: 70\n" +
            "Dex: 183\n" +
            "Int: 155\n" +
            "--------\n" +
            "Sockets: R-B-B-G-G-G \n" +
            "--------\n" +
            "Item Level: 85\n" +
            "--------\n" +
            "3% increased Movement Speed (implicit)\n" +
            "--------\n" +
            "+79 to maximum Life\n" +
            "26% increased Stun and Block Recovery\n" +
            "Attacks have +1.41% to Critical Strike Chance\n" +
            "You have Consecrated Ground around you while stationary\n" +
            "Enemies you Kill Explode, dealing 3% of their Life as Physical Damage\n" +
            "+35% to Fire Resistance (crafted)\n" +
            "--------\n" +
            "Elder Item\n" +
            "Crusader Item"
        );
        
        Item loadCode2 = Item.createItem
        (
            "Rarity: Rare\n" +
            "Beast Spark\n" +
            "Titan Greaves\n" +
            "--------\n" +
            "Quality: +20% (augmented)\n" +
            "Armour: 324 (augmented)\n" +
            "--------\n" +
            "Requirements:\n" +
            "Level: 68\n" +
            "Str: 120\n" +
            "--------\n" +
            "Sockets: R-R-R-R \n" +
            "--------\n" +
            "Item Level: 86\n" +
            "--------\n" +
            "+29 to Armour\n" +
            "Regenerate 13.2 Life per second\n" +
            "+43% to Fire Resistance\n" +
            "26% increased Stun and Block Recovery\n" +
            "--------\n" +
            "Hunter Item"
        );
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
    
    public static Filters loadFilters(String path)
    {
        singleton.filters.clear();
        
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        Filters input = null;
        try {
            input = (Filters) oi.readObject();
        } catch (InvalidClassException | ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }
    
    public static void saveFilters()
    {
        if (singleton.name.equals("") || singleton.name == null)
            return;
        
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File(Utility.getResourcesPath() + "/src/resources/filters" + "/" + singleton.name + ".cbfilter"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(singleton);
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Filters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteFilters(String name)
    {
        File f = new File(Utility.getResourcesPath() + "/src/resources/filters" + "/" + name + ".cbfilter");
        f.delete();
    }
    
    public static boolean verify()
    {
        for (Filter f : singleton.filters)
        {
            for (FilterBase fb : f.filters)
            {
                for (Mod m : fb.mods)
                {
                    if (m.assocModifier == null)
                        return false;
                }
            }
        }
        
        return true;
    }
}