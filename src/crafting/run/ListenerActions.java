package crafting.run;

import crafting.UI.Frame;
import crafting.UI.hotkeys.Ctrl;
import crafting.UI.hotkeys.HotkeyConfig;
import crafting.filters.Filter;
import crafting.persistence.Settings;
import crafting.utility.Utility;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JFrame;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.event.GlobalMouseEvent;

public class ListenerActions {
    
    private static JFrame popup;
    private static boolean ignore = false;
    
    private static boolean onSwingWindow()
    {
        if (popup != null)
        {
            if (Frame.mainFrame.isActive() || Frame.mainFrame.isFocused() || popup.isVisible())
            {
                return true;
            }
        }
        else if (Frame.mainFrame.isActive() || Frame.mainFrame.isFocused())
        {
            return true;
        }
        
        return false;
    }
    
    static void Hotkey_KeyPressed(GlobalKeyEvent event)
    {
        Ctrl ctrl = null;
        boolean ctrlPressed = event.isControlPressed();
        boolean shiftPressed = event.isShiftPressed();
        if (ctrlPressed && shiftPressed) ctrl = Ctrl.CTRL_SHIFT;
        else if (ctrlPressed) ctrl = Ctrl.CTRL;
        else if (shiftPressed) ctrl = Ctrl.SHIFT;

        HotkeyConfig.instance.checkHotkeys(ctrl, event);
    }

    static void Hotkey_KeyReleased(GlobalKeyEvent event) {
    }
    
    static void Mouse_MouseReleased(GlobalMouseEvent event)
    {
        if (Run.run && event.getButton() == 1)
        {
            if (onSwingWindow() || ignore) return;

            Utility.delay(Settings.singleton.delay + 35);
            double start = System.nanoTime();
            boolean b = Filter.checkIfHitOne(Run.DEBUG);
            System.out.println("Elapsed: " + (System.nanoTime() - start)/1000000);
            if(b)
            {
                System.out.println("Hit");
                if (Settings.singleton.showPopup)
                    Run.activityTooltip.modHit();

                Utility.playHitSound();
                if (Settings.singleton.disableOnHit)
                    Run.stop();
            }

        }
    }
    
    static void Mouse_MouseMoved(GlobalMouseEvent event)
    {
        Point p = MouseInfo.getPointerInfo().getLocation();
        Run.activityTooltip.setLocation(p.x-30, p.y-30);
    }
    
}
