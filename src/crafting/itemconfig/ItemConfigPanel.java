package crafting.itemconfig;

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
    public ArrayList<InfluenceConfig> influenceConfigs;
    
    public ItemConfigPanel()
    {
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        itemType = new ItemType();
        add(itemType);
        add(Box.createRigidArea(new Dimension(0,10)));
        itemLevel = new ItemLevel();
        add(itemLevel);
        
        influenceConfigs = new ArrayList<>();
        
        for (Influence inf : Influence.values())
        {
            add(Box.createRigidArea(new Dimension(0,7)));
            InfluenceConfig a = new InfluenceConfig(inf);
            add(a);
            influenceConfigs.add(a);
        }
    }
    
}
