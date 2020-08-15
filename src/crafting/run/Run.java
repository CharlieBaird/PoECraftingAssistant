package crafting.run;

import crafting.ActivityTooltip;
import crafting.UI.Frame;
import crafting.filters.Filter;
import crafting.utility.Utility;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.mouse.GlobalMouseHook;

public class Run {
    public static final boolean DEBUG = false;
    
    public static boolean run = true;
    public static GlobalMouseHook mouseHook = null;
    public static GlobalKeyboardHook hotkeyHook = null;
    public static ActivityTooltip activityTooltip;
        
    public static void runFilter()
    {
        if (mouseHook != null)
        {
            stop();
            return;
        }
        
        if (!Filter.verify())
        {
            JOptionPane.showMessageDialog(Frame.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        run = true;
        
        StringSelection selection = new StringSelection("hi");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        
        activityTooltip = new ActivityTooltip();
        HookEstablisher.establishMouseListener();
        Frame.setChaosIcon(Frame.mainFrame.getClass().getResource("/resources/images/chaosrun.png"));
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
        Frame.setChaosIcon(Frame.mainFrame.getClass().getResource("/resources/images/chaos.png"));
    }
    
    
    
    public static void testFilter(Frame main, JButton owner)
    {
        if (!Filter.verify())
        {
            JOptionPane.showMessageDialog(Frame.mainFrame, "Invalid Mod", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String raw = Utility.getClipboard();
        
        if (raw != null && !raw.equals(""))
        {
            Filter.testMods = raw;
            if (Filter.checkIfHitOne(true))
            {
                JOptionPane.showMessageDialog(Frame.mainFrame, "The item hit the filter!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(Frame.mainFrame, "The item did not hit the filter.", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
