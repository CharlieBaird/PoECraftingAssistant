/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import craftingbot.Filters;
import craftingbot.Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import craftingbot.Filter;
import java.util.ArrayList;
import java.io.File;

public class FilterPanel extends JPanel {
    
    public static ArrayList<FilterPanel> filterpanels = new ArrayList<FilterPanel>();
    public String name = null;
    public String savedname = null;
    public Filter filter;
    public JPanel parent;
    public String resourcePath;
    
    public FilterPanel(Main frame, JPanel parent, Filter filter)
    {
        
        String path = "src/main/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        
        this.name = filter.name;
        this.savedname = filter.name;
        this.filter = filter;
        this.parent = parent;
        
        Dimension size = new Dimension((int)(parent.getWidth() * 0.97),40);
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        CloseButton cb = new CloseButton(this, size);
        FilterTextField ftf = new FilterTextField(this, size, name, savedname);
        OpenButton ob = new OpenButton(this,size);
        
        add(cb);
        add(ftf);
        add(ob);

        
        parent.add(this);
        filterpanels.add(this);
        
//        Dimension size2 = new Dimension(316,300);
//        parent.setSize(size2);
//        frame.pack();
//        System.out.print(" --> " + parent.getWidth() + "\n");
//        System.out.println(getWidth());
//        System.out.println(ftf.getWidth());
//        System.out.println(cb.getSize());

    }
    
    public void remove()
    {
//        System.out.println("Clicked " + name);
        setVisible(false);
//        System.out.println("remove " + name);
        Filters.singleton.remove(name);
        parent.requestFocus();
        try {
            Filters.saveFilters();
        } catch (IOException ex) {
            Logger.getLogger(FilterPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//    class DropdownElement extends JPanel implements MouseListener
//    {
//        public DropdownElement(JPanel parent)
//        {
//            Dimension size = new Dimension(parent.getSize());
//            
//            setSize(size);
//            setPreferredSize(size);
//            setBackground(new Color(40,40,40));
//            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            
//            JLabel label
//        }
//    }
}

class BackgroundListener implements MouseListener
{
    public JComponent object;
    private Color inside;
    private Color away;
    
    public BackgroundListener(JComponent object, Color inside, Color away)
    {
        this.object = object;
        this.inside = inside;
        this.away = away;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        object.setBackground(inside);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        object.setBackground(away);
    }    
}

class FilterTextField extends JTextField
{
    public FilterTextField(FilterPanel parent, Dimension size, String name, String savedname)
    {
        Dimension d = new Dimension((int)(size.width*0.6), size.height);
        setBackground(new Color(40,40,40));
        setForeground(new Color(255,255,255));
        setPreferredSize(d);
        setText(name);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        KeyListener keyListener = new KeyListener()
        {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10)
                {
                    parent.requestFocusInWindow();
                    Filters.singleton.rename(savedname, getText());
                    try {
                        Filters.saveFilters();
                    } catch (IOException ex) {
                        Logger.getLogger(FilterPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    Filters.deleteFilters(savedname);
                    parent.savedname = getText();
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
}

class CloseButton extends JButton
{
    public CloseButton(FilterPanel parent, Dimension size)
    {
        Dimension d1 = new Dimension((int)(size.width * 0.15), size.height);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(d1);
        setBackground(new Color(0,0,0));
        setIcon(new javax.swing.ImageIcon(parent.resourcePath + "/xbuttontransparentsmall.png")); // NOI18N
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

class OpenButton extends JButton
{
    public OpenButton(FilterPanel parent, Dimension size)
    {
        Dimension d1 = new Dimension((int)(size.width * 0.15), size.height);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(d1);
        setBackground(new Color(0,0,0));
        setIcon(new javax.swing.ImageIcon(parent.resourcePath + "/arrowbuttontransparentsmall.png")); // NOI18N
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(0,0,0)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.filter.print();
            }
        };
        addActionListener(actionListener);
    }
}
