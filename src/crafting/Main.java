package crafting;

import crafting.persistence.Settings;
import crafting.utility.Utility;
import crafting.UI.console.Console;
import crafting.UI.Frame;
import crafting.persistence.HotkeyPersistence;
import crafting.update.Release;
import crafting.update.UpdateCheck;
import java.util.logging.Level;
import java.util.logging.Logger;
import poeitem.ModifierLoader;

public class Main {
    
    private static final Release RELEASE = new Release("1.0.1");
    
    public static void main(String[] args)
    {
        Console.launch();
        
        System.out.println("> Loading UI Window... <");
        Utility.delay(100);
        Frame.main();
        System.out.println("> Finished! <");
        
        System.out.println();
        
        System.out.println("> Loading Modifiers... <");
        try {
            ModifierLoader.loadModifiers();
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("> Finished! <");
        
        System.out.println();

        System.out.println("> Loading Settings... <");
        Settings.load();
        System.out.println("> Finished! <");
        
        System.out.println();
        
        System.out.println("> Loading Hotkeys... <");
        HotkeyPersistence.load();
        System.out.println("> Finished! <");
        
        System.out.println();
        
        Frame.mainFrame.updateImportExport(Settings.singleton.pastebinKey);
        if (Console.loadingFrame != null) Console.loadingFrame.setAlwaysOnTop(false);
        Frame.mainFrame.onFinishedLoading();
        
        System.out.println("> Checking for updates... <");
        Release release = UpdateCheck.checkForNewRelease();
        int comp = release.compareTo(RELEASE);
        if (comp < 0) System.out.println("You have a newer release than the latest release.");
        else if (comp > 0) System.out.println("There is a new version available! Version " + release.toString() + " can now be downloaded at https://github.com/CharlieBaird/PoECraftingAssistant/releases");
        if (comp == 0) System.out.println("You are up to date with the latest release.");
        System.out.println("> Finished! <");
        
        System.out.println();
        
    }
}