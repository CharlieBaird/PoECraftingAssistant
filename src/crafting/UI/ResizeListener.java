package crafting.UI;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeListener extends ComponentAdapter {
    
    @Override
    public void componentResized(ComponentEvent e)
    {
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
    }
}
