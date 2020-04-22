package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum IncursionName {
    GUATELITZI_S, OF_GUATELITZI, OF_PUHUARTE, XOPEC_S;

    @JsonValue
    public String toValue() {
        switch (this) {
        case GUATELITZI_S: return "Guatelitzi's";
        case OF_GUATELITZI: return "of Guatelitzi";
        case OF_PUHUARTE: return "of Puhuarte";
        case XOPEC_S: return "Xopec's";
        }
        return null;
    }

    @JsonCreator
    public static IncursionName forValue(String value) throws IOException {
        if (value.equals("Guatelitzi's")) return GUATELITZI_S;
        if (value.equals("of Guatelitzi")) return OF_GUATELITZI;
        if (value.equals("of Puhuarte")) return OF_PUHUARTE;
        if (value.equals("Xopec's")) return XOPEC_S;
        throw new IOException("Cannot deserialize IncursionName");
    }
}
