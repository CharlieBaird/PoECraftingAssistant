package crafting.UI;

import crafting.filters.Filter;
import poeitem.Modifier;
import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import crafting.itemconfig.InfluenceConfig;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import poeitem.bases.BaseItem;
import poeitem.ModifierTier;
import crafting.persistence.FilterPersistence;

public class ModifierPanel extends JPanel {
     
    public String resourcePath;
    public Frame frame;
    public FilterBase filterbase;
    public Mod mod;
    public FilterTypePanel parent;
    
    public TierComboBox tier;
//    public MPMinMax min;
//    public MPMinMax max;
    public ModifierComboBox mcb;
    
    public Modifier assocMod = null;
    
    public ModifierPanel(Frame frame, FilterTypePanel parent, FilterBase filterbase, Mod mod, ModifierComboBox searchBox)
    {
        String path = "src/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        this.frame = frame;
        this.filterbase = filterbase;
        this.parent = parent;
        
        if (mod == null)
        {
            mod = new Mod(null, null);
            filterbase.mods.add(mod);
        }
        
        this.mod = mod;
        
        Dimension size = new Dimension((int) (parent.getWidth()),(int) (40)); // 0.912
        setSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        setBackground(new Color(60,60,60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setVisible(filterbase.UIVisible);
        
        
        CloseMPButton cb = new CloseMPButton(this);
        mcb = showSearchBox(mod, assocMod, searchBox);
        mcb.setPreferredSize(new Dimension(this.getWidth(), 20));
        mcb.setMaximumSize(new Dimension(this.getWidth(), 20));
        ((JTextField) mcb.getEditor().getEditorComponent()).setFont(Frame.mainFrame.getNewFont(12));
        ((JLabel) mcb.getRenderer()).setFont(Frame.mainFrame.getNewFont(12));
        tier = new TierComboBox(this);
        
        
        add(cb, Box.LEFT_ALIGNMENT);
        add(mcb, Box.LEFT_ALIGNMENT);
        
        add(Box.createHorizontalGlue());

        add(tier, Box.RIGHT_ALIGNMENT);
        
//        if (Filter.singleton.SelectedBase != null && assocMod != null)
//        {
//            this.showTierComboBox(assocMod);
//            this.updateDD();
//        }
        
        parent.modifierpanels.add(this);
        parent.add(this);
        mod.assocModifierPanel = this;
        
        FilterPersistence.saveFilters();
    }
    
    public void updateMCBSize()
    {
        this.mcb.setPreferredSize(new Dimension(this.getWidth(), 20));
        this.mcb.setMaximumSize(new Dimension(this.getWidth(), 20));
    }
    
    public ModifierComboBox showSearchBox(Mod mod, Modifier aMod, ModifierComboBox searchBox)
    {
        
        if (searchBox == null)
        {
            Modifier[] types;
        
            if (Filter.singleton.SelectedBase == null) {
                types = ModifierComboBox.toArr(Modifier.AllExplicitModifiers);
            }
            else {
                BaseItem base = Filter.singleton.SelectedBase;
                ArrayList<Modifier> modifiers = Modifier.getAllApplicableModifiers(base, InfluenceConfig.getArrOfSelectedInflu());
                types = ModifierComboBox.toArr(modifiers);
            }
            ModifierComboBox mcb = new ModifierComboBox(this, types);

            if (aMod != null)
            {
                mcb.setSelectedItem(aMod);
            }
            else
            {
                if (mod == null || mod.assocModifier == null)
                    ((JTextField)mcb.getEditor().getEditorComponent()).setText("New Modifier");
                else
                    ((JTextField)mcb.getEditor().getEditorComponent()).setText(mod.assocModifier.getKey());
                ((JTextField)mcb.getEditor().getEditorComponent()).setForeground(new Color(238,99,90));
            }

            mcb.setMinimumSize(new Dimension(0,0));
            add(mcb);
            return mcb;
        }
        mcb.setMinimumSize(new Dimension(0,0));
        add (searchBox);
        return searchBox;
    }
    
    public void showTierComboBox(Modifier m)
    {
        DefaultComboBoxModel model = new DefaultComboBoxModel(tier.modelToTiers(m, Filter.singleton.SelectedItemLevel));
        if (model.getSize() <= 1)
        {
            hideTierComboBox();
            return;
        }
        tier.setModel(model);
        
        ModifierTier mt = this.mod.assocModifierTier;
        if (mt == null)
        {
            tier.setSelectedIndex(0);
        }
        else
        {
            tier.setSelectedItem(mt);
        }
        this.mod.assocModifierTier = (ModifierTier) tier.getSelectedItem();

        tier.setVisible(true);
    }
    
    public void hideTierComboBox()
    {
        tier.setVisible(false);
    }
    
    public boolean updateDD() // true if success
    {
        if (assocMod != null)
        {
            mcb.setSelectedItem(assocMod);
            showTierComboBox(assocMod);
            return true;
        }
        
        return false;
    }
    
    public static void updateTierViews() {
        if (Filter.singleton.SelectedBase == null) return;
                        
        for (FilterTypePanel ftp : FilterTypePanel.filtertypepanels)
        {
            for (ModifierPanel mp : ftp.modifierpanels)
            {
//                mp.mcb.update(mp.assocMod, false);
                boolean success = mp.updateDD();
                if (success)
                {
                    mp.mcb.setForeground(new Color(255,255,255));
                }
                else
                {
                    mp.mcb.setForeground(new Color(238,99,90));
                    mp.hideTierComboBox();
                }
            }
        }
    }
    
    private static String genErrorMsg(ArrayList<Modifier> mods)
    {
        String s = "The following modifiers in your filters cannot be hit on Item Type \"" + Filter.singleton.SelectedBase + "\"\n";
        for (int i=0; i<mods.size(); i++)
        {
            s += (i+1) + ". " + mods.get(i).getKey() + "\n";
        }
        
        return s;
    }
}

class TierComboBox extends JComboBox {
    
    Modifier assocModifier = null;
    ModifierPanel parent = null;
    
    public TierComboBox(ModifierPanel parent)
    {
        this.assocModifier = parent.assocMod;
        this.parent = parent;
        
        setVisible(false);
        setPreferredSize(new Dimension(80,32));
        setMaximumSize(new Dimension(80,32));
        setMaximumRowCount(20);
        
        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event)
            {
               if (event.getStateChange() == ItemEvent.SELECTED)
               {
                    ModifierTier selection = (ModifierTier) event.getItem();
                    parent.mod.assocModifierTier = selection;
               }
            }
        });
    }
    
    public ModifierTier[] modelToTiers(Modifier m, int itemLevel)
    {
        this.assocModifier = m;
        ModifierTier[] tiersWithLevel = m.getModifierTiers(itemLevel);
        return tiersWithLevel;
    }
}

class CloseMPButton extends JButton {
    
    ModifierPanel parent;
    
    public CloseMPButton(ModifierPanel parent)
    {
        this.parent = parent;
        
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setMaximumSize(new Dimension(40,40));
        setMinimumSize(new Dimension(40,40));
        setPreferredSize(new Dimension(40,40));
        setBackground(new Color(60,60,60));
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/xbuttontransparentsmall.png"))); // NOI18N
        setToolTipText("Remove this mod");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(60,60,60)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Frame.mainFrame.requestFocusInWindow();
                parent.parent.modifierpanels.remove(parent);
                parent.filterbase.mods.remove(parent.mod);
                FilterTypePanel.reshow();
                parent.setVisible(false);
        
                FilterPersistence.saveFilters();
            }
        };
        addActionListener(actionListener);
    }
}

class ModLabel extends JLabel {
    public ModLabel(ModifierPanel parent, String text)
    {
        setText(text);
        setFont(parent.frame.getNewFont(13));
        setBackground(new Color(255,0,0));
        setForeground(new Color(255,255,255));
    }
}