package craftingbot.filtertypes.logicgroups;

import craftingbot.Modifier;
import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.Mod;
import craftingbot.item.Item;

public class Count extends FilterBase {
    public int needed;
    
    public Count(int needed, Mod... mods)
    {
        super(mods);
        this.needed = needed;
    }
    
    public boolean hit(String input)
    {
        int count = 0;
        for (Mod m : mods)
        {
            if (count >= needed) return true;
            if (m.hit(input)) count++;
        }
        
        return count >= needed;
    }
    
    public boolean hit(Item item)
    {
        int numHit = 0;
        for (Mod m : mods)
        {
            for (Modifier em : item.explicitModifiers)
            {
                if (m.hit(em))
                {
                    numHit++;
                    break;
                }
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
