/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.filtertypes.logicgroups;

import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import poeitem.PoEItem;
import poeitem.ModifierTier;

public class And extends FilterBase {
    public And(Mod... mods)
    {
        super(mods);
    }
    
    public boolean hit(PoEItem item)
    {
        
        int numHit = 0;
        int goal = this.mods.size();
        for (Mod m : mods)
        {   
            for (ModifierTier em : item.explicitModifierTiers)
            {
                if (m.hit(em))
                {
                    numHit++;
                }
            }
        }
            
        return (numHit >= goal);
    }
}
