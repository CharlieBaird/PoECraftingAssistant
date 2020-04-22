package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum ShaperName {
    OF_SHAPING, THE_SHAPER_S;

    @JsonValue
    public String toValue() {
        switch (this) {
        case OF_SHAPING: return "of Shaping";
        case THE_SHAPER_S: return "The Shaper's";
        }
        return null;
    }

    @JsonCreator
    public static ShaperName forValue(String value) throws IOException {
        if (value.equals("of Shaping")) return OF_SHAPING;
        if (value.equals("The Shaper's")) return THE_SHAPER_S;
        throw new IOException("Cannot deserialize ShaperName");
    }
}
