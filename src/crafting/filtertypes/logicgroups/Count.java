package crafting.filtertypes.logicgroups;

import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import poeitem.PoEItem;
import poeitem.Modifier;

public class Count extends FilterBase {
    public int neededMin;
    public int neededMax;
    
    public Count(int neededMin, int neededMax, Mod... mods)
    {
        super(mods);
        this.neededMin = neededMin;
        this.neededMax = neededMax;
    }
    
    public boolean hit(PoEItem item)
    {
        int numHit = 0;
        int goal = this.mods.size();
//        for (Mod m : mods)
//        {   
//            switch (m.assocModifier.getModGenerationTypeID())
//            {
//                case 0:
//                case 1:
//                case 2:
//                    for (Modifier em : item.explicitModifiers)
//                    {
//                        if (m.hit(em))
//                        {
//                            numHit++;
//                        }
//                    }
//                    break;
//                case -1:
//                    int total = -1;
//                    for (Modifier psm : m.assocModifier.pseudoSupportedModifiers)
//                    {
//                        total += item.getValue(psm.getStr());
//                    }
//                    if (total != -1) total++;
//                    if (m.ID.valid(total, m.assocModifier))
//                    {
//                        numHit++;
//                    }
//                    break;
//                case -2:
//                    int[] numPrefixSuffix = item.numPrefixSuffix();
//                    int max = item.rarity.equals("Rare") ? 3 : 1;
//                    if (m.assocModifier.getStr().equals("# Empty Prefix Modifiers"))
//                    {
//                        if (m.ID.valid((double) max - numPrefixSuffix[0]))
//                        {
//                            numHit++;
//                        }
//                    }
//                    else if (m.assocModifier.getStr().equals("# Empty Suffix Modifiers"))
//                    {
//                        if (m.ID.valid((double) max - numPrefixSuffix[1]))
//                        {
//                            numHit++;
//                        }
//                    }
//                    break;
//                case -3:
//                    for (Modifier em : item.baseModifiers)
//                    {
//                        if (m.hit(em))
//                        {
//                            numHit++;
//                        }
//                    }
//                    break;
//                default:
//                    System.exit(0);
//            }
//        }
//        if (neededMin == -100000)
//        {
//            if (neededMax == 100000)
//            {
//                return (numHit >= 1);
//            }
//            return numHit >= 1 && numHit <= neededMax;
//        }
//        if (neededMax == 100000)
//        {
//            return numHit >= neededMin;
//        }
        
        return (numHit >= neededMin && numHit <= neededMax);
        
    }
    
    @Override
    public String view()
    {
        String str = "    " + this.getClass().getSimpleName() + " " + neededMin + "-" + neededMax + "\n";
              
        for (Mod m : mods)
        {
            str += "        " + m.view() + "\n";
        }
        
        return str;
    }
    
    @Override
    public void print()
    {
        System.out.println("        " + this.getClass().getSimpleName() + " " + neededMin + "-" + neededMax);
        for (Mod m : mods)
        {
            m.print();
        }
    }
}
