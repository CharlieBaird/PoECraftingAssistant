package crafting.UI;

import crafting.filters.Filter;
import crafting.itemconfig.InfluenceConfig;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import poeitem.bases.Influence;
import poeitem.Modifier;
import crafting.persistence.FilterPersistence;
import javax.swing.Icon;

public class ModifierComboBox extends JComboBox
{
    ComboBoxModel defaultmodel;
    public ModifierPanel parent;
    
    public String entry = "";
    public int caretPos = -1;
    
    boolean allSelected = false;
    
    public ModifierComboBox(ModifierPanel parent, Modifier[] types)
    {
        super(types);
        
        setRenderer(new ModifierComboBoxRenderer(this));
        setEditor(new ModifierComboBoxEditor());
        setBackground(new Color(60,60,60));
        setForeground(new Color(60,60,60));
        
        // Code to disable/hide arrow button in dropdown from http://www.java2s.com/Tutorials/Java/Swing_How_to/JComboBox/Hide_arrow_button_from_JComboBox.htm
        UIManager.put("ComboBox.squareButton", Boolean.FALSE);
        setUI(new BasicComboBoxUI() 
        {
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
        
        setEditable(true);
        
        this.parent = parent;
        defaultmodel = this.getModel();
        
        this.getEditor().getEditorComponent().addKeyListener(new ModKeyTypedListenerSJB(this));
        this.getEditor().getEditorComponent().addFocusListener(new ModClickListenerSJB(this));
        this.addItemListener(new ModSelectionListener(this));
        
        this.setSelectedIndex(-1);

    }
    
    public void updateList()
    {
        entry = ((JTextField) this.getEditor().getEditorComponent()).getText();
        
        Modifier[] compat = getCompatObjects();
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
    
    protected Modifier[] getCompatObjects()
    {
        ArrayList<Modifier> os = new ArrayList<>();
        
        if (Filter.singleton.SelectedBase != null)
        {
            for (int i=0; i<defaultmodel.getSize(); i++)
            {
                Modifier o = (Modifier) defaultmodel.getElementAt(i);
                if (o.isInfluenced())
                {
                    ArrayList<InfluenceConfig> assoc = new ArrayList<>();
                    switch (o.getInfluence())
                    {
                        case SHAPER: assoc.add(Frame.mainFrame.itemConfigPanel.shaper); break;
                        case ELDER: assoc.add(Frame.mainFrame.itemConfigPanel.elder); break;
                        case CRUSADER: assoc.add(Frame.mainFrame.itemConfigPanel.crusader); break;
                        case WARLORD: assoc.add(Frame.mainFrame.itemConfigPanel.warlord); break;
                        case HUNTER: assoc.add(Frame.mainFrame.itemConfigPanel.hunter); break;
                        case REDEEMER: assoc.add(Frame.mainFrame.itemConfigPanel.redeemer); break;
                    }

                    if (assoc.isEmpty()) os.add(o);
                    
                    boolean selectedOne = false;
                    for (InfluenceConfig config : assoc) {
                        if (config.isSelected()) selectedOne = true;
                    }
                    
                    if (!selectedOne) continue;

                }
                if (containsIgnoreCase(o.toString(), entry))
                    os.add(o);
            }
        }
        else
        {
            for (int i=0; i<defaultmodel.getSize(); i++)
            {
                Modifier o = (Modifier) defaultmodel.getElementAt(i);
                if (containsIgnoreCase(o.toString(), entry))
                    os.add(o);
            }
        }
            
        Modifier[] objects = new Modifier[os.size()];
        for (int i=0; i<os.size(); i++)
            objects[i] = os.get(i);
        
        return objects;
    }
    
    private boolean containsIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().contains(str2.toUpperCase());
    }
    
    public static Modifier[] toArr(ArrayList<Modifier> typesList)
    {
        ArrayList<Modifier> dispList = new ArrayList<>();
        
        for (int i=0; i<typesList.size(); i++)
        {
            Modifier m = typesList.get(i);
            dispList.add(m);
        }
        
        Modifier[] types = new Modifier[dispList.size()];
        
        for (int i=0; i<types.length; i++)
            types[i] = dispList.get(i);
        
        return types;
    }
    
    public void update(Modifier m, boolean updateTierViews)
    {
        System.out.println();
        if (m != null)
        {
            System.out.println("1");
            parent.assocMod = m;
//            parent.mod.name = m.getKey(); // adjusted
            parent.mod.assocModifier = parent.assocMod;
//            parent.showTierComboBox(m);
//            if (updateTierViews) ModifierPanel.updateTierViews();
        }
        else if (m == null)
        {
            System.out.println("2");
            parent.assocMod = null;
//            parent.mod.name = null;
            parent.mod.assocModifier = parent.assocMod;
//            parent.hideTierComboBox();
//            if (updateTierViews) ModifierPanel.updateTierViews();
        }
        System.out.println("3");
    }
}

class ModifierComboBoxRenderer extends JLabel implements ListCellRenderer {

    ModifierComboBox owner;
    private Map<Influence, ImageIcon> iconMap = new HashMap<>();
    
    public ModifierComboBoxRenderer(ModifierComboBox owner) {
        this.owner = owner;
        setOpaque(true);
        setBackground(new Color(60,60,60));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(7, 5, 7, 0));
        setFont(Frame.mainFrame.getNewFont(12));
        
        iconMap.put(Influence.NORMAL, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/transparent.png")));
        
        iconMap.put(Influence.SHAPER, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/shaper-symbol.png")));
        iconMap.put(Influence.ELDER, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/elder-symbol.png")));
        iconMap.put(Influence.HUNTER, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/hunter-symbol.png")));
        iconMap.put(Influence.WARLORD, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/warlord-symbol.png")));
        iconMap.put(Influence.REDEEMER, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/redeemer-symbol.png")));
        iconMap.put(Influence.CRUSADER, new ImageIcon(Frame.mainFrame.getClass().getResource("/resources/images/crusader-symbol.png")));
    }
    
    private StringBuffer genHTMLString(String rawtext, String highlight) {
        
        String rawTextLower = rawtext.toLowerCase();
        String highlightLower = highlight.toLowerCase();
        
        StringBuffer text = new StringBuffer(rawtext);
        StringBuffer lowertext = new StringBuffer(rawTextLower);
        if (!rawTextLower.contains(highlightLower) || highlight.equals(""))
        {
            return text;
        }
        
        int index = rawTextLower.indexOf(highlightLower);
        int endIndex = index + highlight.length() + 28;

        text.insert(index, "<span style=\"Color: YELLOW\">");
        text.insert(endIndex, "</span>");
        lowertext.insert(index, "<span style=\"Color: YELLOW\">");
        lowertext.insert(endIndex, "</span>");
        
        return text;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        StringBuffer text = genHTMLString(value.toString(), owner.entry);
        
        if (value instanceof Modifier)
        {
            Modifier mod = (Modifier) value;
            Icon icon = iconMap.get(mod.getInfluence());
            setIcon(icon);
        }
        
        text.insert(0, "<html><p style=\"" + getParagraphStyle() + "\">");
        text.insert(text.length(), "</p></html>");
        
        setText(text.toString().replaceAll("\n", " | "));
        
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
    
    private String getParagraphStyle()
    {
        StringBuilder sb = new StringBuilder(100);
	sb.append("word-wrap: break-word;");
	sb.append("width: ");
	sb.append((int) owner.getWidth()*0.7);
	sb.append("px;");
	return sb.toString();
    }
}

class ModifierComboBoxEditor extends BasicComboBoxEditor {
    private JTextField label = new JTextField();
    private Object selectedItem;
     
    public ModifierComboBoxEditor() {
         
        label.setOpaque(false);
        label.setFont(Frame.mainFrame.getNewFont(12));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(60,60,60));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        label.setEditable(true);
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
            Frame.mainFrame.requestFocusInWindow();
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
        
        String content = ((JTextField) owner.getEditor().getEditorComponent()).getText();
        for (int i = 0; i < owner.defaultmodel.getSize(); i++) {
            Modifier m = (Modifier) owner.defaultmodel.getElementAt(i);
            if (content.equals(m.getKey()))
            {
                owner.update(m, true);
                FilterPersistence.saveFilters();
                return;
            }

        }
        owner.update(null, true);
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
        if (Filter.singleton.SelectedBase == null || Filter.singleton.SelectedItemLevel == 0) {
            Frame.mainFrame.requestFocusInWindow();
            JOptionPane.showMessageDialog(Frame.mainFrame, "Please select an item base", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (((JTextField) owner.getEditor().getEditorComponent()).getText().equals("New Modifier"))
        {
            ((JTextField) owner.getEditor().getEditorComponent()).setText("");
        }
        owner.caretPos = ((JTextField) owner.getEditor().getEditorComponent()).getCaretPosition();
        
        owner.updateList();
        if (owner.getModel().getSize() >= 1)
            owner.showPopup();
        
        owner.allSelected = false;
       ((JTextField) owner.getEditor().getEditorComponent()).setFont(Frame.mainFrame.getNewFont(12));
    }

    @Override
    public void focusLost(FocusEvent e)
    {        
        if (owner.parent.assocMod == null)
        {
            System.out.println("assocMod was null");
            ((JTextField) owner.getEditor().getEditorComponent()).setText("New Modifier");
            ((JTextField)owner.getEditor().getEditorComponent()).setForeground(new Color(238,99,90));
            owner.entry = "";
            owner.update(null, true);
        }
        
        FilterPersistence.saveFilters();
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
        if (event.getStateChange() != ItemEvent.SELECTED) {
            return;
        }
        
        if (event.getItem() instanceof Modifier)
        {
            Modifier selected = (Modifier) event.getItem();
            if (selected != null)
            {
                owner.update(selected, true);
                FilterPersistence.saveFilters();
            }
        }
        ((JTextField)owner.getEditor().getEditorComponent()).setForeground(new Color(255,255,255));
        FilterPersistence.saveFilters();
        ((JTextField) owner.getEditor().getEditorComponent()).setFont(Frame.mainFrame.getNewFont(12));
    }
}
