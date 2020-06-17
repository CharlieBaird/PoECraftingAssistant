/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.UI;

import poeitem.Modifier;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import poeitem.BaseItem;
import poeitem.Modifier.Type;

public class SearchBox extends JComboBox
{
    ComboBoxModel defaultmodel;
    
    public String entry = "";
    public int caretPos = -1;
    
    public SearchBox(Object[] types)
    {
        super(types);
        this.setSelectedIndex(-1);
        setEditable(true);
        
        defaultmodel = this.getModel();
        this.setSelectedIndex(-1);
        
        String maxLength = "a";
        for (int i=0; i<getModel().getSize(); i++)
        {
            String m = (String) getModel().getElementAt(i);
            if (m.length() > maxLength.length())
            {
                maxLength = m;
            }
        }
        setPrototypeDisplayValue(maxLength);
        setMaximumRowCount(20);
        
        this.getEditor().getEditorComponent().addKeyListener(new KeyTypedListener(this));
        this.getEditor().getEditorComponent().addFocusListener(new ClickListener(this));
        
        this.setSelectedIndex(-1);
    }
    
    public void updateList()
    {
        String[] compat = getCompatObjects();
        DefaultComboBoxModel model = new DefaultComboBoxModel(compat);
        this.setModel(model);
                
        this.setSelectedItem(entry);
        if (caretPos != -1)
            ((JTextField)this.editor.getEditorComponent()).setCaretPosition(this.caretPos);
        showPopup();
    }
    
    private String[] getCompatObjects()
    {
        ArrayList<Object> os = new ArrayList<>();
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
    
    private boolean containsIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().contains(str2.toUpperCase());
    }
    
    public static String[] toArr(ArrayList<Modifier> typesList)
    {
        for (int i=0; i<typesList.size(); i++)
        {
            Modifier m = typesList.get(i);
            if (
                    (m.getModGenerationTypeID() != 1
                    && m.getModGenerationTypeID() != 2
                    && m.getModGenerationTypeID() != -2
                    && m.getModGenerationTypeID() != -1
                    && m.getModGenerationTypeID() != 0
                    && m.getModGenerationTypeID() != -3) ||
                    !m.isSearchable()
                )
            {
                typesList.remove(m);
                i--;
                continue;
            }
            
            if (ItemBase.SelectedBase != null && m.getType() == Type.EXPLICIT && (m.getModGenerationTypeID() == 1 || m.getModGenerationTypeID() == 2))
            {
                typesList.clear();
                typesList.addAll(BaseItem.getFromBase(ItemBase.SelectedBase).assocModifiers);
            }
        }
        
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
    }

    // Logic of search box for keys, shortcuts, etc.
    @Override
    public void keyReleased(KeyEvent e)
    {
        // Enter key should select one, hide dropdown, remove focus.
         // Up / Down arrow for selecting should not update the list.
        if (e.getKeyCode() == 10 || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)
        {
            e.consume();
        }
        // Ctrl + A
        // Credit to PR from https://github.com/JamesZoft
        else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A)
        {
            owner.showPopup();
            owner.getEditor().selectAll();
        }
        else 
        {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_SHIFT) 
            {
                return;
            }
            JTextField editor = (JTextField) owner.getEditor().getEditorComponent();
            owner.entry = owner.getEditor().getItem().toString();
            owner.caretPos = editor.getCaretPosition();
            owner.updateList();
        }
    }
}

class ClickListener implements FocusListener
{
    SearchBox owner;
    
    public ClickListener(SearchBox owner)
    {
        this.owner = owner;
    }

    @Override
    public void focusGained(FocusEvent e) {
        owner.showPopup();
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
}