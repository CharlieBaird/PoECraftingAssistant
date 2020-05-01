/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.UI;

import craftingbot.Modifier;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author charl
 */
public class SearchBox extends JComboBox
{
    ComboBoxModel defaultmodel;
    
    public String entry = "";
    
    public SearchBox(Object[] types)
    {
        super(types);
        this.setSelectedIndex(-1);
        setEditable(true);
        
        defaultmodel = this.getModel();
        this.setSelectedIndex(-1);
        
        this.getEditor().getEditorComponent().addKeyListener(new KeyTypedListener(this));
        this.setSelectedIndex(-1);
//        showPopup();
    }
    
    public void updateList()
    {
//        System.out.println(entry);
        
        String[] compat = getCompatObjects();
        DefaultComboBoxModel model = new DefaultComboBoxModel(compat);
        this.setModel(model);
                
        this.setSelectedItem(entry);
        showPopup();
    }
    
    private String[] getCompatObjects()
    {
        ArrayList<Object> os = new ArrayList<Object>();
        for (int i=0; i<defaultmodel.getSize(); i++)
        {
            Object o = defaultmodel.getElementAt(i);
            if (containsIgnoreCase(o.toString(), entry))
                os.add(o);
        }
        
        String[] objects = new String[os.size()];
        for (int i=0; i<os.size(); i++)
            objects[i] = os.get(i).toString();
        
        return objects;
    }
    
            
    // checks if str1 starts with str2 - ignores case
    private boolean containsIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().contains(str2.toUpperCase());
    }
    
    public static String[] toArr(ArrayList<Modifier> typesList)
    {
        String[] types = new String[typesList.size()];
        
        for (int i=0; i<types.length; i++)
            types[i] = typesList.get(i).getStr();
        
        return types;
    }
}




class KeyTypedListener implements KeyListener
{
    SearchBox owner;
    
    public KeyTypedListener(SearchBox owner)
    {
        this.owner = owner;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
//            if (owner.entry.length() >= 1)
//            {
//                owner.entry = owner.entry.substring(0,owner.entry.length() - 1);
//                
//            }
        
//        owner.updateList();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        owner.entry = owner.getEditor().getItem().toString();    
        owner.updateList();
    }

}