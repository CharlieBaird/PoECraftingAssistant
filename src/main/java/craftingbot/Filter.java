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
//        boots
//        name = "Count MS/ES/RES";
        filters.clear();
//        boots
//        name = "Count MS/ES/RES";
//        filters.clear();
//        Mod ms = new Mod("% increased movement speed", 25, 35);        
//        Mod totalES = new Mod("energy shield: ", 130, 1000);
//        Mod maxES = new Mod("energy shield: ", 160, 1000);
//        Mod fRes = new Mod("% to fire resistance", 30, 48);
//        Mod lRes = new Mod("% to lightning resistance", 30, 48);
//        Mod cRes = new Mod("% to cold resistance", 30, 48);
//        Mod maxMS = new Mod("% increased movement speed", 35, 35);
//        filters.add(new Count(6, ms, ms, ms, totalES, totalES, totalES, fRes, fRes, lRes, lRes, cRes, cRes, maxMS, maxMS, maxMS, maxMS, maxMS, maxMS, maxES, maxES, maxES, maxES, maxES, maxES));
        
        
//        Mod totalES = new Mod("energy shield: ", 130, 1000);
//        Mod maxES = new Mod("energy shield: ", 160, 1000);
//        Mod fRes = new Mod("% to fire resistance", 30, 48);
//        Mod lRes = new Mod("% to lightning resistance", 30, 48);
//        Mod cRes = new Mod("% to cold resistance", 30, 48);
//        Mod maxMS = new Mod("% increased movement speed", 35, 35);
//        filters.add(new Count(6, ms, ms, ms, totalES, totalES, totalES, fRes, fRes, lRes, lRes, cRes, cRes, maxMS, maxMS, maxMS, maxMS, maxMS, maxMS, maxES, maxES, maxES, maxES, maxES, maxES));
//        name = "TestAltSpam";
//        filters.add(new And(ms));
        
        /* EXPLODE MOD ON CHEST
        Mod explode = new Mod("enemies you kill explode, dealing 3% of their life as physical damage");
        filters.add(new Count(1, explode));
        */
        
//        FiltersParent.add(this);
        
//        print();

        name = "TestNewFilters";
        Mod ms = new Mod("#% increased movement speed", 25, 35);
        Mod totalES = new Mod("energy shield: #", 10,500);
        filters.add(new And(ms,totalES));
        filters.add(new Count(1, ms));
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
