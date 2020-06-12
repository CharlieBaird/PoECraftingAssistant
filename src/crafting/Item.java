package crafting;

import crafting.filtertypes.FilterBase;
import poeitem.Modifier;
import poeitem.PoEItem;

public class Item extends PoEItem {
    
    public static Item createItem(String raw)
    {
        Modifier.genPseudo();
        
        if (raw == null || (!raw.contains("Rarity: Normal") && !raw.contains("Rarity: Magic") && !raw.contains("Rarity: Rare"))) return null;
        else return new Item(raw);
    }
    
    private Item(String input)
    {
        super(input);
    }
    
    public boolean hitFilters(Filters filters)
    {
        for (Filter f : filters.filters)
        {
            if (this.hitFilter(f))
                return true;
        }
        
        return false;
    }
    
    private boolean hitFilter(Filter f)
    {
        int goal = f.filters.size();
        int numhit = 0;
            
        for (FilterBase fb : f.filters)
        {
            if (fb.hit(this)) numhit++;
        }
        
        if (numhit >= goal) return true;
        return false;
    }
}
