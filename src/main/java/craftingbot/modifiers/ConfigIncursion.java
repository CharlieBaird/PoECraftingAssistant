package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class ConfigIncursion {
    private IncursionType type;
    private String showType;
    private String title;
    private Boolean displayChance;

    @JsonProperty("type")
    public IncursionType getType() { return type; }
    @JsonProperty("type")
    public void setType(IncursionType value) { this.type = value; }

    @JsonProperty("show_type")
    public String getShowType() { return showType; }
    @JsonProperty("show_type")
    public void setShowType(String value) { this.showType = value; }

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("display_chance")
    public Boolean getDisplayChance() { return displayChance; }
    @JsonProperty("display_chance")
    public void setDisplayChance(Boolean value) { this.displayChance = value; }
}
