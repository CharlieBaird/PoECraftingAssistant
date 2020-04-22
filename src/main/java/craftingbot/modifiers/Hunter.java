package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Hunter {
    private String id;
    private HunterName name;
    private String code;
    private String level;
    private String modDomainsID;
    private String modGenerationTypeID;
    private String correctGroup;
    private String[] weightList;
    private String statsStr;
    private String dropChance;
    private String str;
    private Object[] masterNo;
    private No[] fossilNo;
    private String[] modNo;
    private Object[] modFossilItem;

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }

    @JsonProperty("Name")
    public HunterName getName() { return name; }
    @JsonProperty("Name")
    public void setName(HunterName value) { this.name = value; }

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

    @JsonProperty("WeightList")
    public String[] getWeightList() { return weightList; }
    @JsonProperty("WeightList")
    public void setWeightList(String[] value) { this.weightList = value; }

    @JsonProperty("StatsStr")
    public String getStatsStr() { return statsStr; }
    @JsonProperty("StatsStr")
    public void setStatsStr(String value) { this.statsStr = value; }

    @JsonProperty("DropChance")
    public String getDropChance() { return dropChance; }
    @JsonProperty("DropChance")
    public void setDropChance(String value) { this.dropChance = value; }

    @JsonProperty("str")
    public String getStr() { return str; }
    @JsonProperty("str")
    public void setStr(String value) { this.str = value; }

    @JsonProperty("master_no")
    public Object[] getMasterNo() { return masterNo; }
    @JsonProperty("master_no")
    public void setMasterNo(Object[] value) { this.masterNo = value; }

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
