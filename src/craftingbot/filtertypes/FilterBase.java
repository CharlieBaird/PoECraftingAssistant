/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

import craftingbot.Item;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author charl
 */
public class FilterBase implements IFilter, Serializable {
    
    public ArrayList<Mod> mods = new ArrayList<Mod>();
    
    public boolean UIVisible = true;
    
    public FilterBase(Mod... newMods)
    {
        mods = new ArrayList<Mod>();
        
        for (int i=0; i<newMods.length; i++)
        {
//            newMods[i].toLowerCase();
            mods.add(newMods[i]);
        }
    }
    
    public boolean hit (Item item)
    {
        return true;
    }
    
    public void print()
    {
        System.out.println("        " + this.getClass().getSimpleName());
        for (Mod m : mods)
        {
            m.print();
        }
    }
    
    public String view()
    {
        String str = "    " + this.getClass().getSimpleName() + "\n";
              
        for (Mod m : mods)
        {
            str += "        " + m.view() + "\n";
        }
        
        return str;
    }
}
