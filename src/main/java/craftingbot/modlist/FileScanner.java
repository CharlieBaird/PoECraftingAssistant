/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.modlist;

import java.io.*; 
import java.util.Scanner;

public class FileScanner {
    public static String readFromFile(String path) throws Exception 
    {
        String str = "";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              str += data;
            }
            myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return str;
    }
    
    public static void writeToFile(String path, String contents)
    {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
