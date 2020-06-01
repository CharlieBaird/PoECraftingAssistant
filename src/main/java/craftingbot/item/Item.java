package craftingbot.item;

import craftingbot.Modifier;
import java.util.ArrayList;

public class Item {
    
    public String name;
    public int itemLevel;
    public ArrayList<Modifier> mods;
    
    public static String sample = 
    "Rarity: Rare\n" +
    "Chimeric Edge\n" +
    "Imbued Wand\n" +
    "--------\n" +
    "Wand\n" +
    "Quality: +41% (augmented)\n" +
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
        
    }
    
    public Item(String name, int itemLevel, ArrayList<Modifier> mods)
    {
        this.name = name;
        this.itemLevel = itemLevel;
        this.mods = mods;
    }
    
    public Item(String name, int itemLevel)
    {
        this.name = name;
        this.itemLevel = itemLevel;
    }
}
