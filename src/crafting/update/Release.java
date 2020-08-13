
package crafting.update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Release implements Comparable {
    
    public Tag tag;
    public boolean prerelease;
    
    public Release(String tag_name)
    {
        this.tag = new Tag(tag_name);
    }
    
    public Release (String tag_name, boolean prerelease)
    {
        this.tag = new Tag(tag_name);
        this.prerelease = prerelease;
    }
    
    @Override
    public String toString()
    {
        return tag.toString();
    }

    @Override
    public int compareTo(Object o) {
        Release that = (Release) o;
        return this.tag.compareTo(that.tag);
    }
    
    public String compareTo_string(Object o)
    {
        Release that = (Release) o;
        
        int x = this.compareTo(that);
        
        if (x > 0) return "Greater";
        if (x < 0) return "Lesser";
        return "Equal";
    }
}

class Tag implements Comparable {
        
        // Form of 3.2.1
        private int a; // 3
        private int b; // 2
        private int c; // 1

        public Tag(String tag_name) {
            Pattern pattern = Pattern.compile("([0-9]+)([.]{1})([0-9]+)([.]{1})([0-9]+)");
            Matcher matcher = pattern.matcher(tag_name);
            if (matcher.find())
            {
                a = Integer.valueOf(matcher.group(1));
                b = Integer.valueOf(matcher.group(3));
                c = Integer.valueOf(matcher.group(5));
            }
        }

        @Override
        public int compareTo(Object o) {
            Tag that = (Tag) o;
            
            int aDiff = this.a - that.a;
            int bDiff = this.b - that.b;
            int cDiff = this.c - that.c;
            
            if (aDiff != 0) return aDiff;
            if (bDiff != 0) return bDiff;
            return cDiff;
        }
        
        @Override
        public String toString()
        {
            return new StringBuilder().append(a).append(".").append(b).append(".").append(c).toString();
        }
    }
