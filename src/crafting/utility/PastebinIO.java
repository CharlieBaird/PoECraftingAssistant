package crafting.utility;

import crafting.persistence.Settings;
import crafting.UI.Frame;
import crafting.filters.Filter;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jpaste.exceptions.PasteException;
import org.jpaste.pastebin.Pastebin;
import crafting.persistence.FilterPersistence;

public class PastebinIO {

    public static void importFilter() {
        JTextField pastebinURL = new JTextField();
        
        Object[] message =  {
            "Pastebin URL",
            pastebinURL
        };
        
        int n = JOptionPane.showConfirmDialog(Frame.mainFrame, message, "Import filter from Pastebin", JOptionPane.OK_CANCEL_OPTION);
        
        if (n == JOptionPane.OK_OPTION)
        {
            if (!Filter.singleton.isInitial)
            {
                Object[] message2 = {
                "Your current filter's configuration will be lost by importing this filter.\nIs this ok?"
                };

                int n2 = JOptionPane.showConfirmDialog(Frame.mainFrame, message2, "Confirm", JOptionPane.OK_CANCEL_OPTION);

                if (n2 != JOptionPane.OK_OPTION)
                    return;
            }
            
            String content = parseTextForImport(pastebinURL.getText());
            
            if (content == null)
            {
                JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to import filter from Pastebin", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Filter filter = SerializationUtils.deserialize(content);
                FilterPersistence.importFilter(filter);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to import filter from Pastebin", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    
    private static String parseTextForImport(String pastebinURL)
    {
        Pattern p = Pattern.compile("[com/]{4}([a-zA-Z0-9]{0,20})");
        Matcher m = p.matcher(pastebinURL);
        if (m.find())
        {
            return getPastebinContent(m.group(1));
        }
        
        p = Pattern.compile("^([a-zA-Z0-9]{0,20})$");
        m = p.matcher(pastebinURL);
        if (m.lookingAt())
        {
            return getPastebinContent(pastebinURL);
        }
        
        p = Pattern.compile("^[-A-Za-z0-9+/=]+$");
        m = p.matcher(pastebinURL);
        if (m.lookingAt())
        {
            return pastebinURL;
        }
        
        return null;
    }
    
    private static String getPastebinContent(String url)
    {
        try {
            return Pastebin.getContents(url);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to import filter from Pastebin", "Error", JOptionPane.ERROR_MESSAGE);
        }
            
        return null;
    }
    
    public static void exportFilter()
    {
        String s = "";
        try {
            s = SerializationUtils.serialize(Filter.singleton);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to export filter to Pastebin. Please check your\n developer key and Internet connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            String link = Pastebin.pastePaste(Settings.singleton.pastebinKey, s).toString();
            StringSelection selection = new StringSelection(link);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            JOptionPane.showMessageDialog(Frame.mainFrame, "Export to Pastebin succeeded.\nThe link has been copied to your clipboard.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (PasteException ex) {
            System.out.println(ex.getMessage());
            if (ex.toString().contains("Post limit"))
            {
                StringSelection selection = new StringSelection(s);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to export filter to Pastebin. Maximum uploads per 24 hours has been reached.\nThe contents of the failed paste have been copied to your clipboard.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(Frame.mainFrame, "Failed to export filter to Pastebin. Please check your\n developer key and Internet connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
