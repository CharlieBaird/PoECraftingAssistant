/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author charl
 */
public class FilterPanel extends JPanel {
    public FilterPanel(JPanel parent)
    {
        Dimension size = new Dimension(60,60);
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(255,255,255));
        parent.add(this);
    }
}
