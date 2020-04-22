package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Baseitem {
    private String cn;
    private Long name;
    private String linkName;
    private Object[] attr;

    @JsonProperty("cn")
    public String getCN() { return cn; }
    @JsonProperty("cn")
    public void setCN(String value) { this.cn = value; }

    @JsonProperty("name")
    public Long getName() { return name; }
    @JsonProperty("name")
    public void setName(Long value) { this.name = value; }

    @JsonProperty("link_name")
    public String getLinkName() { return linkName; }
    @JsonProperty("link_name")
    public void setLinkName(String value) { this.linkName = value; }

    @JsonProperty("attr")
    public Object[] getAttr() { return attr; }
    @JsonProperty("attr")
    public void setAttr(Object[] value) { this.attr = value; }
}
