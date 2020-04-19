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
    public Id[] ids; // Form of: min 25, max 35
    
//    public String converged;
    
    public Mod(String name, int... id)
    {
        this.name = name.toLowerCase();
        this.ids = new Id[id.length / 2];
               
        for (int i=0; i<id.length; i+=2)
            ids[i/2] = new Id(id[i/2], id[i/2+1]);
    }
    
    public void toLowerCase()
    {
        name = name.toLowerCase();
    }
    
    public Mod dupe()
    {
        int[] dupeIds = new int[ids.length*2];
        
        for (int i=0; i<ids.length; i++)
        {
            dupeIds[i*2] = ids[i].min;
            dupeIds[i*2+1] = ids[i].max;
        }
        
        return new Mod(this.name, dupeIds);
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
                        if (!ids[i].valid(Integer.valueOf(rolls[i]))) valid = false;
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
        System.out.println("    \"" + name + "\"");
        for (int i=0; i<ids.length; i++)
            System.out.println("        ids: " + ids[i].min + ", " + ids[i].max);
    }
}

class Id implements Serializable
{
    int min = -100000;
    int max = -100000;
    
    public Id(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
    
    public boolean valid(int roll)
    {
        if (min == -100000 || max == -100000) return true;
        return (roll <= max && roll >= min);
    }
}