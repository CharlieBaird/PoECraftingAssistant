package crafting.itemconfig;

import crafting.UI.Main;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import poeitem.Influence;

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
        setFont(Main.mainFrame.getNewFont(12));
        setForeground(new Color(255,255,255));
    }
    
    public void reset()
    {
        super.setSelected(false);
    }

}