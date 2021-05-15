/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.filtertypes;

import crafting.UI.ModifierPanel;
import java.io.Serializable;
import poeitem.Modifier;
import poeitem.ModifierTier;

public class Mod implements Serializable {
    
    public transient ModifierPanel assocModifierPanel = null;
    public Modifier assocModifier;
    public ModifierTier assocModifierTier;
    
    
    public Mod(Modifier assocModifier, ModifierTier assocModifierTier)
    {
        this.assocModifier = assocModifier;
        this.assocModifierTier = assocModifierTier;
    }
    
    public boolean hit(ModifierTier em)
    {
        if (assocModifierTier == null)
        {
            return assocModifier.getModifierTiers().contains(em);
        }
        
        return assocModifierTier.equals(em);
    }
    
    public void print()
    {
        if (assocModifier != null)            
            System.out.println("        \"" + assocModifier.getKey() + "\"");
        if (assocModifierTier != null)
            System.out.println("            " + assocModifierTier.getName());
    }
}