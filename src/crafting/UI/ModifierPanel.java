package crafting.UI;

import crafting.Filters;
import poeitem.Modifier;
import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
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
import poeitem.Base;
import poeitem.BaseItem;
import poeitem.ModifierTier;

public class ModifierPanel extends JPanel {
     
    public String resourcePath;
    public Main frame;
    public FilterBase filterbase;
    public Mod mod;
    public FilterTypePanel parent;
    
    public TierComboBox tier;
    public MPMinMax min;
    public MPMinMax max;
    public ModifierComboBox mcb;
    
    public Modifier assocMod = null;
    
    public ModifierPanel(Main frame, FilterTypePanel parent, FilterBase filterbase, Mod mod, ModifierComboBox searchBox)
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
            min = new MPMinMax(this, "min", true);
            max = new MPMinMax(this, "max", false);
        }
        else if (mod.name == null)
        {
            min = new MPMinMax(this, "min", true);
            max = new MPMinMax(this, "max", false);
        }
        
        else if (Filters.singleton.SelectedBase != null)
        {
            assocMod = BaseItem.getFromBase(Filters.singleton.SelectedBase).getExplicitFromStr(mod.name);
        }
        else
        {
            assocMod = Modifier.getExplicitFromStr(mod.name);
        }
        this.mod = mod;
        
        Dimension size = new Dimension((int) (parent.getWidth()),(int) (40)); // 0.912
        setSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        setBackground(new Color(60,60,60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setVisible(filterbase.UIVisible);
        
        
        CloseMPButton cb = new CloseMPButton(this);
        mcb = showSearchBox(mod, assocMod, searchBox);
        mcb.setPreferredSize(new Dimension(500,20));
        mcb.setMaximumSize(new Dimension(500,20));
        ((JTextField) mcb.getEditor().getEditorComponent()).setFont(Main.mainFrame.getNewFont(12));
        ((JLabel) mcb.getRenderer()).setFont(Main.mainFrame.getNewFont(12));
        min = new MPMinMax(this, String.valueOf(mod.ID.min), true);
        max = new MPMinMax(this, String.valueOf(mod.ID.max), false);
        tier = new TierComboBox(this);
        
        
        add(cb, Box.LEFT_ALIGNMENT);
        add(mcb, Box.LEFT_ALIGNMENT);
        
        add(Box.createHorizontalGlue());

        add(tier, Box.RIGHT_ALIGNMENT);
        add(min, Box.RIGHT_ALIGNMENT);
        add(max, Box.RIGHT_ALIGNMENT);
        
        if (Filters.singleton.SelectedBase != null && assocMod != null && assocMod.tiers.size() >= 1)
        {
            this.showTierComboBox(assocMod);
            this.updateDD();
            this.tier.manualUpdate(this.min.getText());
        }
        
        parent.modifierpanels.add(this);
        parent.add(this);
        mod.assocModifierPanel = this;
        
        Filters.saveFilters();
    }
    
    public ModifierComboBox showSearchBox(Mod mod, Modifier aMod, ModifierComboBox searchBox)
    {
        if (searchBox == null)
        {
            Modifier[] types;
        
            if (Filters.singleton.SelectedBase == null) {
                types = ModifierComboBox.toArr(Modifier.AllExplicitModifiers);
            }
            else {
                ArrayList<Modifier> modifiers = BaseItem.getFromBase(Filters.singleton.SelectedBase).assocModifiers;
                types = ModifierComboBox.toArr(modifiers);
            }
            ModifierComboBox mcb = new ModifierComboBox(this, types);

            if (aMod != null)
            {
                mcb.setSelectedItem((Object) aMod.getStr());
            }
            else
            {
                if (mod.name == null)
                    ((JTextField)mcb.getEditor().getEditorComponent()).setText("New Modifier");
                else
                    ((JTextField)mcb.getEditor().getEditorComponent()).setText(mod.name);
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
        DefaultComboBoxModel model = new DefaultComboBoxModel(tier.modelToTiers(m, Filters.singleton.SelectedItemLevel));
        if (model.getSize() <= 1)
        {
            hideTierComboBox();
            return;
        }
        tier.setModel(model);
        
        if (this.min.getText().equals("min"))
        {
            this.tier.setSelectedIndex(0);
            tier.manualUpdate(min.getText());
        }
        
        tier.setVisible(true);
    }
    
    public void hideTierComboBox()
    {
        tier.setVisible(false);
    }
    
    public Modifier updateDD()
    {
        Modifier thrownModifier = null;
        
        if (assocMod != null)
        {
            Modifier result = (Modifier) this.mcb.getSelectedItem();

            if (result != null)
            {
                assocMod = result;
                showTierComboBox(assocMod);
            }
            else
            {
                thrownModifier = assocMod;
            }
        }
        
        return thrownModifier;
    }
    
    public static void updateTierViews() {
        if (Filters.singleton.SelectedBase == null) return;
                
        ArrayList<Modifier> errorModifiers = new ArrayList<>();
        
        for (FilterTypePanel ftp : FilterTypePanel.filtertypepanels)
        {
            for (ModifierPanel mp : ftp.modifierpanels)
            {
                mp.mcb.update(mp.assocMod, false);
                Modifier m = mp.updateDD();
                if (m != null)
                {
                    errorModifiers.add(m);
                    mp.mcb.setForeground(new Color(238,99,90));
                    mp.hideTierComboBox();
                }
                else
                {
                    mp.mcb.setForeground(new Color(255,255,255));
                    if (!mp.min.getText().equals("min"))
                    {
                        mp.tier.manualUpdate(mp.min.getText());
                    }
                }
            }
        }
    }
    
    private static String genErrorMsg(ArrayList<Modifier> mods)
    {
        String s = "The following modifiers in your filters cannot be hit on Item Type \"" + Filters.singleton.SelectedBase + "\"\n";
        for (int i=0; i<mods.size(); i++)
        {
            s += (i+1) + ". " + mods.get(i).getStr() + "\n";
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
                    String selection = (String) event.getItem();
                    if (selection.equals("Custom")) return;
                    int tier = Integer.valueOf((selection).substring(1,selection.length()));
                    setMin(tier);
               }
            }
        });

        
    }
    
    public void setMin(int whatTier)
    {
        if (assocModifier != null)
        {
            whatTier = assocModifier.tiers.size() - whatTier;
            
            double val = assocModifier.tiers.get(whatTier).getValue();
            
            parent.min.textUpdate(val);
            
        }
    }
    
    public String[] modelToTiers(Modifier m, int itemLevel)
    {
        this.assocModifier = m;
        ModifierTier[] tiers = m.getTiersWithLevel(itemLevel);
                
        String[] tiersStr = new String[1+tiers.length];
        
        tiersStr[tiersStr.length-1] = "Custom";
        for (int i=0; i<tiers.length; i++)
        {
//            int val = (int) m.tiers.get(m.tiers.size()-1-i).getValue();
            tiersStr[i] = "T" + (i + 1)/* + " - " + val*/;
        }        
        return tiersStr;
        
    }

    public void manualUpdate(String text) {
        Base b = Filters.singleton.SelectedBase;
        if (Filters.singleton.SelectedBase != null)
        {
            if (!text.equals("min") && !text.equals(""))
            {
                Integer value = Integer.valueOf(text);
                if (assocModifier == null)
                {
                    parent.hideTierComboBox();
                    return;
                }
                else if (assocModifier.tiers.size() >= 1)
                {
                    for (int i=0; i<assocModifier.tiers.size(); i++)
                    {
                        if ((int) assocModifier.tiers.get(i).getValue() == value)
                        {
                            int foundTier = assocModifier.tiers.size() - i - 1;
                            if (foundTier < this.getModel().getSize() && foundTier != -1)
                                this.setSelectedIndex(foundTier);
                            else this.setSelectedIndex(this.getModel().getSize()-1);
                            return;
                        }
                    }
                }
                else
                {
                    parent.hideTierComboBox();
                    return;
                }
            }
        }
        if (assocModifier.tiers.size() >= 1)
            this.setSelectedIndex(this.getModel().getSize()-1);
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
                Main.mainFrame.requestFocusInWindow();
                parent.parent.modifierpanels.remove(parent);
                parent.filterbase.mods.remove(parent.mod);
                FilterTypePanel.reshow();
                parent.setVisible(false);
        
                Filters.saveFilters();
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
//        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.4),(int) ((32))));
    }
}

