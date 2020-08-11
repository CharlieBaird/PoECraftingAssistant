package crafting;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActivityTooltip extends JFrame {
    
    JPanel contents;
    
    int x = 25;
    int y = 25;
    int width = 10;
    int height = 10;
    int opacity = 20;
    
    public ActivityTooltip() {
        super();
        setAlwaysOnTop(true);
        setUndecorated(true);
        contents = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setColor(new Color(0,255,0,opacity));
                int w = getWidth();
                int h = getHeight();
                g2.fillRect(0, 0, w,h);
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(x, y, width, height);
            }
        };
        contents.setPreferredSize(new Dimension(60, 60));
        contents.setOpaque(false);
        
        getContentPane().add(contents);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setBackground(new Color(0,0,0,0));
        setFocusable(false);
        Point p = MouseInfo.getPointerInfo().getLocation();
        setLocation(p.x-30, p.y-30);
        setVisible(true);
//        com.sun.awt.AWTUtilities.setWindowOpaque(this,false);
    }
    
    public void modHit()
    {
        new Thread(new Runnable() {
            @Override
            public void run()  {
                opaque();
                try  { Thread.sleep( 2000 ); }
                catch (InterruptedException ie)  {}
                transparent();
            }
        }).start();
    }
    
    public void opaque()
    {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        opacity = 60;
        this.repaint();
    }
    
    public void transparent()
    {
        x = 25;
        y = 25;
        width = 10;
        height = 10;
        opacity = 20;
        this.repaint();
    }
}
