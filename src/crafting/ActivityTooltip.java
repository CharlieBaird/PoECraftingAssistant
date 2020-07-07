package crafting;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActivityTooltip extends JFrame {
    public ActivityTooltip()
    {
        super();
        setSize(60,60);
        Point p = MouseInfo.getPointerInfo().getLocation();
        setLocation(p.x-30, p.y-30);
        setUndecorated(true);
//        JPanel model = new JPanel();
//        model.setSize(this.getSize());
        this.setBackground(new Color(0,255,0,0));
//        model.setBackground(new Color(150,255,150,35));
//        model.setOpaque(false);
//        add(model);
        setAlwaysOnTop(true);
        setVisible(true);
    }
}
