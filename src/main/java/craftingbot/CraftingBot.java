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
    
    public static void main(String[] args)
    {
        Main.main();
    }
    
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
//                            System.out.println("clicked");
                            if (onSwingWindow() || ignore) return;
                            delay(85);
                            try {
                                boolean b = Filters.checkIfHitOne();
                                if (b) {
                                    moveMouseAway();
                                    System.out.println("hit");
                                    Utility.playHitSound();
                                }
                            } catch (AWTException | UnsupportedFlavorException | IOException ex) {
                            Logger.getLogger(CraftingBot.class.getName()).log(Level.SEVERE, null, ex);
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
        
        System.out.println(b);
        
        return b;
    }
    
    public static void stop()
    {
        mouseHook.shutdownHook();
        mouseHook = null;
        keyHook.shutdownHook();
        keyHook = null;
        System.out.println("stopped");
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
        
    public static void runChaosSpam(Main main) throws AWTException, UnsupportedFlavorException, IOException
    {
        while (!establishMouseHook());
        while (!establishKeyboardHook());
        Main.setChaosIcon(main.getClass().getResource("/chaosrun.png"));
    }
}