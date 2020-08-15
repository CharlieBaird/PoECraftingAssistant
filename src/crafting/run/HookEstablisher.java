package crafting.run;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

public class HookEstablisher {
    
    
    public static void establishHotkeyListener()
    {
        while (!establishNativeHotkeyHook());
    }
    
    private static boolean establishNativeHotkeyHook()
    {
        boolean success = true;
        try {
            if (Run.hotkeyHook != null) Run.hotkeyHook.shutdownHook();
            
            Run.hotkeyHook = new GlobalKeyboardHook();
            
            Run.hotkeyHook.addKeyListener(new GlobalKeyAdapter() {
                @Override 
                public void keyPressed(GlobalKeyEvent event) {
                    ListenerActions.Hotkey_KeyPressed(event);
                }
			
                @Override 
                public void keyReleased(GlobalKeyEvent event) {
                    ListenerActions.Hotkey_KeyReleased(event);
                }
            });
        } catch (RuntimeException | UnsatisfiedLinkError e) {
            System.out.println("Failed");
            success = false;
        }
        
        return success;
    }
    
    public static void establishMouseListener()
    {
        while (!establishMouseNativeHook());
    }
    
    private static boolean establishMouseNativeHook()
    {
        boolean success = true;
        try {
            if (Run.mouseHook != null) Run.mouseHook.shutdownHook();
            
            Run.mouseHook = new GlobalMouseHook();
            
            Run.mouseHook.addMouseListener(new GlobalMouseAdapter()
            {
                @Override 
                public void mouseReleased(GlobalMouseEvent event)
                {
                    ListenerActions.Mouse_MouseReleased(event);
                }
                
                @Override
                public void mouseMoved(GlobalMouseEvent event)
                {
                    ListenerActions.Mouse_MouseMoved(event);
                }
            });
        } catch (RuntimeException | UnsatisfiedLinkError e) {
            System.out.println("Failed");
            success = false;
        }
        
        return success;
    }
    
    public static void shutdownAll() {
        
        if (Run.mouseHook != null)
            Run.mouseHook.shutdownHook();
        Run.mouseHook = null;
        if (Run.hotkeyHook != null)
            Run.hotkeyHook.shutdownHook();
        Run.hotkeyHook = null;
    }
}
