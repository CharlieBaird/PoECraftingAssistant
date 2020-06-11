package craftingbot;

import static craftingbot.Utility.*;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;
import poeitem.ModifierLoader;


public class CraftingBot {
    
    public static boolean debug = false;
    
    
    public static void main(String[] args)
    {
        try {
            ModifierLoader.loadModifiers();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.main();
        Settings.load();

//        Item i = new Item()
    }
    
    public static boolean runAuto = false;
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
                    if (event.getButton() == 1) {
                        if (onSwingWindow() || ignore) return;
                        delay(Settings.singleton.delay + 35);
                        boolean b = Filters.checkIfHitOne(debug);
                        if (b) {
                            moveMouseAway();
//                            System.out.println("hit");
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
        Point topLeft  = Main.mainFrame.getLocation();
        Point botRight = new Point(topLeft.x + Main.mainFrame.getWidth(), topLeft.y + Main.mainFrame.getHeight());
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        
        boolean b = mouseLoc.x <= botRight.x &&
               mouseLoc.x >= topLeft.x &&
               mouseLoc.y <= botRight.y &&
               mouseLoc.y >= topLeft.y;
                
        return b;
    }
    
    public static void stop()
    {
        if (mouseHook != null)
            mouseHook.shutdownHook();
        mouseHook = null;
        if (keyHook != null)
            keyHook.shutdownHook();
        keyHook = null;
        Main.setChaosIcon(Main.mainFrame.getClass().getResource("/resources/images/chaos.png"));
    }
    
    private static void moveMouseAway()
    {
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(CraftingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        double xMult = 0.30885416;
        double yMult = 0.64537037;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        r.mouseMove((int) (xMult * screenSize.width), (int) (yMult * screenSize.height));
    }
        
    public static void runChaosSpam(Main main)
    {
        Filters.prepItemLoad();
        if (!Filters.verify())
        {
            JOptionPane.showMessageDialog(null, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Settings.singleton.runAuto)
        {
            runAuto(main);
        }
        
        else
        {
            while (!establishMouseHook());
            while (!establishKeyboardHook());
            Main.setChaosIcon(main.getClass().getResource("/resources/images/chaosrun.png"));
        }
    }
    
    public static boolean run = true;
    
    private static void runAuto(Main main)
    {
        Point modCheckLoc = new Point(331,559); // Point to check if the item has the correct mod (orange outline)
        Point getChaosLoc = new Point(547, 289); // Point to get chaos from
        
        run = true;
        
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(CraftingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        r.keyPress(KeyEvent.VK_SHIFT);
        try {
            rclick(getChaosLoc.x, getChaosLoc.y);
        } catch (AWTException ex) {
            Logger.getLogger(CraftingBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        delay(50);
        r.mouseMove(modCheckLoc.x, modCheckLoc.y-40);
        delay(50);
        
        while (run)
        {
            Point mp = MouseInfo.getPointerInfo().getLocation();
            if (!mp.equals(new Point(modCheckLoc.x, modCheckLoc.y-40)))
                break;
            
            lclick();
            delay(Settings.singleton.delay + 35);
            if (Filters.checkIfHitOne(debug))
            {
                Utility.playHitSound();
                break;
            }
        }
        
        r.keyRelease(KeyEvent.VK_SHIFT);
    }
}