package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class ConfigNormal {
    private String showType;
    private String title;
    private Boolean displayTier;
    private Boolean displayChance;

    @JsonProperty("show_type")
    public String getShowType() { return showType; }
    @JsonProperty("show_type")
    public void setShowType(String value) { this.showType = value; }

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("display_tier")
    public Boolean getDisplayTier() { return displayTier; }
    @JsonProperty("display_tier")
    public void setDisplayTier(Boolean value) { this.displayTier = value; }

    @JsonProperty("display_chance")
    public Boolean getDisplayChance() { return displayChance; }
    @JsonProperty("display_chance")
    public void setDisplayChance(Boolean value) { this.displayChance = value; }
}
