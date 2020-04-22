package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Config {
    private ConfigNormal normal;
    private ConfigMaster master;
    private ConfigEssence essence;
    private DelveClass delve;
    private ConfigIncursion incursion;
    private DelveClass shaper;
    private DelveClass elder;
    private DelveClass crusader;
    private DelveClass redeemer;
    private DelveClass hunter;
    private DelveClass warlord;

    @JsonProperty("normal")
    public ConfigNormal getNormal() { return normal; }
    @JsonProperty("normal")
    public void setNormal(ConfigNormal value) { this.normal = value; }

    @JsonProperty("master")
    public ConfigMaster getMaster() { return master; }
    @JsonProperty("master")
    public void setMaster(ConfigMaster value) { this.master = value; }

    @JsonProperty("essence")
    public ConfigEssence getEssence() { return essence; }
    @JsonProperty("essence")
    public void setEssence(ConfigEssence value) { this.essence = value; }

    @JsonProperty("delve")
    public DelveClass getDelve() { return delve; }
    @JsonProperty("delve")
    public void setDelve(DelveClass value) { this.delve = value; }

    @JsonProperty("incursion")
    public ConfigIncursion getIncursion() { return incursion; }
    @JsonProperty("incursion")
    public void setIncursion(ConfigIncursion value) { this.incursion = value; }

    @JsonProperty("shaper")
    public DelveClass getShaper() { return shaper; }
    @JsonProperty("shaper")
    public void setShaper(DelveClass value) { this.shaper = value; }

    @JsonProperty("elder")
    public DelveClass getElder() { return elder; }
    @JsonProperty("elder")
    public void setElder(DelveClass value) { this.elder = value; }

    @JsonProperty("crusader")
    public DelveClass getCrusader() { return crusader; }
    @JsonProperty("crusader")
    public void setCrusader(DelveClass value) { this.crusader = value; }

    @JsonProperty("redeemer")
    public DelveClass getRedeemer() { return redeemer; }
    @JsonProperty("redeemer")
    public void setRedeemer(DelveClass value) { this.redeemer = value; }

    @JsonProperty("hunter")
    public DelveClass getHunter() { return hunter; }
    @JsonProperty("hunter")
    public void setHunter(DelveClass value) { this.hunter = value; }

    @JsonProperty("warlord")
    public DelveClass getWarlord() { return warlord; }
    @JsonProperty("warlord")
    public void setWarlord(DelveClass value) { this.warlord = value; }
}
