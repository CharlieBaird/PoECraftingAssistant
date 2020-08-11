package crafting.modplanner;

import static crafting.itemconfig.ItemType.BaseTypes;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import poeitem.Base;

public class ModViewerPanel extends JScrollPane  {

    
    public ModViewerPanel()
    {
        setLayout(new ScrollPaneLayout());
        
    }
    
    void generate(Object selectedBase) {
        Base base = BaseTypes.get((String) selectedBase);
        System.out.println(base);
    }
    
}