class MPMinMax extends JTextField {
    public String placeholder;
    public boolean isMin; // true = min, false = max;
    public ModifierPanel parent;
    
    public MPMinMax(ModifierPanel parent, String placeholder, boolean isMin)
    {
        if (placeholder.contains("100000"))
        {
            if (isMin) placeholder = "min";
            else placeholder = "max";
        }
        
        setText(placeholder);
        setMaximumSize(new Dimension(45,40));
        setMinimumSize(new Dimension(45,40));
        setPreferredSize(new Dimension(45,40));
        
        this.isMin = isMin;
        this.parent = parent;
        
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(0,0,0));
        setForeground(new Color(120,120,120));
        
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        
        if (!placeholder.equals("min") && !placeholder.equals("max"))
            setForeground(new Color(255,255,255));
        
        if (!placeholder.equals("min") && !placeholder.equals("max"))
            placeholder = isMin ? "min" : "max";
        
        this.placeholder = placeholder;
        
        setHorizontalAlignment(SwingConstants.CENTER);
        
        addFocusListener(new FListener(this, this.placeholder));
                
        addKeyListener(new NumFieldKeyListener());
    }
    
    @Override
    public void paste() {}
    
    public void focusGained()
    {
        if (getText().equals(placeholder)) {
            setText("");
            setForeground(new Color(255,255,255));
        }
    }
    
    public void focusLost()
    {
        
        if (getText().isEmpty())
        {
            setText(placeholder);
            setForeground(new Color(120,120,120));
            if (isMin) parent.mod.ID.min = -100000;
            else       parent.mod.ID.max =  100000;
        }

        else 
        {
            try {
                if (isMin) parent.mod.ID.min = Integer.valueOf(parent.min.getText());
                else       parent.mod.ID.max = Integer.valueOf(parent.max.getText());
            } catch (NumberFormatException e) { }
        }
            
        if (isMin && parent.tier.isVisible())
        {
            parent.tier.manualUpdate(getText());
        }
        
        Filters.saveFilters();
    }

    void textUpdate(double val) {
        setForeground(new Color(255,255,255));
        setText(String.valueOf((int) val));
        parent.mod.ID.min = (int) val;
        Filters.saveFilters();
    }
}

class FListener implements FocusListener
{
    String placeholder;
    MPMinMax owner;
    
    public FListener(MPMinMax owner, String placeholder)
    {
        this.placeholder = placeholder;
        this.owner = owner;
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        owner.focusGained();
    }
    @Override
    public void focusLost(FocusEvent e) {
        owner.focusLost();
    }
}