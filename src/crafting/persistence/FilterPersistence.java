package crafting.persistence;

import crafting.UI.FilterNamePanel;
import crafting.UI.Frame;
import crafting.UI.ModifierPanel;
import crafting.filters.Filter;
import crafting.utility.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FilterPersistence {
    public static void openFilter()
    {
        JFileChooser chooser = new JFileChooser(Utility.getResourcesPath() + "/src/resources/filters");
        if (chooser.showOpenDialog(Frame.mainFrame) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String path = file.toPath().toString();
            
            Filter loaded = null;
            loaded = loadFilters(path);
            openFilter(loaded);
            return;
        }
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
    }
    
    public static void openFilter(Filter loaded)
    {
        if (loaded == null) // Errored, wrong serial ID
        {
            JOptionPane.showMessageDialog(Frame.mainFrame, "Invalid Filter. Filters from previous PoE Crafting Assistant\nversions cannot be opened.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveFilters();
        Filter.reset_openFilter();
        Frame.mainFrame.updateLeftTab();

        for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
        {
            FilterNamePanel.filterpanels.get(i).remove();
        }

        FilterNamePanel.filterpanels.clear();

        Filter.singleton = loaded;
        Filter.singleton.isInitial = false;

        Frame.mainFrame.updateLeftTab();
        Frame.mainFrame.itemConfigPanel.updateFromFilter();

        if (FilterNamePanel.filterpanels.size() >= 1)
        {
            FilterNamePanel.filterpanels.get(0).open();
        }

        Frame.mainFrame.onOpenFilter();

        ModifierPanel.updateTierViews();
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
    }
    
    public static void importFilter(Filter loaded)
    {
        if (loaded == null) // Errored, wrong serial ID
        {
            JOptionPane.showMessageDialog(Frame.mainFrame, "Invalid Filter. Filters from previous PoE Crafting Assistant\nversions cannot be opened.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveFilters();
        boolean useSavedName = false;
        String savedName = "";
        if (!Filter.singleton.isInitial)
        {
            useSavedName = true;
            savedName = Filter.singleton.name;
        }
        Filter.reset_openFilter();
        Frame.mainFrame.updateLeftTab();

        for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
        {
            FilterNamePanel.filterpanels.get(i).remove();
        }

        FilterNamePanel.filterpanels.clear();
        
        
        Filter.singleton = loaded;
        Filter.singleton.isInitial = false;
        if (useSavedName)
        {
            Filter.singleton.name = savedName;
        }

        Frame.mainFrame.updateLeftTab();
        Frame.mainFrame.itemConfigPanel.updateFromFilter();

        if (FilterNamePanel.filterpanels.size() >= 1)
        {
            FilterNamePanel.filterpanels.get(0).open();
        }

        Frame.mainFrame.onOpenFilter();

        ModifierPanel.updateTierViews();
        
        for (FilterNamePanel fnp : FilterNamePanel.filterpanels)
        {
            if (fnp.active)
            {
                fnp.open();
            }
        }
    }
    
    public static Filter loadFilters(String path)
    {
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Filter input = null;
        try {
            input = (Filter) oi.readObject();
        } catch (InvalidClassException | ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            oi.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }
    
    public static void saveFilters()
    {
        
        if (Filter.singleton.name.equals("") || Filter.singleton.name == null)
            return;
        
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(new File(Utility.getResourcesPath() + "/src/resources/filters" + "/" + Filter.singleton.name + ".cbfilter"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Write objects to file
            o.writeObject(Filter.singleton);
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteFilters(String name)
    {
        File f = new File(Utility.getResourcesPath() + "/src/resources/filters" + "/" + name + ".cbfilter");
        f.delete();
    }
    
    public static void createNewFilter()
    {   
        String name = (String)JOptionPane.showInputDialog(Frame.mainFrame,
            "Enter the New Filter's Name",
            "PoE Crafting Assistant",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            "New Filter");

        if (name != null && !name.equals(""))
        {
            File file = new File(Utility.getResourcesPath() + "/src/resources/filters" + "/" + name + ".cbfilter");
            if (file.exists())
            {
                Object[] message = {
                    "A filter of the name \"" + name + "\" already exists.\nContinuing will overwrite this filter. Is this ok?"
                };

                int n = JOptionPane.showConfirmDialog(Frame.mainFrame, message, "Settings", JOptionPane.OK_CANCEL_OPTION);
                
                if (n != JOptionPane.OK_OPTION) return;
            }
            
            FilterPersistence.saveFilters();

            Filter.reset_newFilter();

            for (int i=0; i<FilterNamePanel.filterpanels.size(); i++)
            {
                FilterNamePanel.filterpanels.get(i).remove();
            }

            FilterNamePanel.filterpanels.clear();


            Filter.singleton.setName(name);
            Filter.singleton.isInitial = false;
            FilterPersistence.saveFilters();

            Frame.mainFrame.onCreateNewFilter(name);
        }
    }
}
