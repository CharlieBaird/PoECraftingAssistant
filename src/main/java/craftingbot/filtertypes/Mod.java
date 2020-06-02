/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

import craftingbot.Modifier;
import craftingbot.Utility;
import java.io.Serializable;

/**
 *
 * @author charl
 */
public class Mod implements Serializable {
    public Modifier assocModifier;
    public String name; // Form of: #% increased movement speed
    public Id ID = new Id(); // Form of: min 25, max 35
    
//    public String converged;
    
    public Mod(Modifier assocModifier, String name, int... id)
    {
        this.assocModifier = assocModifier;
        this.name = name.toLowerCase();
//        this.ids = new Id[id.length / 2];
               
        for (int i=0; i<id.length; i+=2)
            ID = new Id(id[i/2], id[i/2+1]);
    }
    
    public void toLowerCase()
    {
        name = name.toLowerCase();
    }
    
    public Mod dupe()
    {   
        return new Mod(this.assocModifier, this.name, ID.toArr());
    }
    
    public boolean hit(Modifier em)
    {
//        System.out.println(this.name + "-" + em.getStr());
        if (this.name.equals(em.getStr()))
        {
            if (ID.valid(em.rolls))
                return true;
        }
        return false;
    }
    
    public boolean hit(String input)
    {
        String[] inputLines = input.split("\\R");
        
//        System.out.println(name);
        
        if (input.contains(name))
        {
            for (String s : inputLines)
            {
                if (s.contains(name))
                {                

                    String[] inputLinesSep = s.split("[*]{1}");

                    s = inputLinesSep[0];
                    double[] ids = new double[inputLinesSep.length-1];
                    for (int i=1; i<inputLinesSep.length; i++)
                    {
                        ids[i-1] = Double.valueOf(inputLinesSep[i]);
                    }

                    if (ID.valid(ids))
                        return true;
                    else
                        return false;
                }
            }
        }
        
        else if (assocModifier != null && assocModifier.getModGenerationTypeID() == -1)
        {
            if (name.equals("+#% total elemental resistance"))
            {
                double total = total(input, inputLines, "+#% to cold resistance", "+#% to fire resistance", "+#% to lightning resistance");
                return ID.valid(total);
            }
            
            else if (name.equals("+#% total resistance"))
            {
                double total = total(input, inputLines, "+#% to cold resistance", "+#% to fire resistance", "+#% to lightning resistance", "+#% to chaos resistance");
                return ID.valid(total);
            }
            
            else if (name.equals("# empty suffix modifiers"))
            {
                int[] num = numPrefixSuffix(input);
//                printArr(num);
                return ID.valid(3 - num[1]);
            }
            
            else if (name.equals("# empty prefix modifiers"))
            {
                int[] num = numPrefixSuffix(input);
//                printArr(num);
                return ID.valid(3 - num[0]);
            }
        }
        
        return false;
    }
    
    private void printArr(int[] arr)
    {
        System.out.println("# empty prefix: " + (3 - arr[0]));
        System.out.println("# empty suffix: " + (3 - arr[1]));
    }
    
    private int[] numPrefixSuffix(String input)
    {
        String[] inputLines = input.split("\\R");
        
        int[] totalPrefixSuffix = new int[2];
        
        for (String s : inputLines)
        {
            String real = s.split("[*]{1}")[0];
            
            int type;
            try {
                type = Modifier.getFromStr(real).getModGenerationTypeID();
            } catch (NullPointerException e) {
                continue;
            }
            if (type == 1) totalPrefixSuffix[0]++;
            else if (type == 2) totalPrefixSuffix[1]++;
        }
        
        return totalPrefixSuffix;
    }
    
    private double total(String input, String[] inputLines, String... mods)
    {
        double total = 0;
        for (String modName : mods)
        {
            if (input.contains(modName))
            {
                for (String s : inputLines)
                {
                    if (s.contains(modName))
                    {                

                        String[] inputLinesSep = s.split("[*]{1}");

                        s = inputLinesSep[0];
                        double value = Double.valueOf(inputLinesSep[1]);
                        total += value;
                    }
                }
            }
        }
        
        return total;
    }
    
    public void print()
    {
        System.out.println("        \"" + name + "\"");
        System.out.println("            ids: " + ID.min + ", " + ID.max);
    }
    
    public String view()
    {
        String str = name + "\n";
        str += ("               min: " + ID.min + ", max: " + ID.max);
        
        return str;
    }
}