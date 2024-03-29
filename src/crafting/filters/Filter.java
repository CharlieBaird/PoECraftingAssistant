package crafting.filters;

import crafting.UI.Frame;
import crafting.utility.Utility;
import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import crafting.run.Run;
import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import poeitem.Base;
import poeitem.BaseItem;
import poeitem.Modifier;
import poeitem.PoEItem;

public class Filter implements Serializable {
    
    public static String testMods = null;
    public boolean isInitial = false;
    public String name = "";

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Subfilter> filters = new ArrayList<>();
    
    public static Filter singleton = new Filter(true);
    
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
    
    public Filter(boolean isInitial)
    {
        this.isInitial = isInitial;
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
        
        Frame.mainFrame.itemConfigPanel.itemType.baseComboBox.reset();
        
        Frame.mainFrame.itemConfigPanel.shaper.reset();
        Frame.mainFrame.itemConfigPanel.elder.reset();
        Frame.mainFrame.itemConfigPanel.hunter.reset();
        Frame.mainFrame.itemConfigPanel.warlord.reset();
        Frame.mainFrame.itemConfigPanel.redeemer.reset();
        Frame.mainFrame.itemConfigPanel.crusader.reset();
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
                JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to access clipboard", "Error", JOptionPane.ERROR_MESSAGE);
                Run.stop();
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
            JOptionPane.showMessageDialog(Frame.mainFrame, "The tool was not able to parse the item. Broken mods:\n" + item.brokenModifiers + ".\nPlease create an issue report at https://github.com/CharlieBaird/PoECraftingAssistant/issues/new. Thanks!", "Error", JOptionPane.ERROR_MESSAGE);
            Run.stop();
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
    
    public static boolean verify()
    {
        
        if (singleton.isInitial || singleton.filters.isEmpty() || singleton.SelectedBase == null) return false;
        
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