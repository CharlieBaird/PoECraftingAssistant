package crafting.UI.hotkeys;

import static crafting.PoECraftingAssistant.runChaosSpam;
import crafting.UI.Main;
import crafting.UI.console.Console;
import crafting.utility.Utility;
import crafting.filters.Filter;
import java.awt.Frame;
import java.io.Serializable;
import java.util.ArrayList;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
                Main.mainFrame.createNewFilter();
                break;
            case OPEN_FILTER:
                Main.mainFrame.openFilter();
                break;
        }
    }
    
    public static void load()
    {
        File hotkeysFile = null;
        FileInputStream fi = null;
        try {
            hotkeysFile = new File(Utility.getResourcesPath() + "/src/resources/hotkeys.cbhotkeys");
            fi = new FileInputStream(hotkeysFile);
        } catch (FileNotFoundException ex) {
            save();
            load();
            return;
        }
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            instance = (HotkeyConfig) oi.readObject();
        } catch (ClassNotFoundException | InvalidClassException ex) {
            Console.loadingFrame.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(Main.mainFrame, "The hotkey configuration file could not be loaded. It has been\nautomatically recreated. You will have to reconfigure the hotkeys. Sorry!", "Error", JOptionPane.ERROR_MESSAGE);
            Console.loadingFrame.setAlwaysOnTop(true);
            hotkeysFile.delete();
            save();
            load();
            return;
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void save()
    {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File(Utility.getResourcesPath() + "/src/resources/hotkeys.cbhotkeys"));
        } catch (FileNotFoundException ex) {
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(instance);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Hotkey implements Serializable
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

enum Key
{
    ZERO(48, "0"),
    ONE(49, "1"),
    TWO(50, "2"),
    THREE(51, "3"),
    FOUR(52, "4"),
    FIVE(53, "5"),
    SIX(54, "6"),
    SEVEN(55, "7"),
    EIGHT(56, "8"),
    NINE(57, "9"),
    A(65),
    B(66),
    C(67),
    D(68),
    E(69),
    F(70), 
    G(71),
    H(72),
    I(73),
    J(74),
    K(75),
    L(76),
    M(77),
    N(78),
    O(79),
    P(80),
    Q(81),
    R(82),
    S(83),
    T(84),
    U(85),
    V(86),
    W(87),
    X(88),
    Y(89),
    Z(90);
    
    public final int keycode;
    public final String pretty;
    
    private Key(int keycode, String pretty)
    {
        this.keycode = keycode;
        this.pretty = pretty;
    }
    
    private Key(int keycode)
    {
        this.keycode = keycode;
        this.pretty = this.name();
    }
    
    @Override
    public String toString()
    {
        return this.pretty;
    }
}

enum Task
{
    SAVE_FILTER("Save filter"),
    RUN_FILTER("Run/stop filter (Global hotkey)"),
    FOCUS_WINDOW("Bring window to focus/front (Global hotkey)"),
    NEW_FILTER("Create new filter"),
    OPEN_FILTER("Open an existing filter");
    
    public final String pretty;
    
    private Task(String pretty)
    {
        this.pretty = pretty;
    }
}
