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
//import craftingbot.modlist.ModList;
import java.util.regex.*;  
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;


public class CraftingBot {
    
    public static void main(String[] args)
    {
        Main.main();
    }
    
    public static boolean run = true;
    
    public static GlobalMouseHook mouseHook = null;
    
    public static boolean establishHook()
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
                            delay(85);
                            try {
                                if (Filters.checkIfHitOne()) {
                                    System.out.println("hit");
                                    Utility.playHitSound();
                                    moveMouseAway();
                                    mouseHook.shutdownHook();
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
        
    public static void runChaosSpam() throws AWTException, UnsupportedFlavorException, IOException
    {
        while (!establishHook());
        
//        Point modCheckLoc = new Point(331,559); // Point to check if the item has the correct mod (orange outline)
//        Point getChaosLoc = new Point(547, 289); // Point to get chaos from
        
//        run = true;
        
//        Robot r = new Robot();
        
//        r.keyPress(KeyEvent.VK_SHIFT);
//        rclick(getChaosLoc.x, getChaosLoc.y);
//        delay(50);
//        r.mouseMove(modCheckLoc.x, modCheckLoc.y-40);
//        delay(50);
        
//        while (run)
//        {
//            Point mp = MouseInfo.getPointerInfo().getLocation();
//            if (!mp.equals(new Point(modCheckLoc.x, modCheckLoc.y-40)))
//                break;
            
//            lclick();
//            delay(50);
//            if (Filters.checkIfHitOne() || true)
//            {
//                break;
//            }
//        }
        
//        r.keyRelease(KeyEvent.VK_SHIFT);
    }
}