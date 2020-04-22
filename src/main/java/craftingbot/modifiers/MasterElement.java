package craftingbot.modifiers;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class MasterElement {
    private String npcMasterID;
    private String id;
    private String modGenerationTypeID;
    private String level;
    private String correctGroup;
    private String modn;
    private MasterName name;
    private MasterType type;
    private String str;
    private Object[] masterNo;
    private No[] fossilNo;
    private String[] modNo;
    private Object[] modFossilItem;

    @JsonProperty("NPCMasterID")
    public String getNpcMasterID() { return npcMasterID; }
    @JsonProperty("NPCMasterID")
    public void setNpcMasterID(String value) { this.npcMasterID = value; }

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }

    @JsonProperty("ModGenerationTypeID")
    public String getModGenerationTypeID() { return modGenerationTypeID; }
    @JsonProperty("ModGenerationTypeID")
    public void setModGenerationTypeID(String value) { this.modGenerationTypeID = value; }

    @JsonProperty("Level")
    public String getLevel() { return level; }
    @JsonProperty("Level")
    public void setLevel(String value) { this.level = value; }

    @JsonProperty("CorrectGroup")
    public String getCorrectGroup() { return correctGroup; }
    @JsonProperty("CorrectGroup")
    public void setCorrectGroup(String value) { this.correctGroup = value; }

    @JsonProperty("modn")
    public String getModn() { return modn; }
    @JsonProperty("modn")
    public void setModn(String value) { this.modn = value; }

    @JsonProperty("Name")
    public MasterName getName() { return name; }
    @JsonProperty("Name")
    public void setName(MasterName value) { this.name = value; }

    @JsonProperty("type")
    public MasterType getType() { return type; }
    @JsonProperty("type")
    public void setType(MasterType value) { this.type = value; }

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
