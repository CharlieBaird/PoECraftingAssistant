package crafting.itemconfig;

import crafting.filters.Filter;
import crafting.UI.Frame;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import poeitem.bases.BaseItem;

public class ItemType extends JPanel {
        
    public ArrayList<BaseItem> BaseTypes = null;
    
    public ItemTypeComboBox baseComboBox = null;
    
    private void resetHashMap()
    {
        BaseTypes.clear();
        for (BaseItem base : BaseItem.BaseItems)
        {
            BaseTypes.add(base);
        }
    }
        
    public ItemType()
    {
        BaseTypes = new ArrayList<>();
        for (BaseItem b : BaseItem.BaseItems)
        {
            BaseTypes.add(b);
        }
        baseComboBox = new ItemTypeComboBox(this, BaseTypes.toArray());
        
        setMaximumSize(new Dimension(1000, 32));
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        resetHashMap();
        
        add(baseComboBox);
        
        Filter.singleton.SelectedBase = (BaseItem) baseComboBox.getSelectedItem();
        
        Frame.mainFrame.requestFocusInWindow();
    }

    public void updateFromFilter() {
        this.baseComboBox.setSelectedItem(Filter.singleton.SelectedBase.getName());
    }
}

class TitleLabel extends JLabel
{
    public TitleLabel(String text)
    {
        super(text);
        setFont(Frame.mainFrame.getNewFont(12));
        setForeground(new Color(255,255,255));
    }
}
