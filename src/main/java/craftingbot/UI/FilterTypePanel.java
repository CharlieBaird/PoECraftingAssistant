/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import craftingbot.Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;

/**
 *
 * @author charl
 */
public class FilterTypePanel extends JPanel {
    
    public static String[] types = new String[] {"And", "Not", "Count", "Weighted Sum"};
    
    public String type;
    public String resourcePath;
    public Main frame;
    
    public FilterTypePanel(Main frame, JPanel parent)
    {
        this.type = "And";
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
        
        TypeLabel typelabel = new TypeLabel(this);
        add(typelabel);
        DropdownButton dropdown = new DropdownButton(this);
        add(dropdown);
        
        parent.add(this);
        frame.pack();
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
