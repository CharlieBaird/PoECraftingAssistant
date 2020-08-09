package crafting;

import crafting.UI.Main;
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
import poeitem.Base;
import poeitem.BaseItem;
import poeitem.Modifier;

public class Filter implements Serializable {
    
    public static String testMods = null;
    
    private String name = "";

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Subfilter> filters = new ArrayList<>();
    
    public static Filter singleton = new Filter(false);
    
    public Base SelectedBase = null;
    public int SelectedBaseIndex = -1;
    public int SelectedItemLevel = 86;
    public int SelectedItemLevelIndex = 0;
    
    public static String getName()
    {
        return singleton.name;
    }
    
    public Filter(boolean x)
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
    
    public void rename(Subfilter filter, String newname)
    {
        for (int i=0; i<filters.size(); i++)
        {
            if (filters.get(i).equals(filter))
            {
                filters.get(i).name = newname;
            }
        }
    }
    
    public Filter(ArrayList<Subfilter> filters)
    {
        this.filters.clear();
        for (Subfilter f : filters)
        {
            this.filters.add(f);
        }
    }
    
    public static void reset()
    {
        singleton.name = "";
        singleton.filters.clear();
//        singleton.SelectedBase = null;
        singleton.SelectedItemLevel = 86;
//        Main.mainFrame.itemConfigPanel.itemType.baseComboBox.reset();
//        Main.mainFrame.itemConfigPanel.itemLevel.levelComboBox.reset();
//        Main.mainFrame.itemConfigPanel.shaper.reset();
//        Main.mainFrame.itemConfigPanel.elder.reset();
//        Main.mainFrame.itemConfigPanel.hunter.reset();
//        Main.mainFrame.itemConfigPanel.warlord.reset();
//        Main.mainFrame.itemConfigPanel.redeemer.reset();
//        Main.mainFrame.itemConfigPanel.crusader.reset();
        
    }
    
    public static void add(Subfilter f)
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
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
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
            return false;
        }
        
        else if (item.brokenModifiers.size() >= 1)
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Oops, the tool was not able to parse the item. Broken mods:\n" + item.brokenModifiers + ".\nPlease create an issue report at https://github.com/CharlieBaird/PoECraftingAssistant/issues/new. Thanks!", "Error", JOptionPane.ERROR_MESSAGE);
            PoECraftingAssistant.stop();
            return false;
        }
        
        item.print();
        
        if (!debug)
        {
            if (savedModsRaw.equals(mods)) return true;
            
            savedModsRaw = mods;
        }
        
//        if (debug) item.print();

//        item.print();
        
        return item.hitFilters(singleton);
    }
    
    public static void prepItemLoad()
    {
        for (int i=0; i<5; i++)
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
            loadCode.hitFilters(singleton);;
        }
    }
    
    public static void print()
    {
        System.out.println(singleton.name + ":");
        for (Subfilter f : singleton.filters)
        {
            f.print();
        }
        System.out.println("----");
    }
    
    public static String view()
    {
        String str = "";
        for (Subfilter f : singleton.filters)
        {
            str += f.view() + "\n";
        }
        return str;
    }
    
    public static Filter loadFilters(String path)
    {
        singleton.filters.clear();
        
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Filter input = null;
        try {
            input = (Filter) oi.readObject();
        } catch (InvalidClassException | ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(singleton);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteFilters(String name)
    {
        File f = new File(Utility.getResourcesPath() + "/src/resources/filters" + "/" + name + ".cbfilter");
        f.delete();
    }
    
    public static boolean verify()
    {
        for (Subfilter f : singleton.filters)
        {
            for (FilterBase fb : f.filters)
            {
                for (Mod m : fb.mods)
                {
                    if (m.assocModifier == null)
                        return false;
                    
                    Modifier m2;
                    if (Filter.singleton.SelectedBase != null)
                    {
                        m2 = BaseItem.getFromBase(Filter.singleton.SelectedBase).getExplicitFromStr(m.name);
                    }
                    else
                    {
                        m2 = Modifier.getExplicitFromStr(m.name);
                    }
                    
                    if (m2 == null)
                        return false;
                }
            }
        }
        
        return true;
    }
}