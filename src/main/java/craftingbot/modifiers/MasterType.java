package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum MasterType {
    MASTER;

    @JsonValue
    public String toValue() {
        switch (this) {
        case MASTER: return "master";
        }
        return null;
    }

    @JsonCreator
    public static MasterType forValue(String value) throws IOException {
        if (value.equals("master")) return MASTER;
        throw new IOException("Cannot deserialize MasterType");
    }
}
