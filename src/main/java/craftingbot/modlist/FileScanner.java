/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.modlist;

import java.io.*; 
import java.nio.charset.StandardCharsets;

public class FileScanner {
    public static String readFromFile(String path) throws Exception 
    {        
        final BufferedReader buff = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

        StringBuilder builder = new StringBuilder();
        String aux = "";

        while ((aux = buff.readLine()) != null) {
            builder.append(aux);
        }
        
        return builder.toString();
    }
    
    public static void writeToFile(String path, String contents)
    {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException e) {
        }
    }
}
