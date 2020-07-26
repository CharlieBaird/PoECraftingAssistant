package crafting.itemconfig;

import crafting.Filters;
import crafting.UI.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedHashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import poeitem.Base;

public class ItemLevel extends JPanel {
        
    public ItemLevelComboBox levelComboBox;
        
    public ItemLevel()
    {
        setMaximumSize(new Dimension(1000, 32));
        setBackground(new Color(30,30,30));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
//        add(new TitleLabel("Item Level:"));
//        add(Box.createRigidArea(new Dimension(8,0)), Box.LEFT_ALIGNMENT);
        
        
        String[] levels = new String[86];
        for (int i=1; i<=86; i++) levels[i-1] = String.valueOf(87-i);
        
        levelComboBox = new ItemLevelComboBox(this, levels);
        add(levelComboBox);
        
//        add(Box.createRigidArea(new Dimension(8,0)), Box.LEFT_ALIGNMENT);
        
        Main.mainFrame.requestFocusInWindow();
    }

    public void updateFromFilter() {
//        this.baseComboBox.setSelectedIndex(Filters.singleton.);
    }
}
