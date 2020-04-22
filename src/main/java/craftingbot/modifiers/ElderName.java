package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum ElderName {
    ELDRITCH, OF_THE_ELDER;

    @JsonValue
    public String toValue() {
        switch (this) {
        case ELDRITCH: return "Eldritch";
        case OF_THE_ELDER: return "of the Elder";
        }
        return null;
    }

    @JsonCreator
    public static ElderName forValue(String value) throws IOException {
        if (value.equals("Eldritch")) return ELDRITCH;
        if (value.equals("of the Elder")) return OF_THE_ELDER;
        throw new IOException("Cannot deserialize ElderName");
    }
}
