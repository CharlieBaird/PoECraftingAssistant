package crafting.UI.hotkeys;

import crafting.UI.Frame;
import crafting.filters.Filter;
import crafting.persistence.FilterPersistence;
import crafting.run.Run;
import java.io.Serializable;
import java.util.ArrayList;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class HotkeyConfig implements Serializable {
    
    public static HotkeyConfig instance = new HotkeyConfig();
    
    public ArrayList<Hotkey> hotkeys;
    
    private HotkeyConfig()
    {
        hotkeys = new ArrayList<>();
        hotkeys.add(new Hotkey(Ctrl.CTRL, Key.ONE, Task.RUN_FILTER, false));
        hotkeys.add(new Hotkey(Ctrl.CTRL, Key.N, Task.NEW_FILTER, true));
        hotkeys.add(new Hotkey(Ctrl.CTRL, Key.F, Task.FOCUS_WINDOW, false));
        hotkeys.add(new Hotkey(Ctrl.CTRL, Key.S, Task.SAVE_FILTER, true));
        hotkeys.add(new Hotkey(Ctrl.CTRL, Key.O, Task.OPEN_FILTER, true));
    }
    
    public Hotkey getHotkeyByTask(Task task)
    {
        for (Hotkey hotkey : hotkeys)
        {
            if (hotkey.task == task)
                return hotkey;
        }
        
        return null;
    }
    
    public void checkHotkeys(Ctrl ctrl, GlobalKeyEvent event)
    {
        Task task = null;
        
        for (Hotkey hotkey : hotkeys)
        {
            Task t = hotkey.hit(ctrl, event);
            if (t != null)
            {
                task = t;
                break;
            }
        }
        
        if (task == null) return;
        
        switch (task)
        {
            case SAVE_FILTER:
                Frame.mainFrame.saveFilters();
                break;
            case RUN_FILTER:
                Run.runFilter();
                break;
            case FOCUS_WINDOW:
                Frame.mainFrame.setState(Frame.NORMAL);
                Frame.mainFrame.toFront();
                Frame.mainFrame.repaint();
                break;
            case NEW_FILTER:
                FilterPersistence.createNewFilter();
                break;
            case OPEN_FILTER:
                FilterPersistence.openFilter();
                break;
        }
    }
}
