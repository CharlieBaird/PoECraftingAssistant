package crafting.itemconfig;

import crafting.Filters;
import crafting.UI.FilterTypePanel;
import crafting.UI.Main;
import crafting.UI.SearchBoxBase;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ItemLevelComboBox extends SearchBoxBase {
    
    public ItemLevelComboBox(ItemLevel parent, Object[] types)
    {
        super(parent, types, "Item Level");
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
        
    }
}