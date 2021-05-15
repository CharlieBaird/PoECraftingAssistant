/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.filtertypes.logicgroups;

import poeitem.Modifier;
import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import poeitem.ModifierTier;
import poeitem.PoEItem;

public class Not extends FilterBase {
    public Not(Mod... mods)
    {
        super(mods);
    }
    
    public boolean hit(PoEItem item)
    {
        for (Mod m : mods)
        {   
            for (ModifierTier em : item.explicitModifierTiers)
            {
                if (m.hit(em))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
