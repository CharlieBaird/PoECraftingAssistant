package crafting;

import static crafting.Utility.*;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
        try {
            ModifierLoader.loadModifiers();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Utility.SortExplicitModifiers();
        
        Main.main();
        Settings.load();
        
    }
    
    public static boolean run = true;
    
    public static GlobalMouseHook mouseHook = null;
    public static GlobalKeyboardHook keyHook = null;
    
    private static boolean ignore = false;
    
    public static boolean establishMouseHook()
    {
        boolean success = true;
        try {
            if (mouseHook != null) mouseHook.shutdownHook();
            
            mouseHook = new GlobalMouseHook();
            
            mouseHook.addMouseListener(new GlobalMouseAdapter() {
                @Override 
                public void mouseReleased(GlobalMouseEvent event)  {
                    if (run && event.getButton() == 1) {
                        if (onSwingWindow() || ignore) return;
                        delay(Settings.singleton.delay + 35);
                        boolean b = Filters.checkIfHitOne(debug);
                        if (b) {
                            Utility.playHitSound();
                        }
                    }
                }
            });
        } catch (RuntimeException | UnsatisfiedLinkError e) {
            System.out.println("Failed");
            success = false;
        }
        
        return success;
    }
    
    public static boolean establishKeyboardHook()
    {
        boolean success = true;
        try {
            if (keyHook != null) keyHook.shutdownHook();
            
            keyHook = new GlobalKeyboardHook();
            
            keyHook.addKeyListener(new GlobalKeyAdapter() {
                @Override 
                public void keyPressed(GlobalKeyEvent event) {
                    if (event.getVirtualKeyCode() == 192) {
                        ignore = true;
                    }
                }
			
                @Override 
                public void keyReleased(GlobalKeyEvent event) {
                    if (event.getVirtualKeyCode() == 192) {
                        ignore = false;
                    }
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
        if (!Main.mainFrame.isActive() || !Main.mainFrame.isFocused())
        {
            return false;
        }
        
        return true;
    }
    
    public static void stop()
    {
        run = false;
        if (mouseHook != null)
            mouseHook.shutdownHook();
        mouseHook = null;
        if (keyHook != null)
            keyHook.shutdownHook();
        keyHook = null;
        Main.setChaosIcon(Main.mainFrame.getClass().getResource("/resources/images/chaos.png"));
    }
        
    public static void runChaosSpam(Main main)
    {
        Filters.prepItemLoad();
        if (!Filters.verify())
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        run = true;
        
        while (!establishMouseHook());
        while (!establishKeyboardHook());
        Main.setChaosIcon(main.getClass().getResource("/resources/images/chaosrun.png"));
    }
    
    public static void testFilter(Main main, JButton owner)
    {
        Filters.prepItemLoad();
        if (!Filters.verify())
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String raw = Utility.getClipboard();
        
        if (raw != null && !raw.equals(""))
        {
            Filters.testMods = raw;
            if (Filters.checkIfHitOne(true))
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "The item hit the filter!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(Main.mainFrame, "The item did not hit the filter.", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
        
            
    }
}