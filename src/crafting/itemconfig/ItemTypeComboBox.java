package crafting.itemconfig;

import crafting.Filters;
import crafting.UI.FilterTypePanel;
import crafting.UI.SearchBoxBase;
import poeitem.Modifier;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;

public class ItemTypeComboBox extends SearchBoxBase
{
    ComboBoxModel defaultmodel;
    public ItemType parent;
    
    public String entry = "";
    public int caretPos = -1;
    
    public ItemTypeComboBox(ItemType parent, Object[] types)
    {
        super(parent, types);
        setSelectedIndex(0);
        update();
        Filters.saveFilters();
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
       if (event.getStateChange() == ItemEvent.SELECTED)
            update();
       Filters.saveFilters();
    }
    
    private void update()
    {
        Filters.singleton.SelectedBase = ItemType.BaseTypes.get((String) getSelectedItem());
        Filters.singleton.SelectedBaseIndex = getSelectedIndex();
        FilterTypePanel.reshow();
    }
    
    public static String[] toArr(ArrayList<Modifier> typesList)
    {
        for (int i=0; i<typesList.size(); i++)
        {
            Modifier m = typesList.get(i);
            if (
                    m.getModGenerationTypeID() != 1
                    && m.getModGenerationTypeID() != 2
                    && m.getModGenerationTypeID() != -2
                    && m.getModGenerationTypeID() != -1
                    && m.getModGenerationTypeID() != 0
                    && m.getModGenerationTypeID() != -3
                )
            {
                typesList.remove(m);
                i--;
            }
        }
        
        String[] types = new String[typesList.size()];
        
        for (int i=0; i<types.length; i++)
            types[i] = typesList.get(i).getStr();
        
        return types;
    }
}