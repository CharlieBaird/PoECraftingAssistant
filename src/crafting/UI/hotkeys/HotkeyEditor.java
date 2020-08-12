package crafting.UI.hotkeys;

import crafting.UI.Main;
import javax.swing.*;

public class HotkeyEditor {
    
    public Object[] message;
    
    public HotkeyEditor()
    {
        message = new Object[HotkeyConfig.instance.hotkeys.size() * 3];
        
        for (int i=0; i<HotkeyConfig.instance.hotkeys.size()*3; i+=3)
        {
            Hotkey hotkey = HotkeyConfig.instance.hotkeys.get(i/3);
            message[i] = hotkey.task.pretty;
            JComboBox ctrlValue = new JComboBox(Ctrl.values());
            ctrlValue.setSelectedItem(hotkey.ctrl);
            message[i+1] = ctrlValue;
            JComboBox keyValue = new JComboBox(Key.values());
            keyValue.setSelectedItem(hotkey.key);
            message[i+2] = keyValue;
        }
    }
    
    public static void show(HotkeyEditor editor)
    {
        int n = JOptionPane.showConfirmDialog(Main.mainFrame, editor.message, "Hotkey configuration", JOptionPane.OK_CANCEL_OPTION);
        if (n == JOptionPane.OK_OPTION)
        {
            handleResult(editor);
        }
    }
    
    private static void handleResult(HotkeyEditor editor)
    {
        for (int i=0; i<editor.message.length; i+=3)
        {
            Object ctrlComboBox = editor.message[i+1];
            Object keyComboBox = editor.message[i+2];
            Hotkey hotkey = HotkeyConfig.instance.hotkeys.get(i/3);
            hotkey.ctrl = (Ctrl) ((JComboBox) ctrlComboBox).getSelectedItem();
            hotkey.key = (Key) ((JComboBox) keyComboBox).getSelectedItem();
        }
        
        HotkeyConfig.save();
    }
}
