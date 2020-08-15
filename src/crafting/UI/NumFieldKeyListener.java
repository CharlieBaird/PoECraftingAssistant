/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NumFieldKeyListener implements KeyListener {
    
    private boolean loseFocus = true;
    
    public NumFieldKeyListener() { }
    
    public NumFieldKeyListener(boolean loseFocus)
    {
        this.loseFocus = loseFocus;
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        if (!(e.getKeyCode() == 8
            || (e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
            || e.getKeyCode() == KeyEvent.VK_LEFT
            || e.getKeyCode() == KeyEvent.VK_RIGHT))
        {
           e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == 10 && loseFocus)
        {
            Frame.mainFrame.requestFocusInWindow();
        }
    }
}
