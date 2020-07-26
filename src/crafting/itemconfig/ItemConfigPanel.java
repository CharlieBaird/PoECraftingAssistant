package crafting.itemconfig;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ItemConfigPanel extends JPanel {
    
    public ItemConfigPanel()
    {
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(new ItemType());
        add(Box.createRigidArea(new Dimension(0,10)));
        add(new ItemLevel());
    }
    
}
