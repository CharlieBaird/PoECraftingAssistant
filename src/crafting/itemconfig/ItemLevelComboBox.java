package crafting.itemconfig;

import crafting.Filters;
import crafting.UI.FilterTypePanel;
import crafting.UI.SearchBoxBase;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;

public class ItemLevelComboBox extends SearchBoxBase {
    
    public ItemLevelComboBox(ItemLevel parent, Object[] types)
    {
        super(parent, types);
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
       if (event.getStateChange() == ItemEvent.SELECTED)
       {
           try {
               Filters.singleton.SelectedItemLevel = Integer.valueOf(this.getSelectedItem().toString());
           } catch (NumberFormatException e) {
               return;
           }
           Filters.singleton.SelectedItemLevelIndex = getSelectedIndex();
           FilterTypePanel.reshow();
       }
       Filters.saveFilters();
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