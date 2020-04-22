package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum WarlordName {
    OF_THE_CONQUEST, WARLORD_S;

    @JsonValue
    public String toValue() {
        switch (this) {
        case OF_THE_CONQUEST: return "of the Conquest";
        case WARLORD_S: return "Warlord's";
        }
        return null;
    }

    @JsonCreator
    public static WarlordName forValue(String value) throws IOException {
        if (value.equals("of the Conquest")) return OF_THE_CONQUEST;
        if (value.equals("Warlord's")) return WARLORD_S;
        throw new IOException("Cannot deserialize WarlordName");
    }
}
