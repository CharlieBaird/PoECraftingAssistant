/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import java.awt.AWTException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author charl
 */
public class Filters {
    private static ArrayList<Filter> filters = new ArrayList<Filter>();
    
    public Filters(ArrayList<Filter> filters)
    {
        this.filters.clear();
        for (Filter f : filters)
        {
            this.filters.add(new Filter(f));
        }
    }
    
    public static void add(Filter f)
    {
        filters.add(f);
    }
    
    public static boolean checkIfHitOne() throws AWTException, UnsupportedFlavorException, IOException
    {
        String mods = Utility.copy();
        Utility.delay(40);

        mods = mods.toLowerCase();
        
        for (Filter f : filters)
        {
            if (f.checkIfHit(mods)) return true;
        }
        
        return false;
    }
    
    public void print()
    {
        for (Filter f : filters)
        {
            f.print();
        }
    }
}
