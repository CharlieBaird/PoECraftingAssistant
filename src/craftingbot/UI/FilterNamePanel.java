package craftingbot.UI;

import craftingbot.Filters;
import craftingbot.Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import craftingbot.Filter;
import craftingbot.Utility;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.io.File;

public class FilterNamePanel extends JPanel {
    
    public static ArrayList<FilterNamePanel> filterpanels = new ArrayList<FilterNamePanel>();
    public String name = null;
    public String savedname = null;
    public Filter filter;
    public JPanel parent;
    public String resourcePath;
    public Main frame;
    
    public FilterNamePanel(Main frame, JPanel parent, Filter filter)
    {
        
        String path = "src/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        
        this.name = filter.name;
        this.savedname = filter.name;
        this.filter = filter;
        this.parent = parent;
        this.frame = frame;
        
        Dimension size = new Dimension((int)(parent.getWidth() * 0.97),40);
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(40,40,40));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        CloseButton cb = new CloseButton(this, size);
        FilterTextField ftf = new FilterTextField(this, size, name, savedname);
        OpenButton ob = new OpenButton(this,size);
        
        add(cb);
        add(ftf);
        add(ob);
        
        parent.add(this);
        filterpanels.add(this);
    }
    
    public void remove()
    {
        parent.requestFocusInWindow();
        if (filter.active)
        {
            FilterTypePanel.clear(true);
            frame.hideAddButton();
        }
        setVisible(false);
        Filters.singleton.remove(name);
        Filters.saveFilters();
        
    }
}

class BackgroundListener implements MouseListener
{
    public JComponent object;
    private Color inside;
    private Color away;
    
    public BackgroundListener(JComponent object, Color inside, Color away)
    {
        this.object = object;
        this.inside = inside;
        this.away = away;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        object.setBackground(inside);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        object.setBackground(away);
    }    
}

class FilterTextField extends JTextField
{
    public FilterTextField(FilterNamePanel parent, Dimension size, String name, String savedname)
    {
        Dimension d = new Dimension((int)(size.width*0.6), (int)(size.height * 0.9));
        setBackground(new Color(40,40,40));
        setForeground(new Color(255,255,255));
        setPreferredSize(d);
        setText(name);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        setFont(parent.frame.getNewFont(14));
        
        KeyListener keyListener = new KeyListener()
        {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10)
                {
                    parent.requestFocusInWindow();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        };
        
        addKeyListener(keyListener);
        
        addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                Filters.singleton.rename(parent.filter, getText());
                Filters.saveFilters();
                parent.savedname = getText();
            }
        });
    }
}

class CloseButton extends JButton
{
    public CloseButton(FilterNamePanel parent, Dimension size)
    {
        Dimension d1 = new Dimension((int)(size.width * 0.15), (int)(size.height * 0.9));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(d1);
        setBackground(new Color(40,40,40));
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/xbuttontransparentsmall.png"))); // NOI18N
        setToolTipText("Delete this subfilter");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(40,40,40)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.remove();
            }
        };
        addActionListener(actionListener);
    }
}

class OpenButton extends JButton
{
    public OpenButton(FilterNamePanel parent, Dimension size)
    {
        Dimension d1 = new Dimension((int)(size.width * 0.15), (int)(size.height * 0.9));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(d1);
        setBackground(new Color(40,40,40));
        setIcon(new javax.swing.ImageIcon(parent.frame.getClass().getResource("/resources/images/arrowbuttontransparentsmall.png"))); // NOI18N
        setToolTipText("Open this subfilter");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(40,40,40)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.frame.genFilterPanel(parent.filter);
            }
        };
        addActionListener(actionListener);
    }
}
