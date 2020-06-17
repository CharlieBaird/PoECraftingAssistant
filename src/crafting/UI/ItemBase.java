/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.UI;

import crafting.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import poeitem.Base;

/**
 *
 * @author charl
 */
public class ItemBase extends JPanel {
    
    public static Base SelectedBase = null;
    public static int SelectedItemLevel = 100;
        
    protected static final HashMap<String, Base> BaseTypes = new HashMap<String, Base>()
    {{
        put ("Claw", Base.CLAW);
        put ("Dagger", Base.DAGGER);
        put ("Wand", Base.WAND);
        put ("One Hand Sword", Base.ONE_HAND_SWORD);
        put ("Thrusting One Hand Sword", Base.THRUSTING_ONE_HAND_SWORD);
        put ("One Hand Axe", Base.ONE_HAND_AXE);
        put ("One Hand Mace", Base.ONE_HAND_MACE);
        put ("Sceptre", Base.SCEPTRE);
        put ("Rune Dagger", Base.RUNE_DAGGER);
        put ("Bow", Base.BOW);
        put ("Staff", Base.STAFF);
        put ("Two Hand Sword", Base.TWO_HAND_SWORD);
        put ("Two Hand Axe", Base.TWO_HAND_AXE);
        put ("Two Hand Mace", Base.TWO_HAND_MACE);
        put ("FishingRod", Base.FISHING_ROD);
        put ("Warstaff", Base.WARSTAFF);
        put ("Crimson Jewel", Base.CRIMSON_JEWEL);
        put ("Viridian Jewel", Base.VIRIDIAN_JEWEL);
        put ("Murderous Eye Jewel", Base.MURDEROUS_EYE_JEWEL);
        put ("Searching Eye Jewel", Base.SEARCHING_EYE_JEWEL);
        put ("Ghastly Eye Jewel", Base.GHASTLY_EYE_JEWEL);
        put ("Hypnotic Eye Jewel", Base.HYPNOTIC_EYE_JEWEL);
        put ("Large Cluster Jewel", Base.LARGE_CLUSTER_JEWEL);
        put ("Medium Cluster Jewel", Base.MEDIUM_CLUSTER_JEWEL);
        put ("Small Cluster Jewel", Base.SMALL_CLUSTER_JEWEL);
        put ("Amulet", Base.AMULET);
        put ("Ring", Base.RING);
        put ("Unset Ring", Base.UNSET_RING);
        put ("Belt", Base.BELT);
        put ("Gloves", Base.GLOVES);
        put ("Boots", Base.BOOTS);
        put ("Body Armour", Base.BODY_ARMOUR);
        put ("Helmet", Base.HELMET);
        put ("Quiver", Base.QUIVER);
        put ("Shield", Base.SHIELD);
        put ("Flask", Base.FLASK);
    }};
    
    public ItemBase(JPanel parent)
    {
        setPreferredSize(new Dimension(523, 32));
        setBackground(new Color(88,0,0));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        add(new TitleLabel("Item Type:"));
        add(Box.createRigidArea(new Dimension(8,0)), Box.LEFT_ALIGNMENT);
        add(new SearchJBox(this, BaseTypes.keySet().toArray()));
        
        add(Box.createRigidArea(new Dimension(8,0)), Box.LEFT_ALIGNMENT);
        
        add(new TitleLabel("Item Level:"));
        add(Box.createRigidArea(new Dimension(8,0)), Box.LEFT_ALIGNMENT);
        add(new ItemLevelField("100"));
        
        Main.mainFrame.requestFocusInWindow();
    }
}

class ItemLevelField extends JTextField
{
    
    public ItemLevelField(String text)
    {
        super(text);
        addKeyListener(new NumFieldKeyListener());
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                focusGain();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focusLoss();
            }
        });
    }
    
    protected void focusGain()
    {
        
    }
    
    protected void focusLoss()
    {
        ItemBase.SelectedItemLevel = Integer.valueOf(this.getText());
    }
}

class TitleLabel extends JLabel
{
    public TitleLabel(String text)
    {
        super(text);
        setFont(Main.mainFrame.getNewFont(12));
        setForeground(new Color(255,255,255));
    }
}
