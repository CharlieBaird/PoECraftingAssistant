/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes.logicgroups;

import craftingbot.Modifier;
import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.Mod;
import craftingbot.item.Item;

public class And extends FilterBase {
    public And(Mod... mods)
    {
        super(mods);
    }
    
    public boolean hit(String input)
    {
        for (Mod m : mods)
        {
            if (!m.hit(input)) return false;
        }
        return true;
    }
    
    public boolean hit(Item item)
    {
        int numHit = 0;
        int goal = this.mods.size();
        for (Mod m : mods)
        {
            for (Modifier em : item.explicitModifiers)
            {
                switch (em.getModGenerationTypeID())
                {
                    case 0:
                    case 1:
                    case 2: // 0,1,2 are all normal ID's that can be measured this way. 0 = Armour: x; 1 = # to Maximum Life; 2 = +#% to Cold Resistance
                    {
                        if (m.hit(em))
                        {
                            numHit++;
                        }
                        break;
                    }
                    case -1: // Psuedo Total Modifier. Example: Total Res on an item
                    {
                        int total = 0;
                        for (Modifier psm : em.pseudoSupportedModifiers)
                        {
                            total += m.valueOn(psm);
                        }
                        if (m.ID.valid(total))
                        {
                            numHit++;
                        }
                    }
                    case -2: // Pseudo Item Stats Modifier. Example: Total number of empty prefixes on an item
                    {
                        int[] numPrefixSuffix = Mod.numPrefixSuffix(item);
                        if (em.getStr().equals("# Empty Prefix Modifiers"))
                        {
                            if (m.ID.valid((double) 3 - numPrefixSuffix[0]))
                            {
                                numHit++;
                            }
                        }
                        else if (em.getStr().equals("# Empty Suffix Modifiers"))
                        {
                            if (m.ID.valid((double) 3 - numPrefixSuffix[1]))
                            {
                                numHit++;
                            }
                        }
                                
                        break;
                    }
                }
                    
                if (numHit >= goal) return true;
            }
        }
        return false;
    }
}
