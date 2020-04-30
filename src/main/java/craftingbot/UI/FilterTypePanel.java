/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import craftingbot.Filter;
import craftingbot.Filters;
import craftingbot.Main;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.io.File;
import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.logicgroups.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import craftingbot.filtertypes.Mod;

/**
 *
 * @author charl
 */
public class FilterTypePanel extends JPanel {
    
    public static String[] types = new String[] {"And", "Not", "Count"};
    
    public static ArrayList<FilterTypePanel> filtertypepanels = new ArrayList<FilterTypePanel>();
    
    public String type;
    public String resourcePath;
    public Main frame;
    
    public TypeLabel typelabel;
    public DropdownButton dropdown;
    public Min min;
    public AddButton addbutton;
    public boolean minMaxEnabled = false;
    
    public FilterBase filterbase;
    public Filter filter;
    public int index;
    
    public FilterTypePanel(Main frame, JPanel parent, FilterBase filterbase, Filter filter, int index)
    {
        this.filterbase = filterbase;
        this.filter = filter;
        this.index = index;
        this.filter.active = true;
        
//        filterbase.print();
        
        this.type = filterbase.getClass().getSimpleName();
        
        String path = "src/main/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        this.frame = frame;
        
        Dimension size = new Dimension((int) (parent.getWidth() * 0.95),(int) (40));
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(50,50,50));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        CloseFBButton closeButton = new CloseFBButton(this);
        typelabel = new TypeLabel(this);
        dropdown = new DropdownButton(this);
        min = new Min(this, " min");
        addbutton = new AddButton(this);
        
        add(closeButton, Box.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(15,0)), Box.LEFT_ALIGNMENT);
        add(typelabel, Box.LEFT_ALIGNMENT);
        add(Box.createHorizontalGlue());
        add(min, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(9,0)), Box.RIGHT_ALIGNMENT);
        add(addbutton, Box.RIGHT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(9,0)), Box.RIGHT_ALIGNMENT);
        add(dropdown, Box.RIGHT_ALIGNMENT);
        addRemMinMax();
        
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLogicFilter();
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
            
        };
        
        if (type.equals("Count"))
        {
            Count c = (Count) this.filterbase;
            min.setText(String.valueOf(c.needed));
            min.setForeground(new Color(255,255,255));
        }
        
        addMouseListener(mouseListener);
        
        filtertypepanels.add(this);
        
        parent.add(this);
        
        for (Mod m: filterbase.mods)
        {
            ModifierPanel mp = new ModifierPanel(frame, this, m);
            parent.add(mp);
        }
    }
    
    public static void clear()
    {
        for (int i = 0; i < filtertypepanels.size(); i++)
        {
            filtertypepanels.get(i).setVisible(false);
        }
        
        filtertypepanels.clear();
    }
    
    public void addRemMinMax()
    {
        if (type.equals("Count"))
        {
            min.setInView(true);
            minMaxEnabled = true;
        }
        else
        {
            min.setInView(false);
            min.setText("");
            minMaxEnabled = false;
        }
        typelabel.setPreferredSize(new Dimension((int) (getWidth() * 0.5),(int) ((32))));
    }
    
    public void remove()
    {
        if (filtertypepanels.size() >= 2)
        {
            filtertypepanels.remove(this);
            Filters.print();
            this.setVisible(false);
            filter.filters.remove(filterbase);
            Filters.print();
        }
    }
    
    public void showDropdown()
    {
        
    }
    
    private int getIndex()
    {
        switch (type)
        {
            case "And":
                return 0;
            case "Not":
                return 1;
            case "Count":
                return 2;
            default:
                return -1;
        }
    }
    
    public void selectLogicFilter()
    {
        SearchBox sb = new SearchBox(types);
        
        JOptionPane jop = new JOptionPane();
        sb.setSelectedIndex(getIndex());        
        System.out.println(getIndex() + " is the getIndex()");

        jop.showMessageDialog(this, sb, "CraftingBot", JOptionPane.PLAIN_MESSAGE, null);
        
        String selected = sb.getSelectedItem().toString();
        
        if (selected != null && !selected.equals(type))
        {
            type = selected;
            typelabel.setText(type);
            addRemMinMax();
            
            switch (selected)
            {
                case "And":
                    filterbase = new And(filterbase.mods);
                    break;
                case "Not":
                    filterbase = new Not(filterbase.mods);
                    break;
                case "Count":
                    filterbase = new Count(1, filterbase.mods);
                    break;
            }
            
            filter.filters.set(index, filterbase);
        }
        
//        filterbase.print();
    }
}

class AddButton extends JButton {
    public AddButton(FilterTypePanel parent)
    {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.09),(int) ((32))));
        setBackground(new Color(0,0,0));
        setIcon(new javax.swing.ImageIcon(parent.resourcePath + "/plusbuttontransparentsmall.png")); // NOI18N
        setToolTipText("New modifier");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(0,0,0)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.filterbase.print();
            }
        };
        addActionListener(actionListener);
    }
}

class CloseFBButton extends JButton {
    public CloseFBButton(FilterTypePanel parent)
    {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.09),(int) ((32))));
        setBackground(new Color(0,0,0));
        setIcon(new javax.swing.ImageIcon(parent.resourcePath + "/xbuttontransparentsmall.png")); // NOI18N
        setToolTipText("Remove this logic filter");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(0,0,0)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.remove();
            }
        };
        addActionListener(actionListener);
    }
}

class TypeLabel extends JLabel {
    private FilterTypePanel parent;
    
    public TypeLabel(FilterTypePanel parent)
    {
        this.parent = parent;
        
        setText(parent.type);
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(255,0,0));
        setForeground(new Color(255,255,255));
    }
}

class DropdownButton extends JButton {
    public DropdownButton(FilterTypePanel parent)
    {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.09),(int) ((32))));
        setBackground(new Color(0,0,0));
        setIcon(new javax.swing.ImageIcon(parent.resourcePath + "/opendropdowntransparentsmall.png")); // NOI18N
        setToolTipText("Select logic type");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(0,0,0)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
//                parent.filterbase.print();
//                parent.showDropdown();
            }
        };
        addActionListener(actionListener);
    }
}

class Min extends JTextField {
    public String placeholder;
    
    public Min(FilterTypePanel parent, String placeholder)
    {
        this.placeholder = placeholder;
        setText(placeholder);
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(0,0,0));
        setForeground(new Color(120,120,120));
        setVisible(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(new Color(255,255,255));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(new Color(120,120,120));
                }
                
                if (parent.filterbase.getClass().getSimpleName().equals("Count"))
                {
                    Count c = (Count) parent.filterbase;
                    c.needed = Integer.valueOf(getText());
                    parent.filterbase = c;
                }
            }
        });
                
        KeyListener keyListener = new KeyListener()
        {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10)
                {
                    parent.requestFocusInWindow();
                    if (parent.filterbase.getClass().getSimpleName().equals("Count"))
                    {
                        Count c = (Count) parent.filterbase;
                        c.needed = Integer.valueOf(getText());
                        parent.filterbase = c;
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        };
        
        addKeyListener(keyListener);
    }
    
    public void setInView(boolean show)
    {
        setForeground(new Color(120,120,120));
        this.setText(placeholder);
        this.setVisible(show);
    }
}
