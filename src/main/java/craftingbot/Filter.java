/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import craftingbot.filtertypes.*;
import craftingbot.filtertypes.logicgroups.*;
import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author charl
 */
public class Filter implements Serializable
{
    public ArrayList<FilterBase> filters = new ArrayList<FilterBase>();
    public String name;
    public boolean active = false;
    
    public Filter()
    {
        filters.clear();
    }
    
    public Filter(boolean isNew)
    {
        name = "New Filter";
    }
    
    public Filter(Filter old) // duplicates
    {
        for (FilterBase fb : old.filters)
        {
            for (Mod mod : fb.mods)
            {
                Mod dupe = mod.dupe();
            }
        }
    }
    
    public boolean checkIfHit(String mods)
    {
        int goal = filters.size();
        int numhit = 0;
        
//        System.out.println(mods);
            
        for (FilterBase fb : filters)
        {
            if (fb.hit(mods)) numhit++;
        }
        
        if (numhit >= goal) return true;
        return false;
    }
    
    public void print()
    {
        System.out.println("    " + name);
        for (FilterBase f : filters)
        {
            f.print();
        }
    }
    
    public String view()
    {
        String str = name + "\n";
        for (FilterBase f : filters)
        {
            str += f.view() + "\n";
        }
        return str;
    }
}
