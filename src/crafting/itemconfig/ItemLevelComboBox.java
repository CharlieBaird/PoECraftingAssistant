package crafting.itemconfig;

import crafting.Filters;
import crafting.UI.SearchBoxBase;
import java.awt.event.ItemEvent;

public class ItemLevelComboBox extends SearchBoxBase {
    
    public ItemLevelComboBox(ItemLevel parent, Object[] types)
    {
        super(parent, types);
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
        
    }
    
    @Override
    public void reset()
    {
        setModel(defaultmodel);
        setSelectedIndex(0);
        entry = "86";
        Filters.saveFilters();
    }
}