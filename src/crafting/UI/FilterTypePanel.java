package crafting.UI;

import crafting.filters.Subfilter;
import crafting.filters.Filter;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import java.io.File;
import crafting.filtertypes.FilterBase;
import java.util.ArrayList;
import crafting.filtertypes.Mod;
import crafting.filtertypes.logicgroups.*;
import crafting.itemconfig.InfluenceConfig;
import poeitem.bases.BaseItem;
import poeitem.Modifier;
import crafting.persistence.FilterPersistence;

public class FilterTypePanel extends JPanel {
    
    public static String[] types = new String[] {"And", "Not", "Count"};
    
    public static ArrayList<FilterTypePanel> filtertypepanels = new ArrayList<FilterTypePanel>();
    
    public ArrayList<ModifierPanel> modifierpanels = new ArrayList<>();
    
    public String type;
    public String resourcePath;
    public static Frame frame;
    public JPanel parent;
    
    public LogicGroupComboBox typebox;
    public NumLabel numlabel;
    public DropdownButton dropdown;
    public MinMax min;
    public MinMax max;
    public AddButton addbutton;
    public boolean minMaxEnabled = false;
    
    public FilterBase filterbase;
    public Subfilter filter;
    public int index;
    
    public FilterTypePanel(Frame frame, JPanel parent, FilterBase filterbase, Subfilter filter, int index)
    {
        this.filterbase = filterbase;
        this.filter = filter;
        this.index = index;
        this.filter.active = true;
        this.parent = parent;
        
        this.type = filterbase.getClass().getSimpleName();
        
        String path = "src/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        this.frame = frame;
        
        Dimension size = new Dimension((int) (parent.getWidth()),(int) (40));
        setSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        setBackground(new Color(50,50,50));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                
        CloseFBButton closeButton = new CloseFBButton(this);
        typebox = new LogicGroupComboBox(this, type);
        numlabel = new NumLabel(this);
        dropdown = new DropdownButton(this);
        min = new MinMax(this, "min", "min");
        max = new MinMax(this, "max", "min");
        addbutton = new AddButton(this);
        
        add(closeButton, Box.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(15,0)), Box.LEFT_ALIGNMENT);
        add(typebox, Box.LEFT_ALIGNMENT);
        add(Box.createHorizontalGlue());
        add(numlabel, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(9,0)), Box.RIGHT_ALIGNMENT);
        add(min, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(9,0)), Box.RIGHT_ALIGNMENT);
        add(max, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(9,0)), Box.RIGHT_ALIGNMENT);
        add(addbutton, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(9,0)), Box.RIGHT_ALIGNMENT);
        add(dropdown, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(4,0)), Box.RIGHT_ALIGNMENT);
        addRemMinMax();
        
        if (type.equals("Count"))
        {
            Count c = (Count) this.filterbase;
            if (c.neededMin != -100000)
            {
                min.setText(String.valueOf(c.neededMin));
                min.setForeground(new Color(255,255,255));
            }
            if (c.neededMax != 100000)
            {
                max.setText(String.valueOf(c.neededMax));
                max.setForeground(new Color(255,255,255));
            }
        }
        
        filtertypepanels.add(this);
        
        parent.add(this);
        
        for (int i=0; i<filterbase.mods.size(); i++)
        {
            Mod m = filterbase.mods.get(i);
            
            ModifierPanel mp;
            boolean bypass;
            if (m.assocModifierPanel == null) {
                mp = new ModifierPanel(frame, this, filterbase, m, null);
                bypass = true;
            }
            else {
                mp = m.assocModifierPanel;
                bypass = false;
            }
            
            modifierpanels.add(mp);
            parent.add(mp);
            mp.setVisible(true);
            
            if (!bypass)
            {
                mp.mcb.update(mp.assocMod, true);
                
                Modifier[] types;
        
                if (Filter.singleton.SelectedBase == null) {
                    types = ModifierComboBox.toArr(Modifier.AllExplicitModifiers);
                }
                else {
                    types = ModifierComboBox.toArr(Modifier.getAllApplicableModifiers(Filter.singleton.SelectedBase, InfluenceConfig.getArrOfSelectedInflu()));
                }
                
                if (mp.mcb.getSelectedItem() instanceof Modifier)
                {
                    Modifier selItem = (Modifier) mp.mcb.getSelectedItem();
                    mp.mcb.setModel(new DefaultComboBoxModel<>(types));
                    mp.mcb.defaultmodel = mp.mcb.getModel();
                    
                    Modifier[] validModifiersArr = mp.mcb.getCompatObjects();
                    ArrayList<Modifier> validModifiers = new ArrayList<>();
                    for (Modifier modi : validModifiersArr) validModifiers.add(modi);
                    if (validModifiers.contains(selItem))
                    {
                        mp.mcb.setSelectedItem(selItem);
                        mp.showTierComboBox(selItem);
                    }
                    else
                    {
                        System.out.println("Valid modifiers didn't contain it");
                        setDefault(mp);
                    }
                }
                else
                {
                    String selItem = (String) mp.mcb.getSelectedItem();
                    mp.mcb.setModel(new DefaultComboBoxModel<>(types));
                    mp.mcb.defaultmodel = mp.mcb.getModel();
                    mp.mcb.setSelectedItem(selItem);
                }
                
                if (mp.assocMod == null)
                {
                    setDefault(mp);
                }
            }
            
            Dimension mpsize = new Dimension((int) (getWidth() * 0.95),(int) (40)); // 0.912
            mp.setSize(mpsize);
            mp.setMaximumSize(mpsize);
            mp.setPreferredSize(mpsize);
            mp.updateMCBSize();
        }
        
        if (!filterbase.UIVisible)
        {
            for (int i=0; i<modifierpanels.size(); i++)
            {
                modifierpanels.get(i).setVisible(false);
            }
        }
    }
    
    private void setDefault(ModifierPanel mp)
    {
        mp.assocMod = null;
        mp.mod.assocModifier = null;
        mp.mod.assocModifierTier = null;
        mp.mcb.entry = "";
        mp.mcb.setSelectedIndex(-1);
        mp.hideTierComboBox();
        ((JTextField)mp.mcb.getEditor().getEditorComponent()).setText("New Modifier");
        ((JTextField)mp.mcb.getEditor().getEditorComponent()).setForeground(new Color(238,99,90));
    }
    
    public static void clear(boolean clearLists)
    {
        for (int i = 0; i < filtertypepanels.size(); i++)
        {
            filtertypepanels.get(i).setVisible(false);
            
            for (int j = 0; j < filtertypepanels.get(i).modifierpanels.size(); j++)
            {
                filtertypepanels.get(i).modifierpanels.get(j).setVisible(false); // todo fix this. it can't clear them.
            }
        }
        
        if (clearLists)
        {
            for (int i = 0; i < filtertypepanels.size(); i++)
            {            
                for (int j = 0; j < filtertypepanels.get(i).modifierpanels.size(); j++)
                {
                    filtertypepanels.get(i).modifierpanels.clear();
                }
            }
            
            filtertypepanels.clear();
        }
    }
    
    public void addRemMinMax()
    {
        if (type.equals("Count"))
        {
            min.setInView(true);
            max.setInView(true);
            minMaxEnabled = true;
        }
        else
        {
            min.setInView(false);
            max.setInView(false);
            min.setText("");
            max.setText("");
            minMaxEnabled = false;
        }
        typebox.setPreferredSize(new Dimension((int) (getWidth() * 0.4),(int) ((32))));
    }
    
    public void remove()
    {
        if (filtertypepanels.size() >= 1)
        {
            for (int i=0; i<modifierpanels.size(); i++)
            {
                modifierpanels.get(i).setVisible(false);
            }
            modifierpanels.clear();
            this.setVisible(false);
            
            filtertypepanels.remove(this);
            filter.filters.remove(filterbase);

            FilterPersistence.saveFilters();
        }
    }
    
    public static void reshow()
    {
        if (!FilterTypePanel.filtertypepanels.isEmpty()) {
            Subfilter f = FilterTypePanel.filtertypepanels.get(0).filter;
            frame.genFilterPanel(f);
        }
        Frame.mainFrame.validate();
        
        FilterPersistence.saveFilters();
    }
    
    public void logicGroupChanged(String selected)
    {
        if (selected != null && !selected.equals(type))
        {
            Filter.print();
            type = selected;
            addRemMinMax();
            
            Mod[] modsToAdd = new Mod[filterbase.mods.size()];
            for (int i=0; i<modsToAdd.length; i++)
            {
                modsToAdd[i] = filterbase.mods.get(i);
            }
            
            switch (selected)
            {
                case "And":
                    filterbase = new And(modsToAdd);
                    break;
                case "Not":
                    filterbase = new Not(modsToAdd);
                    break;
                case "Count":
                    filterbase = new Count(-100000, 100000, modsToAdd);
                    break;
            }
            
            filter.filters.set(index, filterbase);
            for (ModifierPanel mp : this.modifierpanels) mp.filterbase = filterbase;
            FilterPersistence.saveFilters();
            Filter.print();
        }        
    }
    
    protected void hideMods()
    {
        for (ModifierPanel m : modifierpanels)
        {
            m.setVisible(false);
        }
    }
    
    protected void showMods()
    {
        for (ModifierPanel m : modifierpanels)
        {
            m.setVisible(true);
        }
    }
}

