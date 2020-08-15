package crafting.UI.hotkeys;

import crafting.UI.Main;
import java.io.Serializable;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Hotkey implements Serializable
{ 
    public Ctrl ctrl;
    public Key key;
    public Task task;
    private boolean onlyWhenFrameActive;
    
    
    public Hotkey(Ctrl ctrl, Key key, Task task, boolean onlyWhenFrameActive)
    {
        this.ctrl = ctrl;
        this.key = key;
        this.task = task;
        this.onlyWhenFrameActive = onlyWhenFrameActive;
    }
    
    // Return null if hotkey wasn't hit, return task if it was
    public Task hit(Ctrl ctrl, GlobalKeyEvent event)
    {
        if (this.ctrl == ctrl && event.getVirtualKeyCode() == key.keycode)
        {            
            if (onlyWhenFrameActive && !Main.isFocus())
            {
                 return null;
            }
            
            return this.task;
        }
        
        return null;
    }
}
