/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

import craftingbot.item.Item;


public interface IFilter {
    public boolean hit(Item item);
    
    public void print();
    
    public String view();
//    public FilterBase dupe();
}
