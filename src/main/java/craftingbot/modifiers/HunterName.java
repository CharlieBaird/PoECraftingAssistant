package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum HunterName {
    HUNTER_S, OF_THE_HUNT;

    @JsonValue
    public String toValue() {
        switch (this) {
        case HUNTER_S: return "Hunter's";
        case OF_THE_HUNT: return "of the Hunt";
        }
        return null;
    }

    @JsonCreator
    public static HunterName forValue(String value) throws IOException {
        if (value.equals("Hunter's")) return HUNTER_S;
        if (value.equals("of the Hunt")) return OF_THE_HUNT;
        throw new IOException("Cannot deserialize HunterName");
    }
}
