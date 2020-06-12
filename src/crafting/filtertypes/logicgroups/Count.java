package crafting.filtertypes.logicgroups;

import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import crafting.Item;
import poeitem.Modifier;

public class Count extends FilterBase {
    public int needed;
    
    public Count(int needed, Mod... mods)
    {
        super(mods);
        this.needed = needed;
    }
    
    public boolean hit(Item item)
    {
//        item.print();
        
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
                    if (m.assocModifier.getStr().equals("# Empty Prefix Modifiers"))
                    {
                        if (m.ID.valid((double) 3 - numPrefixSuffix[0]))
                        {
                            numHit++;
                        }
                    }
                    else if (m.assocModifier.getStr().equals("# Empty Suffix Modifiers"))
                    {
                        if (m.ID.valid((double) 3 - numPrefixSuffix[1]))
                        {
                            numHit++;
                        }
                    }
                    break;
                default:
                    System.exit(0);
            }
            
            if (numHit >= needed) return true;
        }
        return false;
    }
    
    @Override
    public String view()
    {
        String str = "    " + this.getClass().getSimpleName() + " " + needed + "\n";
              
        for (Mod m : mods)
        {
            str += "        " + m.view() + "\n";
        }
        
        return str;
    }
    
    @Override
    public void print()
    {
        System.out.println("        " + this.getClass().getSimpleName() + " " + needed);
        for (Mod m : mods)
        {
            m.print();
        }
    }
}
