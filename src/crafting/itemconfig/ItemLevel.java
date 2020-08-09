package crafting.itemconfig;

import crafting.Filter;
import crafting.UI.Main;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ItemLevel extends JPanel {
        
    public ItemLevelComboBox levelComboBox;
        
    public ItemLevel()
    {
        setMaximumSize(new Dimension(1000, 32));
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        String[] levels = new String[86];
        for (int i=1; i<=86; i++) levels[i-1] = String.valueOf(87-i);
        
        levelComboBox = new ItemLevelComboBox(this, levels);
        add(levelComboBox);
        
        Main.mainFrame.requestFocusInWindow();
    }

    public void updateFromFilter() {
        this.levelComboBox.setSelectedIndex(Filter.singleton.SelectedItemLevelIndex);

    }
}
