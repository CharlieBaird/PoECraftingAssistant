package crafting.filtertypes.logicgroups;

import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import poeitem.PoEItem;
import poeitem.Modifier;
import poeitem.ModifierTier;

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
        
        if (neededMin == -100000)
        {
            if (neededMax == 100000)
            {
                return (numHit >= 1);
            }
            return numHit >= 1 && numHit <= neededMax;
        }
        if (neededMax == 100000)
        {
            return numHit >= neededMin;
        }
        
        return (numHit >= neededMin && numHit <= neededMax);
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
