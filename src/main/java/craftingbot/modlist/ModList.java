/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modlist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 *
 * @author charl
 */
public class ModList {
    private Result[] result;

    @JsonProperty("result")
    public Result[] getResult() { return result; }
    @JsonProperty("result")
    public void setResult(Result[] value) { this.result = value; }
}

class Result {
    private String label;
    private Entry[] entries;

    @JsonProperty("label")
    public String getLabel() { return label; }
    @JsonProperty("label")
    public void setLabel(String value) { this.label = value; }

    @JsonProperty("entries")
    public Entry[] getEntries() { return entries; }
    @JsonProperty("entries")
    public void setEntries(Entry[] value) { this.entries = value; }
}

class Entry {
    private String id;
    private String text;
    private Type type;
    private EntryOption option;

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }

    @JsonProperty("type")
    public Type getType() { return type; }
    @JsonProperty("type")
    public void setType(Type value) { this.type = value; }

    @JsonProperty("option")
    public EntryOption getOption() { return option; }
    @JsonProperty("option")
    public void setOption(EntryOption value) { this.option = value; }
}

class EntryOption {
    private OptionElement[] options;

    @JsonProperty("options")
    public OptionElement[] getOptions() { return options; }
    @JsonProperty("options")
    public void setOptions(OptionElement[] value) { this.options = value; }
}

class OptionElement {
    private ID id;
    private String text;

    @JsonProperty("id")
    public ID getID() { return id; }
    @JsonProperty("id")
    public void setID(ID value) { this.id = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }
}

class ID {
    public Long integerValue;
    public String stringValue;

    static class Deserializer extends JsonDeserializer<ID> {
        @Override
        public ID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            ID value = new ID();
            switch (jsonParser.getCurrentToken()) {
            case VALUE_NUMBER_INT:
                value.integerValue = jsonParser.readValueAs(Long.class);
                break;
            case VALUE_STRING:
                value.stringValue = jsonParser.readValueAs(String.class);
                break;
            default: throw new IOException("Cannot deserialize ID");
            }
            return value;
        }
    }

    static class Serializer extends JsonSerializer<ID> {
        @Override
        public void serialize(ID obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj.integerValue != null) {
                jsonGenerator.writeObject(obj.integerValue);
                return;
            }
            if (obj.stringValue != null) {
                jsonGenerator.writeObject(obj.stringValue);
                return;
            }
            throw new IOException("ID must not be null");
        }
    }
}

enum Type {
    CRAFTED, DELVE, ENCHANT, EXPLICIT, FRACTURED, IMPLICIT, MONSTER, PSEUDO, VEILED;

    @JsonValue
    public String toValue() {
        switch (this) {
        case CRAFTED: return "crafted";
        case DELVE: return "delve";
        case ENCHANT: return "enchant";
        case EXPLICIT: return "explicit";
        case FRACTURED: return "fractured";
        case IMPLICIT: return "implicit";
        case MONSTER: return "monster";
        case PSEUDO: return "pseudo";
        case VEILED: return "veiled";
        }
        return null;
    }

    @JsonCreator
    public static Type forValue(String value) throws IOException {
        if (value.equals("crafted")) return CRAFTED;
        if (value.equals("delve")) return DELVE;
        if (value.equals("enchant")) return ENCHANT;
        if (value.equals("explicit")) return EXPLICIT;
        if (value.equals("fractured")) return FRACTURED;
        if (value.equals("implicit")) return IMPLICIT;
        if (value.equals("monster")) return MONSTER;
        if (value.equals("pseudo")) return PSEUDO;
        if (value.equals("veiled")) return VEILED;
        throw new IOException("Cannot deserialize Type");
    }
}