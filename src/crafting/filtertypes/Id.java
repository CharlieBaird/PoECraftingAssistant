/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.filtertypes;

import poeitem.Modifier;
import java.io.Serializable;

public class Id implements Serializable
{
    public int min = -100000;
    public int max = 100000;
    
    public Id(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
    
    public Id(int min)
    {
        this.min = min;
    }
    
    public Id()
    {
        
    }
    
    public boolean valid(double roll)
    {
//        System.out.println(min + " <= " + roll + " <= " +  + max);
        return (roll <= max && roll >= min);
    }
    
    public boolean valid(double roll, Modifier m)
    {
//        if (m.getModGenerationTypeID() == -1 && min == -100000)
//        {
//            return (roll <= max && roll >= 1); 
//        }
//        return (roll <= max && roll >= min);
        // come back to this
        return true;
    }
    
    public boolean valid (double[] rolls)
    {
        if (rolls.length == 1)
        {
            return valid(rolls[0]);
        }
        if (rolls.length == 2)
        {
            return valid( (rolls[0] + rolls[1]) / 2 );
        }
        if (rolls.length == 4)
        {
            return valid((((rolls[0] + rolls[1]) / 2) + ((rolls[2] + rolls[3]) / 2)) / 2 );
        }
        return true;
    }
    
    public int[] toArr()
    {
        return new int[] {min, max};
    }
}
