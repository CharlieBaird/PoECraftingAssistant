package crafting.UI;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeListener extends ComponentAdapter {
    
    Thread thread;
    
    @Override
    public void componentResized(ComponentEvent e)
    {
        if (thread == null)
        {
            thread = new Thread(
                new Runnable() {
                    @Override
                    public void run()  {
                        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
                        {
                            if (fnp.active)
                            {
                                fnp.open();
                            }
                        }
                    }
                }
            );
            thread.start();
        }
        
        else if (!thread.isAlive())
        {
            thread = new Thread(
                new Runnable() {
                    @Override
                    public void run()  {
                        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
                        {
                            if (fnp.active)
                            {
                                fnp.open();
                            }
                        }
                    }
                }
            );
            thread.start();
        }
    }
}
