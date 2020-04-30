/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

import craftingbot.Utility;
import java.io.Serializable;

/**
 *
 * @author charl
 */
public class Mod implements Serializable {
    public String name; // Form of: #% increased movement speed
    public Id ID = new Id(); // Form of: min 25, max 35
    
//    public String converged;
    
    public Mod(String name, int... id)
    {
        this.name = name.toLowerCase();
//        this.ids = new Id[id.length / 2];
               
        for (int i=0; i<id.length; i+=2)
            ID = new Id(id[i/2], id[i/2+1]);
    }
    
    public void toLowerCase()
    {
        name = name.toLowerCase();
    }
    
    public Mod dupe()
    {   
        return new Mod(this.name, ID.toArr());
    }
    
    
    public boolean hit(String input)
    {
        String[] inputLines = input.split("\\R");
        
        for (String s : inputLines)
        {
//            System.out.println(s);
            if (s.contains(name))
            {
                String[] rolls = Utility.getModFormat(s);
                
                if (rolls.length != 3)
                {
                    boolean valid = true;
                    for (int i=0; i<rolls.length; i++)
                    {
                        if (!ID.valid(Integer.valueOf(rolls[i]))) valid = false;
                    }

                    return valid;
                }
                else return true;
            }
        }
        
        return false;
    }
    
    public void print()
    {
        System.out.println("        \"" + name + "\"");
        System.out.println("            ids: " + ID.min + ", " + ID.max);
    }
    
    public String view()
    {
        String str = name + "\n";
        str += ("               min: " + ID.min + ", max: " + ID.max);
        
        return str;
    }
}