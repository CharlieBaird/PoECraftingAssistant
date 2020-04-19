package craftingbot.filtertypes.logicgroups;

import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.Mod;

public class Count extends FilterBase {
    int needed;
    
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
}
