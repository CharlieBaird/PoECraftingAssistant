/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

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
}