class AddButton extends JButton {
    public AddButton(FilterTypePanel parent)
    {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setMaximumSize(new Dimension(40,40));
        setMinimumSize(new Dimension(40,40));
        setPreferredSize(new Dimension(40,40));
        setBackground(new Color(50,50,50));
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/plusbuttontransparentsmall.png"))); // NOI18N
        setToolTipText("New modifier");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(50,50,50)));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ModifierPanel mp = new ModifierPanel(parent.frame, parent, parent.filterbase, null, null);
                parent.modifierpanels.add(mp);
                parent.parent.add(mp);
                parent.parent.requestFocusInWindow();
                parent.dropdown.open();
                FilterTypePanel.reshow();
                parent.numlabel.update();
            }
        });
    }
}

class CloseFBButton extends JButton {
    public CloseFBButton(FilterTypePanel parent)
    {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setMaximumSize(new Dimension(40,40));
        setMinimumSize(new Dimension(40,40));
        setPreferredSize(new Dimension(40,40));
        setBackground(new Color(50,50,50));
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/xbuttontransparentsmall.png"))); // NOI18N
        setToolTipText("Remove this logic filter");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(50,50,50)));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.parent.requestFocusInWindow();
                parent.remove();
                parent.numlabel.update();
                FilterPersistence.saveFilters();
            }
        };
        addActionListener(actionListener);
    }
}

