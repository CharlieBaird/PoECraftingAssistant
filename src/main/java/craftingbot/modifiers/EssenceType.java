package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum EssenceType {
    ESSENCE;

    @JsonValue
    public String toValue() {
        switch (this) {
        case ESSENCE: return "essence";
        }
        return null;
    }

    @JsonCreator
    public static EssenceType forValue(String value) throws IOException {
        if (value.equals("essence")) return ESSENCE;
        throw new IOException("Cannot deserialize EssenceType");
    }
}
