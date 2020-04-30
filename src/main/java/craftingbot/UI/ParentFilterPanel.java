/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import craftingbot.Main;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author charl
 */
public class ParentFilterPanel extends JPanel {
    public ParentFilterPanel(Main main, JPanel parent)
    {
        Dimension size = new Dimension((int) (parent.getWidth() * 0.95),(int) (40));
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(50,255,50));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        parent.add(this);
    }
    
    public void addmp(ModifierPanel mp)
    {
        Dimension size = new Dimension((int) (getWidth()),(int) (getHeight() + 40));
        setSize(size);
        add(mp, Box.TOP_ALIGNMENT);
    }
}
