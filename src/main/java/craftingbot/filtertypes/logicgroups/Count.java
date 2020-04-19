package craftingbot.filtertypes;

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
}
