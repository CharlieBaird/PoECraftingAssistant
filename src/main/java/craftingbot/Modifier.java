/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author charl
 */
public class Modifier {
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
        this.str = str;
        
        parse();
    }
    
    public void print()
    {
//        System.out.println(ModGenerationTypeID + " | " + CorrectGroup + " | " + str);
        System.out.printf("%-5s %-50s %-40s\n", ModGenerationTypeID, CorrectGroup, str);
    }
    
    private void parse()
    {
        Pattern p;
        Matcher m;
        
        System.out.println(str);
        
        p = Pattern.compile("^([\\D]*)([0-9]+)([\\D]+)([0-9]+)([\\D]*)$");
        m = p.matcher(str);
        if      (m.find())
        {
//            System.out.println(m.group(2) + "-" + m.group(4));
        }
    }
}
