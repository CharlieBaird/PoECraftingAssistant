package crafting;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActivityTooltip extends JFrame {
    public ActivityTooltip() {
        super();
        setAlwaysOnTop(true);
        setUndecorated(true);
        Component c = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setColor(new Color(0,255,0,10));
                int w = getWidth();
                int h = getHeight();
                g2.fillRect(0, 0, w,h);
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(25, 25, 10, 10);
            }
        };
        c.setPreferredSize(new Dimension(60, 60));
        getContentPane().add(c);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        com.sun.awt.AWTUtilities.setWindowOpaque(this,false);
        Point p = MouseInfo.getPointerInfo().getLocation();
        setLocation(p.x-30, p.y-30);
    }
}
