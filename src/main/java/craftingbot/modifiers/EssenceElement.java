package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class EssenceElement {
    private String tier;
    private String baseItemTypesID;
    private String amuletModsID;
    private String id;
    private String name;
    private String code;
    private String level;
    private String modDomainsID;
    private String modGenerationTypeID;
    private String correctGroup;
    private EssenceType type;
    private String tierName;
    private String str;
    private No[] masterNo;
    private No[] fossilNo;
    private String[] modNo;
    private Object[] modFossilItem;

    @JsonProperty("Tier")
    public String getTier() { return tier; }
    @JsonProperty("Tier")
    public void setTier(String value) { this.tier = value; }

    @JsonProperty("BaseItemTypesID")
    public String getBaseItemTypesID() { return baseItemTypesID; }
    @JsonProperty("BaseItemTypesID")
    public void setBaseItemTypesID(String value) { this.baseItemTypesID = value; }

    @JsonProperty("AmuletModsID")
    public String getAmuletModsID() { return amuletModsID; }
    @JsonProperty("AmuletModsID")
    public void setAmuletModsID(String value) { this.amuletModsID = value; }

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }

    @JsonProperty("Name")
    public String getName() { return name; }
    @JsonProperty("Name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("Code")
    public String getCode() { return code; }
    @JsonProperty("Code")
    public void setCode(String value) { this.code = value; }

    @JsonProperty("Level")
    public String getLevel() { return level; }
    @JsonProperty("Level")
    public void setLevel(String value) { this.level = value; }

    @JsonProperty("ModDomainsID")
    public String getModDomainsID() { return modDomainsID; }
    @JsonProperty("ModDomainsID")
    public void setModDomainsID(String value) { this.modDomainsID = value; }

    @JsonProperty("ModGenerationTypeID")
    public String getModGenerationTypeID() { return modGenerationTypeID; }
    @JsonProperty("ModGenerationTypeID")
    public void setModGenerationTypeID(String value) { this.modGenerationTypeID = value; }

    @JsonProperty("CorrectGroup")
    public String getCorrectGroup() { return correctGroup; }
    @JsonProperty("CorrectGroup")
    public void setCorrectGroup(String value) { this.correctGroup = value; }

    @JsonProperty("type")
    public EssenceType getType() { return type; }
    @JsonProperty("type")
    public void setType(EssenceType value) { this.type = value; }

    @JsonProperty("TierName")
    public String getTierName() { return tierName; }
    @JsonProperty("TierName")
    public void setTierName(String value) { this.tierName = value; }

    @JsonProperty("str")
    public String getStr() { return str; }
    @JsonProperty("str")
    public void setStr(String value) { this.str = value; }

    @JsonProperty("master_no")
    public No[] getMasterNo() { return masterNo; }
    @JsonProperty("master_no")
    public void setMasterNo(No[] value) { this.masterNo = value; }

    @JsonProperty("fossil_no")
    public No[] getFossilNo() { return fossilNo; }
    @JsonProperty("fossil_no")
    public void setFossilNo(No[] value) { this.fossilNo = value; }

    @JsonProperty("mod_no")
    public String[] getModNo() { return modNo; }
    @JsonProperty("mod_no")
    public void setModNo(String[] value) { this.modNo = value; }

    @JsonProperty("mod_fossil_item")
    public Object[] getModFossilItem() { return modFossilItem; }
    @JsonProperty("mod_fossil_item")
    public void setModFossilItem(Object[] value) { this.modFossilItem = value; }
}
