package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class ConfigMaster {
    private MasterType type;
    private String showType;
    private String title;

    @JsonProperty("type")
    public MasterType getType() { return type; }
    @JsonProperty("type")
    public void setType(MasterType value) { this.type = value; }

    @JsonProperty("show_type")
    public String getShowType() { return showType; }
    @JsonProperty("show_type")
    public void setShowType(String value) { this.showType = value; }

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }
}
