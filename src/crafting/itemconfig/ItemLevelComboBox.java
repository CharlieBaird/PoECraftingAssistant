package crafting.itemconfig;

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
}