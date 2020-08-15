package crafting.filters;

import crafting.PoECraftingAssistant;
import crafting.UI.FilterNamePanel;
import crafting.UI.Main;
import crafting.UI.ModifierPanel;
import crafting.utility.Utility;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import poeitem.Base;
import poeitem.BaseItem;
import poeitem.Modifier;
import poeitem.PoEItem;

public class Filter implements Serializable {
    
    public static String testMods = null;

    public static void createNewFilter()
    {   
        String name = (String)JOptionPane.showInputDialog(
            Main.mainFrame,
            "Enter the New Filter's Name",
            "PoE Crafting Assistant",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            "New Filter");

        if (name != null && !name.equals(""))
        {
            saveFilters();

            reset_newFilter();

            for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
            {
                FilterNamePanel.filterpanels.get(i).remove();
            }

            FilterNamePanel.filterpanels.clear();


            Filter.singleton.setName(name);
            Filter.saveFilters();

            Main.mainFrame.onCreateNewFilter(name);
        }
    }
    
    private String name = "";

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Subfilter> filters = new ArrayList<>();
    
    public static Filter singleton = new Filter(false);
    
    public Base SelectedBase = null;
    public int SelectedItemLevel = 86;
    public int SelectedItemLevelIndex = 0;
    public boolean shaper = false;
    public boolean elder = false;
    public boolean hunter = false;
    public boolean warlord = false;
    public boolean redeemer = false;
    public boolean crusader = false;
    
    public static String getName()
    {
        return singleton.name;
    }
    
    public Filter(boolean x)
    {
        filters.clear();
    }
    
    public static void openFilter()
    {
        JFileChooser chooser = new JFileChooser(Utility.getResourcesPath() + "/src/resources/filters");
        if (chooser.showOpenDialog(Main.mainFrame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String path = file.toPath().toString();
            
            Filter loaded = null;
            loaded = Filter.loadFilters(path);
            openFilter(loaded);
            return;
        }
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
    }
    
    public static void openFilter(Filter loaded)
    {
        if (loaded == null) // Errored, wrong serial ID
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Filter. Filters from previous PoE Crafting Assistant\nversions cannot be opened.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Filter.saveFilters();
        Filter.reset_openFilter();
        Main.mainFrame.updateLeftTab();

        for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
        {
            FilterNamePanel.filterpanels.get(i).remove();
        }

        FilterNamePanel.filterpanels.clear();

        Filter.singleton = loaded;

        Main.mainFrame.updateLeftTab();
        Main.mainFrame.itemConfigPanel.updateFromFilter();

        if (FilterNamePanel.filterpanels.size() >= 1)
        {
            FilterNamePanel.filterpanels.get(0).open();
        }

        Main.mainFrame.onOpenFilter();

        ModifierPanel.updateTierViews();
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
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
    
    public static void reset_newFilter()
    {
        singleton.name = "";
        singleton.filters.clear();
        singleton.SelectedItemLevel = 86;
    }
    
    public static void reset_openFilter()
    {
        singleton.name = "";
        singleton.filters.clear();
        singleton.SelectedItemLevel = 86;
        
        Main.mainFrame.itemConfigPanel.itemType.baseComboBox.reset();
        
        Main.mainFrame.itemConfigPanel.shaper.reset();
        Main.mainFrame.itemConfigPanel.elder.reset();
        Main.mainFrame.itemConfigPanel.hunter.reset();
        Main.mainFrame.itemConfigPanel.warlord.reset();
        Main.mainFrame.itemConfigPanel.redeemer.reset();
        Main.mainFrame.itemConfigPanel.crusader.reset();
    }
    
    public static void add(Subfilter f)
    {
        singleton.filters.add(f);
    }
    
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
        PoEItem item = PoEItem.createItem(mods);
        
        if (item == null)
        {
            System.out.println("null");
            return false;
        }
        
        else if (item.brokenModifiers.size() >= 1)
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "The tool was not able to parse the item. Broken mods:\n" + item.brokenModifiers + ".\nPlease create an issue report at https://github.com/CharlieBaird/PoECraftingAssistant/issues/new. Thanks!", "Error", JOptionPane.ERROR_MESSAGE);
            PoECraftingAssistant.stop();
            return false;
        }
        
        return hitFilters(item, singleton);
    }
    
    public static boolean hitFilters(PoEItem item, Filter filters)
    {
        for (Subfilter f : filters.filters)
        {
            if (hitFilter(item, f))
                return true;
        }
        
        return false;
    }
    
    private static boolean hitFilter(PoEItem item, Subfilter f)
    {
        int goal = f.filters.size();
        int numhit = 0;
            
        for (FilterBase fb : f.filters)
        {
            if (fb.hit(item)) numhit++;
        }
        
        if (numhit >= goal) return true;
        return false;
    }
    
    public static void prepItemLoad()
    {
        for (int i=0; i<5; i++)
            {
            PoEItem loadCode = PoEItem.createItem
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
            hitFilters(loadCode, singleton);
        }
    }
    
    public static void print()
    {
        singleton.printFilter();
    }
    
    public void printFilter()
    {
        System.out.println(name + ":");
        for (Subfilter f : filters)
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
        
        if (singleton.filters.isEmpty() || singleton.SelectedBase == null) return false;
        
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