package crafting.itemconfig;

import crafting.UI.FilterTypePanel;
import crafting.UI.Frame;
import crafting.filters.Filter;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import poeitem.bases.Influence;

public class InfluenceConfig extends JPanel {
    
    public Influence influence;
    public InfluenceCheckBox influenceCheckBox;
    
    public InfluenceConfig(Influence influence)
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.influence = influence;
        
        setBackground(new Color(30,30,30));
        
        influenceCheckBox = new InfluenceCheckBox(this);
        add (influenceCheckBox);
    }
    
    public void reset()
    {
        influenceCheckBox.reset();
    }

    public boolean isSelected() {
        return influenceCheckBox.isSelected();
    }
}

class InfluenceCheckBox extends JCheckBox
{
    public InfluenceConfig parent;
    
    public InfluenceCheckBox(InfluenceConfig parent)
    {
        this.parent = parent;
        this.setText(parent.influence.friendly + " influence");
        setFont(Frame.mainFrame.getNewFont(12));
        setForeground(new Color(255,255,255));
        
        this.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                boolean selected = isSelected();
                switch (parent.influence)
                {
                    case SHAPER: Filter.singleton.shaper = selected; break;
                    case ELDER: Filter.singleton.elder = selected; break;
                    case HUNTER: Filter.singleton.hunter = selected; break;
                    case WARLORD: Filter.singleton.warlord = selected; break;
                    case REDEEMER: Filter.singleton.redeemer = selected; break;
                    case CRUSADER: Filter.singleton.crusader = selected; break;
                }
                        
                FilterTypePanel.reshow();
            }
        });
    }
    
    public void reset()
    {
        super.setSelected(false);
    }

}