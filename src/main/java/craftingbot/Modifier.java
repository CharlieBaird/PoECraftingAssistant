/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author charl
 */
public class Modifier {
    public static ArrayList<Modifier> all = new ArrayList<Modifier>();
    
    private int ModGenerationTypeID; // 1 = prefix, 2 = suffix
    private String CorrectGroup;
    private String str;

    public String getCorrectGroup() {
        return CorrectGroup;
    }

    public void setCorrectGroup(String CorrectGroup) {
        this.CorrectGroup = CorrectGroup;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    
    public Modifier(String ModGenerationTypeID, String CorrectGroup, String str)
    {
        this.ModGenerationTypeID = Integer.valueOf(ModGenerationTypeID);
        this.CorrectGroup = CorrectGroup;
        
        str = str.replaceAll("<span class='mod-value'>", "");
        str = str.replaceAll("</span>", "");
        str = str.replaceAll("&ndash;", "-");
        str = str.replaceAll("\\(", "");
        str = str.replaceAll("\\)", "");
        if (str.contains("<br"))
            str = str.substring(0,str.indexOf("<br"));
        
        str = removeRolls(str);
        str = str.toLowerCase();
        
        this.str = str;
        
//        if (str.contains("explode")) System.out.println(str);
        
        if (!contains())
            all.add(this);
    }
    
    private String removeRolls(String str)
    {
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(str);
        while(m.find())
        {
            str = str.replace(m.group(1), "#");
        }
        
        str = str.replaceAll("#-#", "#");
        
        return str;
    }
    
    private boolean contains()
    {
        for (Modifier m : all)
        {
            if (m.getCorrectGroup().equals(getCorrectGroup()))
                return true;
        }
        return false;
    }
    
    public void print()
    {
        System.out.printf("%-5s %-50s %-40s\n", ModGenerationTypeID, CorrectGroup, str);
    }
}
