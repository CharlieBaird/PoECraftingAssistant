package craftingbot.item;

import craftingbot.Filters;
import craftingbot.Modifier;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item {
    
//    public String rarity;
//    public String customName;
//    public String baseType;
    
//    public String itemType;
    public int quality = 0;
//    public int physicalDamage = 0;
//    public int fireDamage = 0;
//    public int coldDamage = 0;
//    public int lightningDamage = 0;
//    public int chaosDamage = 0;
//    public double baseCrit;
//    public double baseAps;
    
//    public int levelReq;
//    public Dictionary<String, Integer> attrReqs;
    
//    public String sockets;
    
//    public int itemLevel;
    
//    public ArrayList<Modifier> implicitModifiers = new ArrayList<>();
    
    public ArrayList<Modifier> explicitModifiers = new ArrayList<>();
    
//    public boolean corrupted;
    
//    public String influence;
    
    public static String sample = 
        "Rarity: Rare\n" +
        "Chimeric Edge\n" +
        "Imbued Wand\n" +
        "--------\n" +
        "Wand\n" +
        "Quality: +41% (augmented)\n" + // 5
        "Physical Damage: 150-286 (augmented)\n" +
        "Critical Strike Chance: 7.00%\n" +
        "Attacks per Second: 1.65 (augmented)\n" +
        "--------\n" +
        "Requirements:\n" +
        "Level: 64\n" +
        "Int: 188\n" +
        "--------\n" +
        "Sockets: G-B-B \n" +
        "--------\n" +
        "Item Level: 83\n" +
        "--------\n" +
        "33% increased Spell Damage (implicit)\n" +
        "--------\n" +
        "38% increased Physical Damage\n" +
        "Adds 21 to 40 Physical Damage\n" +
        "62% increased Mana Regeneration Rate\n" +
        "24% increased Projectile Speed\n" +
        "Can have up to 3 Crafted Modifiers (crafted)\n" +
        "128% increased Physical Damage (crafted)\n" +
        "10% increased Attack Speed (crafted)\n" +
        "+11% to Quality (crafted)\n" +
        "--------\n" +
        "Corrupted\n" +
        "--------\n" +
        "Crusader Item";
    
    public Item(String raw)
    {
        if (raw == null) raw = sample;
        
        raw = Filters.parseMods(raw);
        
        String[] lines = raw.split("\\r?\\n");
        
        int start = 0;
        if (lines[0].contains("Quality"))
        {
            quality = Integer.valueOf(lines[0].substring(lines[0].indexOf("*") + 1));
            start++;
        }
        
        for (int i=start; i<lines.length; i++)
        {
            String s = lines[i];
            
            ArrayList<Double> rolls = new ArrayList<>();
            
            Matcher getRoll = Pattern.compile("([*]+)([(\\d+(?:\\.\\d+)?)]+)").matcher(s);
            while (getRoll.find())
            {
                rolls.add(Double.valueOf(getRoll.group(2)));
                s = s.replace(getRoll.group(0), "");
            }
            
            Modifier m = Modifier.getFromStr(s);
            
            for (int j=0; j<rolls.size(); j++)
            {
                m.rolls[j] = rolls.get(j);
            }
            
            explicitModifiers.add(m);
        }
        
        print();
    }
                
    public final String getSingleString(String inputLine, String regex)
    {
        Matcher m = Pattern.compile(regex).matcher(inputLine);
        if (m.find())
            return m.group(3);
        return null;
    }
    
    public final void print()
    {
//        System.out.println(rarity + " " + customName + " " + baseType);
//        System.out.println(itemType);
        System.out.println("Quality: " + quality);
//        System.out.println(
//                physicalDamage + " phys, " + 
//                fireDamage + " fire, " + 
//                coldDamage + " cold, " + 
//                lightningDamage + " lightning, " + 
//                chaosDamage + " chaos");
//        System.out.println("BaseCrit: " + baseCrit);
//        System.out.println("BaseAps: " + baseAps);
//        System.out.println("LevelReq: " + levelReq);
//        System.out.println("AttrReqs: " + attrReqs);
//        System.out.println("Sockets: " + sockets);
//        System.out.println("ItemLevel: " + itemLevel);
//        System.out.println("Implicits: ");
//        for (Modifier m : implicitModifiers) m.print();
        System.out.println("Explicits: ");
        for (Modifier m : explicitModifiers) m.print();
//        System.out.println("Corrupted: " + corrupted);
//        System.out.println("Influence: " + influence);
    }
}
