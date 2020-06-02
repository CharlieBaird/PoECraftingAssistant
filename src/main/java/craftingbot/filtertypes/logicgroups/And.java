/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes.logicgroups;

import craftingbot.Modifier;
import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.Mod;
import craftingbot.item.Item;

public class And extends FilterBase {
    public And(Mod... mods)
    {
        super(mods);
    }
    
    public boolean hit(String input)
    {
        for (Mod m : mods)
        {
            if (!m.hit(input)) return false;
        }
        return true;
    }
    
    public boolean hit(Item item)
    {
        int numHit = 0;
        int goal = this.mods.size();
        for (Mod m : mods)
        {
//            m.print();
            for (Modifier em : item.explicitModifiers)
            {
                if (m.hit(em)) numHit++;
                if (numHit >= goal) return true;
            }
        }
        return false;
    }
}
