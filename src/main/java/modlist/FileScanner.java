/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modlist;

import java.io.*; 

public class FileScanner {
    public static String readFromFile(String path) throws Exception 
    {
        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file)); 

        String st; 
        while ((st = br.readLine()) != null) 
          System.out.println(st); 
        
        return st;
    } 
}
