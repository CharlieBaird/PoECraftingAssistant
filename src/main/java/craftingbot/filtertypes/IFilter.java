/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;


public interface IFilter {
    public boolean hit(String input);
    
    public void print();
    
//    public FilterBase dupe();
}
