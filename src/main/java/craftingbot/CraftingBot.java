/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import static craftingbot.Utility.*;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;


public class CraftingBot {
    
    public static boolean debug = false;
    
    public static void main(String[] args)
    {
        try {
            Utility.pullModsFromAPI();
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
                        boolean b = Filters.checkIfHitOne(false);
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
        mouseHook.shutdownHook();
        mouseHook = null;
        keyHook.shutdownHook();
        keyHook = null;
        Main.setChaosIcon(Main.mainFrame.getClass().getResource("/chaos.png"));
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
        if (Settings.singleton.runAuto)
        {
            runAuto(main);
            return;
        }
        
        while (!establishMouseHook());
        while (!establishKeyboardHook());
        Main.setChaosIcon(main.getClass().getResource("/chaosrun.png"));
    }
    
    public static boolean run = true;
    
    private static void runAuto(Main main)
    {
        Main.setChaosIcon(main.getClass().getResource("/chaosrun.png"));
        
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
            if (Filters.checkIfHitOne(false))
            {
                Utility.playHitSound();
                break;
            }
        }
        
        r.keyRelease(KeyEvent.VK_SHIFT);
        Main.setChaosIcon(Main.mainFrame.getClass().getResource("/chaos.png"));
    }
}