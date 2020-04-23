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

/**
 *
 * @author charl
 */
public class FilterPanel extends JPanel {
    String name = null;
    String savedname = null;
    Filter filter;
    
    public FilterPanel(Main frame, JPanel parent, String name, Filter filter)
    {
        this.name = name;
        this.savedname = name;
        this.filter = filter;
        if (filter != null)
            Filters.singleton.filters.add(filter);
        
        KeyListener keyListener = null;
        ActionListener actionListener = null;
        
        Dimension size = new Dimension((int)(parent.getWidth()*0.97),40);
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        GridBagConstraints gbc = new GridBagConstraints();
        
        JTextField text = new JTextField();
        Dimension d = new Dimension((int)(size.width*0.7), size.height);
        text.setBackground(new Color(40,40,40));
        text.setForeground(new Color(255,255,255));
        text.setPreferredSize(d);
        text.setText(name);
//        JScrollPane scrollPane = new JScrollPane(text);
        add(text);
        
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                System.out.println("remove " + name);
                Filters.singleton.remove(name);
                try {
                    Filters.saveFilters();
                } catch (IOException ex) {
                    Logger.getLogger(FilterPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        keyListener = new KeyListener()
        {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10)
                {
                    requestFocusInWindow();
                    Filters.singleton.rename(savedname, text.getText());
                    try {
                        Filters.saveFilters();
                    } catch (IOException ex) {
                        Logger.getLogger(FilterPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    Filters.deleteFilters(savedname);
                    savedname = text.getText();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        };
        
        text.addKeyListener(keyListener);
        
        JButton button = new JButton();
        Dimension d1 = new Dimension((int)(size.width * 0.15), size.height);
        button.setPreferredSize(d1);
        button.setBackground(new Color(40,40,40));
        button.addActionListener(actionListener);
        add(button);
        
        parent.add(this);
        
        Filters.print();
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
