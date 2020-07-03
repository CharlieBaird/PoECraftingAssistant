package crafting.UI;

import crafting.Filters;
import crafting.Main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import poeitem.BaseItem;
import poeitem.Modifier;

public class ModifierComboBox extends JComboBox
{
    ComboBoxModel defaultmodel;
    public ModifierPanel parent;
    
    public String entry = "";
    public int caretPos = -1;
    
    boolean allSelected = false;
    
    public ModifierComboBox(ModifierPanel parent, Object[] types)
    {
        super(types);
        
        setRenderer(new ModifierComboBoxRenderer());
        setEditor(new ModifierComboBoxEditor());
        setBackground(new Color(60,60,60));
        setForeground(new Color(60,60,60));
        
        // Code to disable/hide arrow button in dropdown from http://www.java2s.com/Tutorials/Java/Swing_How_to/JComboBox/Hide_arrow_button_from_JComboBox.htm
        UIManager.put("ComboBox.squareButton", Boolean.FALSE);
            setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton()
            {
                JButton b = new JButton();
                b.setBorder(BorderFactory.createEmptyBorder());
                b.setVisible(false);
                return b;
            }
        });
        // End code
        
        this.setSelectedIndex(-1);
        setEditable(true);
        
        this.parent = parent;
        defaultmodel = this.getModel();
        
        this.setSelectedIndex(0);
        
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
        entry = ((JTextField) this.getEditor().getEditorComponent()).getText();
        
        String[] compat = getCompatObjects();
        DefaultComboBoxModel model = new DefaultComboBoxModel(compat);
        this.setModel(model);
                
        this.setSelectedItem(entry);
        if (caretPos != -1)
            ((JTextField)this.editor.getEditorComponent()).setCaretPosition(this.caretPos);
        
        if (compat.length >= 1)
            showPopup();
        else
            hidePopup();
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
        }
        else if (m == null)
        {
            parent.assocMod = null;
            parent.mod.name = null;
            parent.mod.assocModifier = parent.assocMod;
            parent.hideTierComboBox();
            if (updateTierViews) ModifierPanel.updateTierViews();
        }
    }
}

class ModifierComboBoxRenderer extends JLabel implements ListCellRenderer {

    public ModifierComboBoxRenderer() {
        setOpaque(true);
        setBackground(new Color(60,60,60));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 0));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        
        if (isSelected)
        {
            setBackground(new Color(70,82,96));
        }
        else
        {
            setBackground(new Color(60,60,60));
        }
        
        return this;
    }
}

class ModifierComboBoxEditor extends BasicComboBoxEditor {
    private JTextField label = new JTextField();
    private Object selectedItem;
     
    public ModifierComboBoxEditor() {
         
        label.setOpaque(false);
        label.setFont(Main.mainFrame.getNewFont(12));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(60,60,60));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        label.setEditable(true);
    }
     
    public Component getEditorComponent() {
        return this.label;
    }
     
    public Object getItem() {
        return this.selectedItem != null ? this.selectedItem.toString() : null;
    }
     
    public void setItem(Object item) {
        this.selectedItem = item;
        if (item != null)
            label.setText(item.toString());
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
        // Detect backspace key, to update every time even when held down
        if (e.getKeyCode() != 17)
            ((JTextField)owner.getEditor().getEditorComponent()).setForeground(new Color(255,255,255));
        
        if (e.getKeyCode() == 8)
        {
            JTextField editor = (JTextField) owner.getEditor().getEditorComponent();
            if (e.isControlDown() || owner.allSelected)
            {
                editor.setText("");
            }
            owner.caretPos = editor.getCaretPosition();
            owner.updateList();
        }
        owner.allSelected = false;
        ctrlWasPressed = false;
    }
    
    boolean ctrlWasPressed = false;
    
    // Logic of search box for keys, shortcuts, etc.
    @Override
    public void keyReleased(KeyEvent e)
    {
        
        if (e.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            ctrlWasPressed = true;
            return;
        }
        
        // Enter key
        if (e.getKeyCode() == 10)
        {
            e.consume();
            Main.mainFrame.requestFocusInWindow();
        }
        
        else if (e.isControlDown())
        {
            if (e.getKeyCode() == KeyEvent.VK_A)
            {
                owner.allSelected = true;
            }
            e.consume();
        }
        
        // Up / Down arrow for selecting should not update the list.        
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)
        {
            e.consume();
        }
        
        // Anything else, normal key
        else
        {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_SHIFT) 
            {
                return;
            }
            JTextField editor = (JTextField) owner.getEditor().getEditorComponent();
    //            owner.entry = owner.getEditor().getItem().toString();
            owner.caretPos = editor.getCaretPosition();
            
            if (!ctrlWasPressed)
                owner.updateList(); 
        }
        
        if (ctrlWasPressed && e.getKeyCode() == KeyEvent.VK_A)
        {
            owner.allSelected = true;
            ctrlWasPressed = false;
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
    public void focusGained(FocusEvent e)
    {
        if (((JTextField) owner.getEditor().getEditorComponent()).getText().equals("New Modifier"))
        {
            ((JTextField) owner.getEditor().getEditorComponent()).setText("");
        }
        owner.caretPos = ((JTextField) owner.getEditor().getEditorComponent()).getCaretPosition();
        
        owner.updateList();
        if (owner.getModel().getSize() >= 1)
            owner.showPopup();
        
        owner.allSelected = false;
       ((JTextField) owner.getEditor().getEditorComponent()).setFont(Main.mainFrame.getNewFont(12));
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        owner.allSelected = false;
        ((JTextField) owner.getEditor().getEditorComponent()).setFont(Main.mainFrame.getNewFont(12));
       
        String content = ((JTextField) owner.getEditor().getEditorComponent()).getText();
        
        for (int i = 0; i < owner.defaultmodel.getSize(); i++) {
            String str = (String) owner.defaultmodel.getElementAt(i);
            if (content.equals(str))
            {
                Modifier m;
                if (Filters.singleton.SelectedBase != null)
                {
                    m = BaseItem.getFromBase(Filters.singleton.SelectedBase).getExplicitFromStr(str);
                }
                else
                {
                    m = Modifier.getExplicitFromStr(str);
                }

                owner.update(m, true);
                Filters.saveFilters();

                return;
            }

        }
        
        ((JTextField) owner.getEditor().getEditorComponent()).setText("New Modifier");
        ((JTextField)owner.getEditor().getEditorComponent()).setForeground(new Color(238,99,90));
        owner.entry = "";
        owner.update(null, true);
        Filters.saveFilters();
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
        String selected = (String) owner.getSelectedItem();
        ((JTextField)owner.getEditor().getEditorComponent()).setForeground(new Color(255,255,255));
        
        if (event.getStateChange() == ItemEvent.SELECTED)
        {
            if (selected != null)
            {
                Modifier m;
                if (Filters.singleton.SelectedBase != null)
                {
                    m = BaseItem.getFromBase(Filters.singleton.SelectedBase).getExplicitFromStr(selected);
                }
                else
                {
                    m = Modifier.getExplicitFromStr(selected);
                }
                
                owner.update(m, true);

                Filters.saveFilters();
            }
        }
        Filters.saveFilters();
        ((JTextField) owner.getEditor().getEditorComponent()).setFont(Main.mainFrame.getNewFont(12));
    }
}
