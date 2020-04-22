package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum CrusaderName {
    CRUSADER_S, OF_THE_CRUSADE;

    @JsonValue
    public String toValue() {
        switch (this) {
        case CRUSADER_S: return "Crusader's";
        case OF_THE_CRUSADE: return "of the Crusade";
        }
        return null;
    }

    @JsonCreator
    public static CrusaderName forValue(String value) throws IOException {
        if (value.equals("Crusader's")) return CRUSADER_S;
        if (value.equals("of the Crusade")) return OF_THE_CRUSADE;
        throw new IOException("Cannot deserialize CrusaderName");
    }
}
