package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;

@JsonDeserialize(using = WeightList.Deserializer.class)
@JsonSerialize(using = WeightList.Serializer.class)
public class WeightList {
    public String[] stringArrayValue;
    public String stringValue;

    static class Deserializer extends JsonDeserializer<WeightList> {
        @Override
        public WeightList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            WeightList value = new WeightList();
            switch (jsonParser.getCurrentToken()) {
            case VALUE_NULL:
                break;
            case VALUE_STRING:
                value.stringValue = jsonParser.readValueAs(String.class);
                break;
            case START_ARRAY:
                value.stringArrayValue = jsonParser.readValueAs(String[].class);
                break;
            default: throw new IOException("Cannot deserialize WeightList");
            }
            return value;
        }
    }

    static class Serializer extends JsonSerializer<WeightList> {
        @Override
        public void serialize(WeightList obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj.stringArrayValue != null) {
                jsonGenerator.writeObject(obj.stringArrayValue);
                return;
            }
            if (obj.stringValue != null) {
                jsonGenerator.writeObject(obj.stringValue);
                return;
            }
            jsonGenerator.writeNull();
        }
    }
}
