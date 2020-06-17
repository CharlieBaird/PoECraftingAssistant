package crafting.UI;

import crafting.Main;
import crafting.Filters;
import poeitem.Modifier;
import crafting.filtertypes.FilterBase;
import crafting.filtertypes.Mod;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import poeitem.BaseItem;
import poeitem.ModifierLoader;
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
    public ModLabel ml;
    
    public Modifier assocMod;
    
    public ModifierPanel(Main frame, FilterTypePanel parent, FilterBase filterbase, Mod mod)
    {
        String path = "src/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        this.frame = frame;
        this.filterbase = filterbase;
        this.mod = mod;
        this.parent = parent;
        
        if (mod == null)
        {
            mod = new Mod(null, "New Modifier");
            filterbase.mods.add(mod);
            min = new MPMinMax(this, "min", true);
            max = new MPMinMax(this, "max", false);
        }
        
        assocMod = Modifier.getExplicitFromStr(mod.name);
        
        Dimension size = new Dimension((int) (parent.getWidth() * 0.95),(int) (40)); // 0.912
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(60,60,60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setVisible(filterbase.UIVisible);
        
        CloseMPButton cb = new CloseMPButton(this);
        ml = new ModLabel(this, mod.name);
        min = new MPMinMax(this, String.valueOf(mod.ID.min), true);
        max = new MPMinMax(this, String.valueOf(mod.ID.max), false);
        tier = new TierComboBox();
        
        add(cb, Box.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(15,0)), Box.LEFT_ALIGNMENT);
        add(ml, Box.LEFT_ALIGNMENT);
        
        add(Box.createHorizontalGlue());
        add(tier, Box.RIGHT_ALIGNMENT);
        add(min, Box.RIGHT_ALIGNMENT);
        add(max, Box.RIGHT_ALIGNMENT);
        
        addMouseListener(new ModMouseListener(this));
        
        parent.add(this);
        
        Filters.saveFilters();
    }
    
    public void showSearchBox()
    {
        String[] types = SearchBox.toArr(Modifier.AllExplicitModifiers);
        SearchBox sb = new SearchBox(types);
        
        sb.setSelectedIndex(-1);
        
        JPanel msgPanel = new JPanel();
        msgPanel.add(sb);
        
        JOptionPane message = new JOptionPane(msgPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
            @Override
            public void selectInitialValue() {
                sb.requestFocusInWindow();
            }
        };
        message.createDialog(this, "PoE Crafting Assistant").setVisible(true);
        
        Object selected = sb.getSelectedItem();
        
        if (selected != null && !selected.toString().equals(""))
        {
            if (ItemBase.SelectedBase == null)
            {
                Modifier m = Modifier.getExplicitFromStr(selected.toString());
                if (m != null)
                {
                    assocMod = m;
                    mod.name = m.getStr();
                    mod.assocModifier = assocMod;
                    ml.setText(m.getStr());
                    hideTierComboBox();

                    Filters.saveFilters();
                }
            }
            else
            {
                Modifier m = BaseItem.getFromBase(ItemBase.SelectedBase).getExplicitFromStr(selected.toString());
                if (m != null)
                {
                    assocMod = m;
                    mod.name = m.getStr();
                    mod.assocModifier = assocMod;
                    ml.setText(m.getStr());
                    
                    showTierComboBox(m);

                    Filters.saveFilters();
                }
            }
        }
        
        ModifierLoader.loadModifiers();
    }
    
    public void showTierComboBox(Modifier m)
    {
        DefaultComboBoxModel model = new DefaultComboBoxModel(tier.modelToString(m));
        if (model.getSize() == 0)
        {
            hideTierComboBox();
            return;
        }
        tier.setModel(model);
        tier.setPrototypeDisplayValue(12);
        tier.setVisible(true);
    }
    
    public void hideTierComboBox()
    {
        tier.setVisible(false);
        if (assocMod != null) assocMod = Modifier.getExplicitFromStr(assocMod.getStr());
    }
    
    public Modifier updateDD()
    {
        Modifier thrownModifier = null;
        
        if (ItemBase.SelectedBase != null)
        {
            if (assocMod != null)
            {
                Modifier result = BaseItem.getFromBase(ItemBase.SelectedBase).getExplicitFromStr(assocMod.getStr());
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
        }
        else
        {
            hideTierComboBox();
        }
        
        return thrownModifier;
    }
    
    public static void updateTierViews() {
        ArrayList<Modifier> errorModifiers = new ArrayList<>();
        
        for (FilterTypePanel ftp : FilterTypePanel.filtertypepanels)
        {
            for (ModifierPanel mp : ftp.modifierpanels)
            {
                if (mp.isVisible())
                {
                    Modifier m = mp.updateDD();
                    if (m != null)
                    {
                        errorModifiers.add(m);
                    }
                }
            }
        }
        System.out.println(errorModifiers.size());
        if (errorModifiers.size() >= 1)
        {
            String errorMsg = genErrorMsg(errorModifiers);
            JOptionPane.showMessageDialog(Main.mainFrame, errorMsg, "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private static String genErrorMsg(ArrayList<Modifier> mods)
    {
        String s = "The following modifiers in your filters cannot be hit on Item Type \"" + ItemBase.SelectedBase + "\"\n";
        for (int i=0; i<mods.size(); i++)
        {
            s += (i+1) + ". " + mods.get(0).getStr() + "\n";
        }
        
        return s;
    }
}

class TierComboBox extends JComboBox {
    
    public TierComboBox()
    {
        setVisible(false);
    }
    
    public String[] modelToString(Modifier m)
    {
        ModifierTier[] tiers = m.getTiersWithLevel(ItemBase.SelectedItemLevel);
        String[] tiersStr = new String[tiers.length];
        int highest = m.getHighestHittableTier(ItemBase.SelectedItemLevel);
        
        for (int i=0; i<tiers.length; i++)
        {
            tiersStr[i] = "T" + (i + highest);
        }
        
        return tiersStr;
    }
    
}

class ModMouseListener implements MouseListener {
    
    ModifierPanel owner;
    
    public ModMouseListener(ModifierPanel owner)
    {
        this.owner = owner;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        owner.showSearchBox();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.06),(int) ((32))));
        setBackground(new Color(60,60,60));
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/xbuttontransparentsmall.png"))); // NOI18N
        setToolTipText("Remove this mod");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(60,60,60)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.parent.parent.requestFocusInWindow();
                boolean b = parent.parent.modifierpanels.remove(parent);
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
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.74),(int) ((32))));
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
        
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.09),(int) (parent.getHeight())));
        setMinimumSize(new Dimension((int) (parent.getWidth() * 0.09),(int) (parent.getHeight())));
        setMaximumSize(new Dimension((int) (parent.getWidth() * 0.09),(int) (parent.getHeight())));
        
        setHorizontalAlignment(SwingConstants.CENTER);
        
        addFocusListener(new FListener(this, this.placeholder));
                
        addKeyListener(new NumFieldKeyListener());
    }
    
    public void focusGained()
    {
        if (getText().equals(placeholder)) {
            setText("");
            setForeground(new Color(255,255,255));
        }
    }
    
    public void focusLost()
    {
        if (getText().isEmpty()) {
            setText(placeholder);
            setForeground(new Color(120,120,120));
            if (isMin) parent.mod.ID.min = -100000;
            else       parent.mod.ID.max =  100000;
//            parent.mod.updateMin();
        }

        // save value
        else 
            try {
                if (isMin) parent.mod.ID.min = Integer.valueOf(parent.min.getText());
                else       parent.mod.ID.max = Integer.valueOf(parent.max.getText());
//                parent.mod.updateMin();
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        
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