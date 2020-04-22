package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Modifier {
    private Baseitem baseitem;
    private Config config;
    private Map<String, String> gen;
    private NormalElement[] normal;
    private MasterElement[] master;
    private EssenceElement[] essence;
    private Delve[] delve;
    private IncursionElement[] incursion;
    private Map<String, Elder> elder;
    private Map<String, Shaper> shaper;
    private Map<String, CrusaderValue> crusader;
    private Map<String, Redeemer> redeemer;
    private Map<String, Hunter> hunter;
    private Map<String, Warlord> warlord;
    private Long cached;

    @JsonProperty("baseitem")
    public Baseitem getBaseitem() { return baseitem; }
    @JsonProperty("baseitem")
    public void setBaseitem(Baseitem value) { this.baseitem = value; }

    @JsonProperty("config")
    public Config getConfig() { return config; }
    @JsonProperty("config")
    public void setConfig(Config value) { this.config = value; }

    @JsonProperty("gen")
    public Map<String, String> getGen() { return gen; }
    @JsonProperty("gen")
    public void setGen(Map<String, String> value) { this.gen = value; }

    @JsonProperty("normal")
    public NormalElement[] getNormal() { return normal; }
    @JsonProperty("normal")
    public void setNormal(NormalElement[] value) { this.normal = value; }

    @JsonProperty("master")
    public MasterElement[] getMaster() { return master; }
    @JsonProperty("master")
    public void setMaster(MasterElement[] value) { this.master = value; }

    @JsonProperty("essence")
    public EssenceElement[] getEssence() { return essence; }
    @JsonProperty("essence")
    public void setEssence(EssenceElement[] value) { this.essence = value; }

    @JsonProperty("delve")
    public Delve[] getDelve() { return delve; }
    @JsonProperty("delve")
    public void setDelve(Delve[] value) { this.delve = value; }

    @JsonProperty("incursion")
    public IncursionElement[] getIncursion() { return incursion; }
    @JsonProperty("incursion")
    public void setIncursion(IncursionElement[] value) { this.incursion = value; }

    @JsonProperty("elder")
    public Map<String, Elder> getElder() { return elder; }
    @JsonProperty("elder")
    public void setElder(Map<String, Elder> value) { this.elder = value; }

    @JsonProperty("shaper")
    public Map<String, Shaper> getShaper() { return shaper; }
    @JsonProperty("shaper")
    public void setShaper(Map<String, Shaper> value) { this.shaper = value; }

    @JsonProperty("crusader")
    public Map<String, CrusaderValue> getCrusader() { return crusader; }
    @JsonProperty("crusader")
    public void setCrusader(Map<String, CrusaderValue> value) { this.crusader = value; }

    @JsonProperty("redeemer")
    public Map<String, Redeemer> getRedeemer() { return redeemer; }
    @JsonProperty("redeemer")
    public void setRedeemer(Map<String, Redeemer> value) { this.redeemer = value; }

    @JsonProperty("hunter")
    public Map<String, Hunter> getHunter() { return hunter; }
    @JsonProperty("hunter")
    public void setHunter(Map<String, Hunter> value) { this.hunter = value; }

    @JsonProperty("warlord")
    public Map<String, Warlord> getWarlord() { return warlord; }
    @JsonProperty("warlord")
    public void setWarlord(Map<String, Warlord> value) { this.warlord = value; }

    @JsonProperty("cached")
    public Long getCached() { return cached; }
    @JsonProperty("cached")
    public void setCached(Long value) { this.cached = value; }
}
