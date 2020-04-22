package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum IncursionType {
    INCURSION;

    @JsonValue
    public String toValue() {
        switch (this) {
        case INCURSION: return "incursion";
        }
        return null;
    }

    @JsonCreator
    public static IncursionType forValue(String value) throws IOException {
        if (value.equals("incursion")) return INCURSION;
        throw new IOException("Cannot deserialize IncursionType");
    }
}
