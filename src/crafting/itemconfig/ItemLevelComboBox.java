package crafting.itemconfig;

import crafting.filters.Filter;
import crafting.UI.FilterTypePanel;
import crafting.UI.SearchBoxBase;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import crafting.persistence.FilterPersistence;

public class ItemLevelComboBox extends SearchBoxBase {
    
    public ItemLevelComboBox(ItemLevel parent, Object[] types)
    {
        super(parent, types);
        setSelectedIndex(0);
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
       if (event.getStateChange() == ItemEvent.SELECTED)
       {
           try {
               Filter.singleton.SelectedItemLevel = Integer.valueOf(this.getSelectedItem().toString());
           } catch (NumberFormatException e) {
               return;
           }
           Filter.singleton.SelectedItemLevelIndex = getSelectedIndex();
           FilterTypePanel.reshow();
       }
       FilterPersistence.saveFilters();
    }
    
    @Override
    public void reset()
    {
        setModel(defaultmodel);
        setSelectedIndex(0);
        entry = "86";
        FilterPersistence.saveFilters();
    }
}