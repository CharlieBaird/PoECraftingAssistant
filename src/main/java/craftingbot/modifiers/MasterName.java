package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum MasterName {
    HELENA, JUN;

    @JsonValue
    public String toValue() {
        switch (this) {
        case HELENA: return "Helena";
        case JUN: return "Jun";
        }
        return null;
    }

    @JsonCreator
    public static MasterName forValue(String value) throws IOException {
        if (value.equals("Helena")) return HELENA;
        if (value.equals("Jun")) return JUN;
        throw new IOException("Cannot deserialize MasterName");
    }
}
