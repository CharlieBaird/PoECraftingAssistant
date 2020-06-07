/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import craftingbot.filtertypes.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Filter implements Serializable
{
    public ArrayList<FilterBase> filters = new ArrayList<>();
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
