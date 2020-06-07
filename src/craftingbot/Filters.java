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
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Filters implements Serializable {
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
    
    public void rename(String oldname, String newname)
    {
        for (int i=0; i<filters.size(); i++)
        {
            if (filters.get(i).name.equals(oldname))
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
    
    public Filters(String name) throws IOException
    {
        Filter f = new Filter();
        singleton.name = name;
        singleton.filters.clear();
        singleton.filters.add(f);
        Filters.saveFilters();
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
            if (mods == null) return false;

//            mods = parseMods(mods);
        }
        else
        {
            mods = 
                "Rarity: Rare\n" +
                "Vortex Impaler\n" +
                "Primeval Rapier\n" +
                "--------\n" +
                "One Handed Sword\n" +
                "Physical Damage: 18-73\n" +
                "Elemental Damage: 10-15 (augmented), 8-153 (augmented)\n" +
                "Critical Strike Chance: 6.50%\n" +
                "Attacks per Second: 1.30\n" +
                "Weapon Range: 14\n" +
                "--------\n" +
                "Requirements:\n" +
                "Level: 50\n" +
                "Dex: 158\n" +
                "--------\n" +
                "Sockets: G-G B \n" +
                "--------\n" +
                "Item Level: 69\n" +
                "--------\n" +
                "+25% to Global Critical Strike Multiplier (implicit)\n" +
                "--------\n" +
                "+1 to Level of Socketed Gems\n" +
                "+21 to Dexterity\n" +
                "Adds 10 to 15 Fire Damage\n" +
                "Adds 8 to 153 Lightning Damage\n" +
                "+14 Life gained on Kill"
            ;
        }
//        double startLoad = System.nanoTime();
        Item item = Item.createItem(mods);
//        double endLoad = System.nanoTime();
//        System.out.println("Load Time: " + (endLoad - startLoad) / 1000000 + " ms");
//        item.print();
        savedModsRaw = mods;
        
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
    
    public static String parseMods(String mods)
    {
        String[] arr = mods.split("\\R");
        ArrayList<String> modLines = new ArrayList<String>();
        for (String s : arr) modLines.add(s);
        
        for (int i=0; i<modLines.size(); i++)
        {
            String str = modLines.get(i);
            
            Pattern p1a = Pattern.compile("^(\\D+)(\\d+(?:\\.\\d+)?)(\\D+)$"); // 2
            Pattern p1b = Pattern.compile("^(\\d+(?:\\.\\d+)?)(\\D+)$"); // 1
            Pattern p1c = Pattern.compile("^(\\D+)(\\d+(?:\\.\\d+)?)$"); // 2
            Pattern p2  = Pattern.compile("^(\\D+)(\\d+(?:\\.\\d+)?)(\\D+)(\\d+(?:\\.\\d+)?)(\\D+)$"); // 2, 4
            Matcher m1a = p1a.matcher(str);
            Matcher m1b = p1b.matcher(str);
            Matcher m1c = p1c.matcher(str);
            Matcher m2  = p2.matcher(str);
            
            if      (m1a.find()) str = swapHash(str, m1a.group(2));
            else if (m1b.find()) str = swapHash(str, m1b.group(1));
            else if (m1c.find()) str = swapHash(str, m1c.group(2));
            else if (m2.find())  str = swapHash(str, m2.group(2), m2.group(4));
            
            modLines.set(i,str);
            
            Pattern pAllWord = Pattern.compile("^([^0-9#%]*)$");
            Matcher mAllWord = pAllWord.matcher(str);
            
            modLines.set(i, modLines.get(i).replace("# added passive skill is a jewel socket", "# added passive skills are jewel sockets"));
            
            if (
                    mAllWord.find() 
                    || modLines.get(i).contains("(crafted)")
                    || modLines.get(i).contains("Physical Damage: ")
                    || modLines.get(i).contains("Elemental Damage: ")
                    || modLines.get(i).contains("Critical Strike Chance: ")
                    || modLines.get(i).contains("Attacks per Second: ")
                    || modLines.get(i).contains("Level: ")
                    || modLines.get(i).contains("Item Level: ")
                    || modLines.get(i).contains("Int: ")
                    || modLines.get(i).contains("Dex: ")
                    || modLines.get(i).contains("Str: ")
                    || modLines.get(i).contains("Corrupted")
                    || modLines.get(i).contains("Str: ")
                    || modLines.get(i).contains("Weapon Range: ")
                    || modLines.get(i).contains("(implicit)")
                )
            {
                modLines.remove(i);
                i--;
            }
        }
        String joined = String.join(String.valueOf(((char)10)), modLines);
        return joined;
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
        
        for (String s : keys)
        {
            mod += "*" + s;
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
    
    public static Filters loadFilters(String path) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        singleton.filters.clear();
        
        FileInputStream fi = new FileInputStream(new File(path));
        ObjectInputStream oi = new ObjectInputStream(fi);
        Filters input = null;
        try {
            input = (Filters) oi.readObject();
        } catch (InvalidClassException e) {
            input = null;
        }
                
        fi.close();
        oi.close();
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
}