package crafting.itemconfig;

import crafting.filters.Filter;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import poeitem.Influence;

public class ItemConfigPanel extends JPanel {
    
    public ItemType itemType;
    public ItemLevel itemLevel;
    
    public InfluenceConfig shaper;
    public InfluenceConfig elder;
    public InfluenceConfig hunter;
    public InfluenceConfig crusader;
    public InfluenceConfig warlord;
    public InfluenceConfig redeemer;
    
    public ItemConfigPanel()
    {
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        itemType = new ItemType();
        add(itemType);
        add(Box.createRigidArea(new Dimension(0,10)));
        
//        itemLevel = new ItemLevel();
//        add(itemLevel);
        
        add(Box.createRigidArea(new Dimension(0,7)));
        shaper = new InfluenceConfig(Influence.SHAPER);
        add(shaper);
        
        add(Box.createRigidArea(new Dimension(0,7)));
        elder = new InfluenceConfig(Influence.ELDER);
        add(elder);
        
        add(Box.createRigidArea(new Dimension(0,7)));
        hunter = new InfluenceConfig(Influence.HUNTER);
        add(hunter);
        
        add(Box.createRigidArea(new Dimension(0,7)));
        crusader = new InfluenceConfig(Influence.CRUSADER);
        add(crusader);
        
        add(Box.createRigidArea(new Dimension(0,7)));
        warlord = new InfluenceConfig(Influence.WARLORD);
        add(warlord);
        
        add(Box.createRigidArea(new Dimension(0,7)));
        redeemer = new InfluenceConfig(Influence.REDEEMER);
        add(redeemer);
    }

    public void updateFromFilter() {
        itemType.baseComboBox.setSelectedItem(ItemType.getKey(Filter.singleton.SelectedBase));
        shaper.influenceCheckBox.setSelected(Filter.singleton.shaper);
        elder.influenceCheckBox.setSelected(Filter.singleton.elder);
        hunter.influenceCheckBox.setSelected(Filter.singleton.hunter);
        warlord.influenceCheckBox.setSelected(Filter.singleton.warlord);
        redeemer.influenceCheckBox.setSelected(Filter.singleton.redeemer);
        crusader.influenceCheckBox.setSelected(Filter.singleton.crusader);
    }
    
}
