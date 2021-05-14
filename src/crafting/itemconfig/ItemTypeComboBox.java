package crafting.itemconfig;

import crafting.filters.Filter;
import crafting.UI.FilterTypePanel;
import crafting.UI.SearchBoxBase;
import poeitem.Modifier;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import crafting.persistence.FilterPersistence;
import poeitem.bases.BaseItem;

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
        FilterPersistence.saveFilters();
    }
    
    @Override
    public void reset()
    {
        super.reset();
        update();
        FilterPersistence.saveFilters();
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
       if (event.getStateChange() == ItemEvent.SELECTED && ItemType.BaseTypes.indexOf(event.getItem()) != -1)
       {
           update();
       }
       FilterPersistence.saveFilters();
    }
    
    private void update()
    {
        Filter.singleton.SelectedBase = (BaseItem) getSelectedItem();
        FilterTypePanel.reshow();
    }
    
    public static String[] toArr(ArrayList<Modifier> typesList)
    {
//        for (int i=0; i<typesList.size(); i++)
//        {
//            Modifier m = typesList.get(i);
//            if (
//                    m.getModGenerationTypeID() != 1
//                    && m.getModGenerationTypeID() != 2
//                    && m.getModGenerationTypeID() != -2
//                    && m.getModGenerationTypeID() != -1
//                    && m.getModGenerationTypeID() != 0
//                    && m.getModGenerationTypeID() != -3
//                )
//            {
//                typesList.remove(m);
//                i--;
//            }
//        }
        
        String[] types = new String[typesList.size()];
        
        for (int i=0; i<types.length; i++)
            types[i] = typesList.get(i).getKey();
        
        return types;
    }
}