/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

/**
 *
 * @author charl
 */
public class FilterBase implements IFilter {
    
    public Mod[] mods;
    
    public FilterBase(Mod... newMods)
    {
        mods = new Mod[newMods.length];
        
        for (int i=0; i<newMods.length; i++)
        {
            newMods[i].toLowerCase();
            mods[i] = newMods[i];
        }
    }
    
    public boolean hit(String input)
    {
        return true;
    }
    
    public void print()
    {
        System.out.println(this.getClass().getSimpleName());
        for (Mod m : mods)
        {
            m.print();
        }
    }
}
