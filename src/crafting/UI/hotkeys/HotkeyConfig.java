package crafting.UI.hotkeys;

import static crafting.PoECraftingAssistant.runChaosSpam;
import crafting.UI.Main;
import crafting.filters.Filter;
import crafting.persistence.FilterPersistence;
import java.awt.Frame;
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
                Main.mainFrame.saveFilters();
                break;
            case RUN_FILTER:
                runChaosSpam(Main.mainFrame);
                break;
            case FOCUS_WINDOW:
                Main.mainFrame.setState(Frame.NORMAL);
                Main.mainFrame.toFront();
                Main.mainFrame.repaint();
                break;
            case NEW_FILTER:
                Filter.createNewFilter();
                break;
            case OPEN_FILTER:
                FilterPersistence.openFilter();
                break;
        }
    }
}
