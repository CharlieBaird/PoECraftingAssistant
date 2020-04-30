/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import craftingbot.Main;
import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.Mod;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author charl
 */
public class ModifierPanel extends JPanel {
    public ModifierPanel(Main frame, JPanel parent, FilterBase fb, Mod mod)
    {
        Dimension size = new Dimension((int) (parent.getWidth() * 0.95),(int) (40));
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(60,60,60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        parent.add(this);
    }
}
