/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

import java.io.Serializable;

/**
 *
 * @author charl
 */
public class Id implements Serializable
{
    public int min = -100000;
    public int max = 100000;
    
    public Id(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
    
    public Id()
    {
        
    }
    
    public boolean valid(int roll)
    {
        if (min == -100000 || max == 100000) return true;
        return (roll <= max && roll >= min);
    }
    
    public int[] toArr()
    {
        return new int[] {min, max};
    }
}
