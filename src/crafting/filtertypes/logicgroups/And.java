/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.filtertypes.logicgroups;

import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import poeitem.PoEItem;
import poeitem.Modifier;

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
            switch (m.assocModifier.getModGenerationTypeID())
            {
                case 0:
                case 1:
                case 2:
                    for (Modifier em : item.explicitModifiers)
                    {
                        if (m.hit(em))
                        {
                            numHit++;
                        }
                    }
                    break;
                case -1:
                    int total = -1;
                    for (Modifier psm : m.assocModifier.pseudoSupportedModifiers)
                    {
                        total += item.getValue(psm.getStr());
                    }
                    if (total != -1) total++;
                    if (m.ID.valid(total, m.assocModifier))
                    {
                        numHit++;
                    }
                    break;
                case -2:
                    int[] numPrefixSuffix = item.numPrefixSuffix();
                    int max = item.rarity.equals("Rare") ? 3 : 1;
                    if (m.assocModifier.getStr().equals("# Empty Prefix Modifiers"))
                    {
                        if (m.ID.valid((double) max - numPrefixSuffix[0]))
                        {
                            numHit++;
                        }
                    }
                    else if (m.assocModifier.getStr().equals("# Empty Suffix Modifiers"))
                    {
                        if (m.ID.valid((double) max - numPrefixSuffix[1]))
                        {
                            numHit++;
                        }
                    }
                    break;
                case -3:
                    for (Modifier em : item.baseModifiers)
                    {
                        if (m.hit(em))
                        {
                            numHit++;
                        }
                    }
                    break;
                default:
                    System.exit(0);
            }
            
            if (numHit >= goal) return true;
        }
        return false;
    }
}
