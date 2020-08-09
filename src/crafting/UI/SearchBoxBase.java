package crafting.UI;

import crafting.Filters;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchBoxBase extends JComboBox {
    
    public ComboBoxModel defaultmodel;
    public JPanel parent;
    public String entry = "";
    public int caretPos = -1;
    
    protected SearchBoxBase(JPanel parent, Object[] types)
    {
        super(types);
        
        this.parent = parent;
        
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
        setEditable(true);
        defaultmodel = this.getModel();
        
        this.setFont(Main.mainFrame.getNewFont(12));
        
        this.getEditor().getEditorComponent().addKeyListener(new IL_KeyTypedListener(this));
        this.getEditor().getEditorComponent().addFocusListener(new IL_ClickListener(this));
        this.addItemListener(new IL_SelectionListener(this));
        
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
    
    private boolean containsIgnoreCase(String str1, String str2)
    {
        return str1.toUpperCase().contains(str2.toUpperCase());
    }
    
    protected void itemUpdate(ItemEvent event) {}
    
    public void reset()
    {
        setModel(defaultmodel);
        setSelectedIndex(-1);
        entry = "";
        Filters.saveFilters();
    }
    
}

class IL_KeyTypedListener implements KeyListener
{
    SearchBoxBase owner;

    public IL_KeyTypedListener(SearchBoxBase owner)
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

class IL_ClickListener implements FocusListener
{
    SearchBoxBase owner;
    
    public IL_ClickListener(SearchBoxBase owner)
    {
        this.owner = owner;
    }

    @Override
    public void focusGained(FocusEvent e) {
        owner.showPopup();
    }

    @Override
    public void focusLost(FocusEvent e) {
        
        String content = ((JTextField) owner.getEditor().getEditorComponent()).getText();
        
        for (int i = 0; i < owner.defaultmodel.getSize(); i++) {
            String m = (String) owner.defaultmodel.getElementAt(i);
            if (content.equals(m))
            {
                Filters.saveFilters();
                return;
            }
        }
        
        owner.reset();
    }
}

class IL_SelectionListener implements ItemListener
{
    
    SearchBoxBase owner;
    
    public IL_SelectionListener(SearchBoxBase owner)
    {
        this.owner = owner;
    }
    
    public void itemStateChanged(ItemEvent event)
    {
       owner.itemUpdate(event);
    }
}