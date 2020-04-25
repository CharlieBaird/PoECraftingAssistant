/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

/**
 *
 * @author charl
 */
public class SearchBox extends JComboBox
{
    public SearchBox(Object[] types)
    {
        super(types);
        setEditable(true);
        
        AutoCompletion.enable(this);
    }
}