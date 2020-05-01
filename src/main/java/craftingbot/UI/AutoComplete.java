package craftingbot.UI;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.*;

/* This work is hereby released into the Public Domain.
 * To view a copy of the public domain dedication, visit
 * http://creativecommons.org/licenses/publicdomain/
 */
public class AutoComplete extends PlainDocument {
    JComboBox comboBox;
    
    ComboBoxModel modelOnInit;
    ComboBoxModel activeModel;
    
    JTextComponent editor;
    // flag to indicate if setSelectedItem has been called
    // subsequent calls to remove/insertString should be ignored
    boolean selecting=false;
    boolean hidePopupOnFocusLoss;
    boolean hitBackspace=false;
    boolean hitBackspaceOnSelection;
    
    KeyListener editorKeyListener;
    FocusListener editorFocusListener;
    
    String entry = "";
    int total = 0;
    
    public AutoComplete(final JComboBox comboBox) {
        this.comboBox = comboBox;
        modelOnInit = comboBox.getModel();
        activeModel = modelOnInit;
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("typed");
                if (!selecting) highlightCompletedText(0);
            }
        });
        comboBox.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName().equals("editor")) configureEditor((ComboBoxEditor) e.getNewValue());
                if (e.getPropertyName().equals("model")) modelOnInit = (ComboBoxModel) e.getNewValue();
            }
        });
        editorKeyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (comboBox.isDisplayable()) comboBox.setPopupVisible(true);
                hitBackspace=false;
                switch (e.getKeyCode()) {
                    // determine if the pressed key is backspace (needed by the remove method)
                    case KeyEvent.VK_BACK_SPACE :
                        if (entry.length() >= 1)
                            entry = entry.substring(0, entry.length() - 1);
                    break;
                    // ignore delete key
                    case KeyEvent.VK_DELETE : e.consume();
//                    comboBox.getToolkit().beep();
                    break;
                }
            }
        };
        // Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
        hidePopupOnFocusLoss=System.getProperty("java.version").startsWith("1.5");
        // Highlight whole text when gaining focus
        editorFocusListener = new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                highlightCompletedText(0);
            }
            public void focusLost(FocusEvent e) {
                // Workaround for Bug 5100422 - Hide Popup on focus loss
                if (hidePopupOnFocusLoss) comboBox.setPopupVisible(false);
            }
        };
        configureEditor(comboBox.getEditor());
    }
    
    public static void enable(JComboBox comboBox) {
        // has to be editable
        comboBox.setEditable(true);
        // change the editor's document
        new AutoComplete(comboBox);
    }
    
    void configureEditor(ComboBoxEditor newEditor) {
        if (editor != null) {
            editor.removeKeyListener(editorKeyListener);
            editor.removeFocusListener(editorFocusListener);
        }
        
        if (newEditor != null) {
            editor = (JTextComponent) newEditor.getEditorComponent();
            editor.addKeyListener(editorKeyListener);
            editor.addFocusListener(editorFocusListener);
            editor.setDocument(this);
        }
    }
    
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        System.out.println("str: " + str);
        
//        if (str.length() == 1)
//            entry += str;
//        
//        System.out.println("called");
//        System.out.println(entry);
//        
//        System.out.println(comboBox.getModel().getSize());
//        
//        String[] newModelBase = getCompatObjects();
//        activeModel = new DefaultComboBoxModel(newModelBase);
//        comboBox.setModel(activeModel);
//        
//        System.out.println(comboBox.getModel().getSize());
//        System.out.println("done");
        
        super.insertString(offs,str,a);
    }
    
    private void highlightCompletedText(int start) {
//        editor.setCaretPosition(getLength());
//        editor.moveCaretPosition(start);
    }
    
    private String[] getCompatObjects()
    {
        ArrayList<Object> os = new ArrayList<Object>();
        for (int i=0; i<modelOnInit.getSize(); i++)
        {
            Object o = modelOnInit.getElementAt(i);
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
}