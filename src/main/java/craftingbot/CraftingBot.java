/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import static craftingbot.Utility.*;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
//import craftingbot.modlist.ModList;
import java.util.regex.*;  


public class CraftingBot {
    
//    public static ModList modlist = null;
    
    public static boolean run = true;
    
    public static void runBot(String[] args) throws AWTException, UnsupportedFlavorException, IOException, Exception {
//        modlist = Utility.pullModsFromAPI();
        
//        runChaosSpam();

//        Filter filter = new Filter();
//        filter.print();

//        testRegex();
    }
    
    public static void testRegex()
    {
//        System.out.println(Pattern.matches("^([+])([0-9]+)([ to ][a-zA-Z ]+)$", "+4 to dexterity")); // +# to ""
        
//        Pattern p = Pattern.compile("^([+])([0-9]+)([ to ][a-zA-Z ]+)$");
//        Matcher m = p.matcher("+4 to dexterity");
//        System.out.println(m.find());
        
//        if (m.find())
//            System.out.println(m.group(2));
        
//        Pattern p = Pattern.compile("^([a-zA-Z ]+)([:])([ ])([0-9]+)([^%]+)$");
//        Matcher m = p.matcher("energy shield: 59 (augmented)");
        
//        Pattern p = Pattern.compile("^([0-9]+)([ ])(added passive skill is )([a-zA-Z ]*)$");
//        Matcher m = p.matcher("1 added passive skill is heraldry");
//        
//        if (m.find())
//        {
//            System.out.println(m.group(1));
//        }
            
        
        
//        System.out.println(Pattern.matches("^[0-9]{1,5}[%][ increased ][a-zA-Z ]{1,30}$", "743% increased energy shield")); // #% increased ""
//        System.out.println(Pattern.matches("^[+][0-9]{1,5}[%][ to ].{1,30}$", "+34% to fire resistance")); // +#% to ""
//        System.out.println(Pattern.matches("^[a-zA-Z ]{1,30}[:][ ][0-9]{1,5}$", "Energy Shield: 32")); // "": #
//        System.out.println(Pattern.matches("^[a-zA-Z ]{1,30}[:][ ][0-9]{1,5}[%]$", "Quality: 32%")); // "": #%
//        System.out.println(Pattern.matches("^[a-zA-Z ]{1,40}[0-9]{1,5}[ ][t][o][ ][0-9]{1,5}[a-zA-Z ]{1,40}", "Adds 5 to 10 lightning damage to spells")); // "" # to # ""
//        System.out.println(Pattern.matches("^.*$", "adds purposeful harbinger")); // words
    }
    
    public static void runChaosSpam() throws AWTException, UnsupportedFlavorException, IOException
    {
        Point modCheckLoc = new Point(331,559); // Point to check if the item has the correct mod (orange outline)
        Point getChaosLoc = new Point(547, 289); // Point to get chaos from
        
        run = true;
        
        Robot r = new Robot();
        
        r.keyPress(KeyEvent.VK_SHIFT);
        rclick(getChaosLoc.x, getChaosLoc.y);
        delay(50);
        r.mouseMove(modCheckLoc.x, modCheckLoc.y-40);
        delay(50);
        
        while (run)
        {
//            lclick();
            delay(50);
            if (Filters.checkIfHitOne())
                break;
        }
        
        r.keyRelease(KeyEvent.VK_SHIFT);
    }
    
        
    public static void runAltSpam() throws AWTException, UnsupportedFlavorException, IOException
    {
        Point modCheckLoc = new Point(331,559);
        Point getAltLoc = new Point(115, 290);
        Point getAugLoc = new Point(230, 350);
        
        Robot r = new Robot();
                
        run = true;
        while (run)
        {
            rclick(getAltLoc.x, getAltLoc.y);
            delay(50);
            r.mouseMove(modCheckLoc.x, modCheckLoc.y-40);
            delay(50);
            lclick();
            delay(50);
            rclick(getAugLoc.x, getAugLoc.y);
            delay(50);
            r.mouseMove(modCheckLoc.x, modCheckLoc.y-40);
            delay(50);
            lclick();
            delay(50);
            if (Filters.checkIfHitOne())
                break;
        }
        
        r.keyRelease(KeyEvent.VK_SHIFT);
    }
}