package crafting.UI;

import crafting.Main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class LogicGroupComboBox extends JComboBox
{
    DefaultComboBoxModel model;
    public FilterTypePanel parent;
    
    public LogicGroupComboBox(FilterTypePanel parent, String selected)
    {
        String[] options = new String[] {
            "And",
            "Not",
            "Count"
        };
        model = new DefaultComboBoxModel();
        for (String s : options) model.addElement(s);
        setModel(model);
        
        setEditable(true);
        setRenderer(new LogicGroupComboBoxRenderer(this));
        setEditor(new LogicGroupComboBoxEditor());
        setSelectedItem(selected);
        
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMaximumSize(new Dimension(40, parent.getHeight()));
        setMinimumSize(new Dimension(40, parent.getHeight()));
        
        this.setBackground(new Color(50,50,50));
        this.setForeground(Color.white);
//        this.
        
        // Code to disable/hide arrow button in dropdown from http://www.java2s.com/Tutorials/Java/Swing_How_to/JComboBox/Hide_arrow_button_from_JComboBox.htm
        UIManager.put("ComboBox.squareButton", Boolean.FALSE);
        setUI(new BasicComboBoxUI() 
        {
            @Override
            protected JButton createArrowButton()
            {
                JButton b = new JButton();
                b.setBorder(null);
                b.setVisible(false);
                return b;
            }
        });
        // End code
        
        this.parent = parent;
        setFont(Main.mainFrame.getNewFont(12));
        
        this.getEditor().getEditorComponent().addFocusListener(new LogicGroupClickListenerSJB(this));
        this.addItemListener(new LogicGroupSelectionListener(this));
    }
}

class LogicGroupComboBoxRenderer extends JLabel implements ListCellRenderer {

    LogicGroupComboBox owner;
    
    public LogicGroupComboBoxRenderer(LogicGroupComboBox owner) {
        this.owner = owner;
//        setOpaque(true);
//        setForeground(Color.white);
//        setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 0));
//        setFont(Main.mainFrame.getNewFont(12));
        setOpaque(true);        
        setForeground(Color.white);
        setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 0));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
//        String text = genHTMLString(value.toString(), owner.entry);
//        setText(value.toString());
        if (value == null)
            setText("null");
        else
            setText(value.toString());
        
        if (isSelected)
        {
            setBackground(new Color(70,82,96));
        }
        else
        {
            setBackground(new Color(50,50,50));
        }
        
        return this;
    }
}

class LogicGroupComboBoxEditor extends BasicComboBoxEditor {
    private JLabel label = new JLabel();
    private Object selectedItem;
     
    public LogicGroupComboBoxEditor() {
         
        label.setOpaque(false);
        label.setFont(Main.mainFrame.getNewFont(12));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }
     
    @Override
    public Component getEditorComponent() {
        return this.label;
    }
     
    @Override
    public Object getItem() {
        return this.selectedItem != null ? this.selectedItem.toString() : null;
    }
     
    @Override
    public void setItem(Object item) {
        this.selectedItem = item;
        if (item != null)
            label.setText(item.toString());
    }
}

class LogicGroupClickListenerSJB implements FocusListener
{
    LogicGroupComboBox owner;
    
    public LogicGroupClickListenerSJB(LogicGroupComboBox owner)
    {
        this.owner = owner;
    }

    @Override
    public void focusGained(FocusEvent e)
    {
    }

    @Override
    public void focusLost(FocusEvent e)
    {
    }
}

class LogicGroupSelectionListener implements ItemListener
{
    
    LogicGroupComboBox owner;
    
    public LogicGroupSelectionListener(LogicGroupComboBox owner)
    {
        this.owner = owner;
    }
    
    public void itemStateChanged(ItemEvent event)
    {
        String selected = owner.getSelectedItem().toString();
        owner.parent.logicGroupChanged(selected);
    }
}