class NumLabel extends JLabel {
    private FilterTypePanel parent;
    
    public NumLabel(FilterTypePanel parent)
    {
        this.parent = parent;
        
        setText(String.valueOf(parent.filterbase.mods.size()));
        setFont(parent.frame.getNewFont(14));
        setForeground(new Color(200,200,200));
    }
    
    public void update()
    {
        setText(String.valueOf(parent.filterbase.mods.size()));
    }
}

class DropdownButton extends JButton {
    
    private FilterTypePanel parent;
    
    public DropdownButton(FilterTypePanel parent)
    {
        this.parent = parent;
        
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setMaximumSize(new Dimension(40,40));
        setMinimumSize(new Dimension(40,40));
        setPreferredSize(new Dimension(40,40));
        setBackground(new Color(50,50,50));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        act();
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(50,50,50)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateVisibility();
            }
        };
        addActionListener(actionListener);
    }
    
    public void act()
    {
        if (parent.filterbase.UIVisible)
        {
            setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/closedropdowntransparentsmall.png"))); // NOI18N
            setToolTipText("Hide mods");
        }
        else
        {
            setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/opendropdowntransparentsmall.png"))); // NOI18N
            setToolTipText("Show mods");
        }
    }
    
    public void open()
    {
        parent.filterbase.UIVisible = true;
        parent.showMods();
        setToolTipText("Hide mods");
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/closedropdowntransparentsmall.png"))); // NOI18N
    }
    
    public void close()
    {
        parent.filterbase.UIVisible = false;
        parent.hideMods();
        setToolTipText("Show mods");
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/opendropdowntransparentsmall.png"))); // NOI18N
    }
    
    public void updateVisibility()
    {
        if (parent.filterbase.UIVisible)
            close();
        else
            open();
    }
}

class MinMax extends JTextField {
    
    public String placeholder;
    public FilterTypePanel parent;
    
    public MinMax(FilterTypePanel parent, String placeholder, String placeholderName)
    {   
        if (placeholder.contains("100000"))
        {
            placeholder = placeholderName;
        }
        this.placeholder = placeholder;
        this.parent = parent;
        
        setText(placeholder);
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(0,0,0));
        setForeground(new Color(120,120,120));
        setVisible(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.07),parent.getHeight()));
        setMinimumSize(new Dimension((int) (parent.getWidth() * 0.07),parent.getHeight()));
        setMaximumSize(new Dimension((int) (parent.getWidth() * 0.07),parent.getHeight()));
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e)
            {
                focusGain();
            }
            @Override
            public void focusLost(FocusEvent e)
            {
                focusLoss();
            }
        });
        
        addKeyListener(new NumFieldKeyListener());
    }
    
    @Override
    public void paste() {}
    
    protected void focusGain()
    {
        if (getText().equals(placeholder)) {
            setText("");
            setForeground(new Color(255,255,255));
        }
    }
    
    protected void focusLoss()
    {
        if (getText().isEmpty()) {
            setText(placeholder);
            setForeground(new Color(120,120,120));
        }

        if (this.parent.filterbase.getClass().getSimpleName().equals("Count"))
        {
            Count c = (Count) parent.filterbase;
            if (!parent.min.getText().isEmpty() && !parent.min.getText().equals("min"))
                c.neededMin = Integer.valueOf(parent.min.getText());
            else
                c.neededMin = -100000;
            if (!parent.max.getText().isEmpty() && !parent.max.getText().equals("max"))
                c.neededMax = Integer.valueOf(parent.max.getText());
            else
                c.neededMax = 100000;
            parent.filterbase = c;
            FilterPersistence.saveFilters();
        }
    }
    
    public void setInView(boolean show)
    {
        setForeground(new Color(120,120,120));
        this.setText(placeholder);
        this.setVisible(show);
    }
}
