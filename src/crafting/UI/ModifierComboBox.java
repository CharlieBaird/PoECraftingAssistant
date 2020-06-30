package crafting.UI;

import crafting.Filters;
import crafting.Main;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import poeitem.Modifier;

public class ModifierComboBox extends JComboBox
{
    ComboBoxModel defaultmodel;
    public ModifierPanel parent;
    
    public String entry = "";
    public int caretPos = -1;
    
    public ModifierComboBox(ModifierPanel parent, Object[] types)
    {
        super(types);
        this.setSelectedIndex(-1);
        setEditable(true);
        
        this.parent = parent;
        defaultmodel = this.getModel();
        
        this.setSelectedIndex(0);
        
        this.setFont(Main.mainFrame.getNewFont(12));
        
        String maxLength = "a";
        for (int i=0; i<getModel().getSize(); i++)
        {
            String m = (String) getModel().getElementAt(i);
            if (m.length() > maxLength.length())
            {
                maxLength = m;
            }
        }
        
        this.getEditor().getEditorComponent().addKeyListener(new ModKeyTypedListenerSJB(this));
        this.getEditor().getEditorComponent().addFocusListener(new ModClickListenerSJB(this));
        this.addItemListener(new ModSelectionListener(this));
        
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
    
    protected String[] getCompatObjects()
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
                    m.getModGenerationTypeID() != 1
                    && m.getModGenerationTypeID() != 2
                    && m.getModGenerationTypeID() != -2
                    && m.getModGenerationTypeID() != -1
                    && m.getModGenerationTypeID() != 0
                    && m.getModGenerationTypeID() != -3
                )
            {
                typesList.remove(m);
                i--;
            }
        }
        
        String[] types = new String[typesList.size()];
        
        for (int i=0; i<types.length; i++)
            types[i] = typesList.get(i).getStr();
        
        return types;
    }
    
    public void update(Modifier m, boolean updateTierViews)
    {
        if (m != null && parent.tier != null)
        {
            parent.assocMod = m;
            parent.mod.name = m.getStr();
            parent.mod.assocModifier = parent.assocMod;
            parent.showTierComboBox(m);
            if (updateTierViews) ModifierPanel.updateTierViews();

            ((JTextField) parent.mcb.getEditor().getEditorComponent()).setForeground(new Color(0,0,0));
        }
    }
}

class ModKeyTypedListenerSJB implements KeyListener
{
    ModifierComboBox owner;

    public ModKeyTypedListenerSJB(ModifierComboBox owner)
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
        
        // Up / Down arrow for selecting should not update the list.
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)
        {
            e.consume();
        }
        
        // Enter key
        else if (e.getKeyCode() == 10)
        {
            e.consume();
//            Main.mainFrame.requestFocusInWindow();
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

class ModClickListenerSJB implements FocusListener
{
    ModifierComboBox owner;
    
    public ModClickListenerSJB(ModifierComboBox owner)
    {
        this.owner = owner;
    }

    @Override
    public void focusGained(FocusEvent e) {
        owner.showPopup();
    }

    @Override
    public void focusLost(FocusEvent e)
    {
    }
}

class ModSelectionListener implements ItemListener
{
    
    ModifierComboBox owner;
    
    public ModSelectionListener(ModifierComboBox owner)
    {
        this.owner = owner;
    }
    
    public void itemStateChanged(ItemEvent event)
    {
       if (event.getStateChange() == ItemEvent.SELECTED)
       {
            Object selected = owner.getSelectedItem();
            if (selected != null)
            {
                Modifier m = Modifier.getExplicitFromStr(selected.toString());
                
                owner.update(m, true);

                Filters.saveFilters();
            }
       }
       Filters.saveFilters();
    }
}
