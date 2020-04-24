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
import java.awt.event.ActionEvent;
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

/**
 *
 * @author charl
 */
public class FilterTypePanel extends JPanel {
    
    public static String[] types = new String[] {"And", "Not", "Count"};
    
    public String type;
    public String resourcePath;
    public Main frame;
    public TypeLabel typelabel;
    public DropdownButton dropdown;
    public Min min;
    public boolean minMaxEnabled = false;
    
    public FilterBase filterbase;
    public Filter filter;
    public int index;
    
    public FilterTypePanel(Main frame, JPanel parent, FilterBase filterbase, Filter filter, int index)
    {
        this.filterbase = filterbase;
        this.filter = filter;
        this.index = index;
        
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
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        typelabel = new TypeLabel(this);
        add(typelabel);
        min = new Min(this, " min");
        add(min);
        dropdown = new DropdownButton(this);
        add(dropdown);
        
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
        addMouseListener(mouseListener);
        
        parent.add(this);
        frame.pack();
    }
    
    public void selectLogicFilter()
    {
        String selected = (String)JOptionPane.showInputDialog(
                            this,
                            "Select a logic type",
                            "CraftingBot",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            types,
                            type);
        
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
        
        filterbase.print();
    }
    
    public void addRemMinMax()
    {
        if (type.equals("Count") || type.equals("Weighted Sum"))
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
    }
    
    public void showDropdown()
    {
        
    }
}

class TypeLabel extends JLabel {
    public TypeLabel(FilterTypePanel parent)
    {
        setText(parent.type);
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(0,0,0));
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
                parent.showDropdown();
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
