/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crafting.filtertypes;

import poeitem.PoEItem;



public interface IFilter {
    public boolean hit(PoEItem item);
    
    public void print();
}
