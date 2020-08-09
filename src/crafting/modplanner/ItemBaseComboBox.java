package crafting.modplanner;

import crafting.UI.SearchBoxBase;
import static crafting.itemconfig.ItemType.BaseTypes;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.JPanel;

public class ItemBaseComboBox extends SearchBoxBase {
    
    
    public ItemBaseComboBox(JPanel panel)
    {
        super(panel, BaseTypes.keySet().toArray());
        setSelectedIndex(0);
        
        Dimension size = new Dimension((int)(panel.getWidth()*0.9), 32);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        
        this.setRequestFocusEnabled(false);
    }
    
    @Override
    public void itemUpdate(ItemEvent event)
    {
       if (event.getStateChange() == ItemEvent.SELECTED)
            update();
    }
    
    private void update()
    {
        
    }
    
    
}
