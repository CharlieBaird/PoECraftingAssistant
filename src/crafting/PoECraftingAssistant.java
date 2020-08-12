package crafting;

import crafting.UI.console.Console;
import crafting.filters.Filter;
import crafting.UI.Main;
import crafting.UI.hotkeys.Ctrl;
import crafting.UI.hotkeys.HotkeyConfig;
import static crafting.Utility.*;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;
import poeitem.ModifierLoader;


public class PoECraftingAssistant {
    
    public static boolean debug = false;
    
    public static void main(String[] args)
    {
        Console.launch();
        
        
        System.out.println("> Loading UI Window... <");
        Utility.delay(100);
        Main.main();
        System.out.println("> Finished! <");
        
        System.out.println();
        
        System.out.println("> Loading Modifiers... <");
        try {
            ModifierLoader.loadModifiers();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("> Finished! <");
        
        System.out.println();

        System.out.println("> Loading Settings... <");
        Settings.load();
        System.out.println("> Finished! <");
        
        System.out.println();
        
        System.out.println("> Loading Hotkeys... <");
        HotkeyConfig.load();
        System.out.println("> Finished! <");
        
        System.out.println();
                
//        Console.close();
    }
    
    public static boolean run = true;
    
    public static GlobalMouseHook mouseHook = null;
    public static GlobalKeyboardHook hotkeyHook = null;
    
    private static boolean ignore = false;
    
    private static JFrame popup;
    
    public static boolean establishMouseHook()
    {
        boolean success = true;
        try {
            if (mouseHook != null) mouseHook.shutdownHook();
            
            mouseHook = new GlobalMouseHook();
            
            mouseHook.addMouseListener(new GlobalMouseAdapter()
            {
                @Override 
                public void mouseReleased(GlobalMouseEvent event)
                {
                    if (run && event.getButton() == 1)
                    {
                        if (onSwingWindow() || ignore) return;
                        
                        delay(Settings.singleton.delay + 35);
                        double start = System.nanoTime();
                        boolean b = Filter.checkIfHitOne(debug);
                        System.out.println("Elapsed: " + (System.nanoTime() - start)/1000000);
                        if(b)
                        {
                            System.out.println("Hit");
                            if (Settings.singleton.showPopup)
                                activityTooltip.modHit();
                            
                            Utility.playHitSound();
                            if (Settings.singleton.disableOnHit)
                                stop();
                        }
                        
                    }
                }
                
                @Override
                public void mouseMoved(GlobalMouseEvent event)
                {
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    activityTooltip.setLocation(p.x-30, p.y-30);
                }
            });
        } catch (RuntimeException | UnsatisfiedLinkError e) {
            System.out.println("Failed");
            success = false;
        }
        
        return success;
    }
    
    private static boolean onSwingWindow()
    {
        if (popup != null)
        {
            if (Main.mainFrame.isActive() || Main.mainFrame.isFocused() || popup.isVisible())
            {
                return true;
            }
        }
        else if (Main.mainFrame.isActive() || Main.mainFrame.isFocused())
        {
            return true;
        }
        
        return false;
    }
    
    public static void stop()
    {
        if (activityTooltip != null) {
            activityTooltip.setVisible(false);
            activityTooltip.dispose();
        }
        
        run = false;
        if (mouseHook != null)
            mouseHook.shutdownHook();
        mouseHook = null;
        Main.setChaosIcon(Main.mainFrame.getClass().getResource("/resources/images/chaos.png"));
    }
    
    private static ActivityTooltip activityTooltip;
        
    public static void runChaosSpam(Main main)
    {
        
        if (mouseHook != null)
        {
            PoECraftingAssistant.stop();
            return;
        }
        
        if (!Filter.verify())
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
//        Filter.print();
        
        run = true;
        
//        Filter.prepItemLoad();
        
        StringSelection selection = new StringSelection("hi");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        
        activityTooltip = new ActivityTooltip();
        while (!establishMouseHook());
        Main.setChaosIcon(main.getClass().getResource("/resources/images/chaosrun.png"));
        
    }
    
    public static void testFilter(Main main, JButton owner)
    {
        if (!Filter.verify())
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Filter.print();
        
//        Filter.prepItemLoad();
        
        String raw = Utility.getClipboard();
        
        if (raw != null && !raw.equals(""))
        {
            Filter.testMods = raw;
            if (Filter.checkIfHitOne(true))
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "The item hit the filter!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "The item did not hit the filter.", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    public static boolean establishHotkeyHook()
    {
        boolean success = true;
        try {
            if (hotkeyHook != null) hotkeyHook.shutdownHook();
            
            hotkeyHook = new GlobalKeyboardHook();
            
            hotkeyHook.addKeyListener(new GlobalKeyAdapter() {
                @Override 
                public void keyPressed(GlobalKeyEvent event) {
                    Ctrl ctrl = null;
                    boolean ctrlPressed = event.isControlPressed();
                    boolean shiftPressed = event.isShiftPressed();
                    if (ctrlPressed && shiftPressed) ctrl = Ctrl.CTRL_SHIFT;
                    else if (ctrlPressed) ctrl = Ctrl.CTRL;
                    else if (shiftPressed) ctrl = Ctrl.SHIFT;
                    
                    HotkeyConfig.instance.checkHotkeys(ctrl, event);
                }
			
                @Override 
                public void keyReleased(GlobalKeyEvent event) {
                }
            });
        } catch (RuntimeException | UnsatisfiedLinkError e) {
            System.out.println("Failed");
            success = false;
        }
        
        return success;
    }
    
    public static void establishHotkeyShortcut()
    {
        while (!establishHotkeyHook());
    }

    public static void shutdownAll() {
        
        if (mouseHook != null)
            mouseHook.shutdownHook();
        mouseHook = null;
        if (hotkeyHook != null)
            hotkeyHook.shutdownHook();
        hotkeyHook = null;
    }
    
}