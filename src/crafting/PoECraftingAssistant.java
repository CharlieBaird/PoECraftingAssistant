package crafting;

import static crafting.Main.mainFrame;
import static crafting.Utility.*;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
                        
                        boolean b = Filters.checkIfHitOne(debug);
                        
                        if (!Settings.singleton.invertTool) // act normal
                        {
                            if (b)
                            {
                                Utility.playHitSound();
                                if (Settings.singleton.showPopup) showPopup();
                                if (Settings.singleton.disableOnHit) stop();
                            }
                        }
                        else
                        {
                            if (!b)
                            {
                                Utility.playHitSound();
                            }
                            else
                            {
                                if (Settings.singleton.showPopup) showPopup();
                                if (Settings.singleton.disableOnHit) stop();
                            }
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
    
    private static void showPopup()
    {
        ignore = true;
        
        popup = new JFrame();
        popup.setUndecorated(true);
        popup.setSize(100,100);
        
        JPanel panel = new JPanel();
        panel.setSize(120,120);
        panel.setBackground(new Color(80,80,80));
        popup.add(panel);
        
        Point pos = MouseInfo.getPointerInfo().getLocation();
        popup.setLocation(pos.x - popup.getWidth()/2, pos.y - popup.getHeight()/2);
        popup.setAlwaysOnTop(true);
        popup.setVisible(true);
        
        
        new Thread( new Runnable() {
        @Override
        public void run()  {
            try  { Thread.sleep( 2000 ); }
            catch (InterruptedException ie)  {}
            popup.dispose();
            ignore = false;
        }
    } ).start();
        
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
        run = false;
        if (mouseHook != null)
            mouseHook.shutdownHook();
        mouseHook = null;
        Main.setChaosIcon(Main.mainFrame.getClass().getResource("/resources/images/chaos.png"));
    }
        
    public static void runChaosSpam(Main main)
    {
        if (mouseHook != null)
        {
            PoECraftingAssistant.stop();
            return;
        }
        
        Filters.prepItemLoad();
        if (!Filters.verify())
        {
            JOptionPane.showMessageDialog(Main.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        run = true;
        
        while (!establishMouseHook());
//        while (!establishKeyboardHook());
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
    
    
    public static boolean establishHotkeyHook()
    {
        boolean success = true;
        try {
            if (hotkeyHook != null) hotkeyHook.shutdownHook();
            
            hotkeyHook = new GlobalKeyboardHook();
            
            hotkeyHook.addKeyListener(new GlobalKeyAdapter() {
                @Override 
                public void keyPressed(GlobalKeyEvent event) {
                    if (event.getKeyChar()=='1' && event.isControlPressed()) {
                        runChaosSpam(Main.mainFrame);
                    }
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