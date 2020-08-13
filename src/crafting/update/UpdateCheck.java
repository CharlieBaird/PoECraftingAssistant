package crafting.update;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateCheck {
    
    private UpdateCheck() {}
    
    public static Release checkForNewRelease()
    {
        Release[] releases = getAllReleases();
        
        if (releases == null) return null;
        
        return getLatestNonPrerelease(releases);
    }
    
    private static Release getLatestNonPrerelease(Release[] releases)
    {
        if (releases == null || releases.length == 0) return null;
        
        Release savedRelease = releases[0];
        
        for (Release release : releases) {
            if (!release.prerelease) {
                savedRelease = release;
                break;
            }
        }
        
        return savedRelease;
    }
    
    public static String getJson(String urlToRead) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
   }
    
    private static Release[] getAllReleases()
    {
        String response = null;
        try {
            response = getJson("https://api.github.com/repos/CharlieBaird/PoECraftingAssistant/releases");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if (response == null) return null;
        
        JsonParser parser = new JsonParser();
        
        JsonArray array = parser.parse(response).getAsJsonArray();
        
        Release[] releases = new Release[array.size()];
        
        for (int i=0; i<array.size(); i++)
        {
            JsonObject releaseObject = array.get(i).getAsJsonObject();
            
            String tag_name = releaseObject.get("tag_name").getAsString();
            boolean prerelease = releaseObject.get("prerelease").getAsBoolean();
            
            Release r = new Release(tag_name, prerelease);
            releases[i] = r;
        }
        
        return releases;
    }
}
