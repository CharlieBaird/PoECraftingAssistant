package crafting.UI.console;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.StyledDocument;

public class Console extends JFrame {
    
    public static Console loadingFrame;
    public static StyledDocument doc;
    
    public static void launch()
    {
        if (true) return;
        loadingFrame = new Console();
        loadingFrame.setTitle("PoE Crafting Assistant Console");
        loadingFrame.setBackground(Color.BLACK);
        loadingFrame.setAlwaysOnTop(true);
        loadingFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        Point p = loadingFrame.getLocation();
        loadingFrame.setLocation(p.x-loadingFrame.getWidth()/2, p.y-loadingFrame.getHeight()/2);
        
        loadingFrame.setVisible(true);
        
        
    }

    public static void close() {
        if (loadingFrame != null)
            loadingFrame.setVisible(false);
    }

    public static void open() {
        if (loadingFrame != null)
            loadingFrame.setVisible(true);
    }
    
    private Console()
    {
        setLocationRelativeTo(null);
        setSize(600,450);
        setLayout(new GridLayout());
        
        JTextArea ta = new JTextArea();
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.WHITE);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );


        add( new JScrollPane( ta )  );
        
    }
}
