package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum RedeemerName {
    OF_REDEMPTION, REDEEMER_S;

    @JsonValue
    public String toValue() {
        switch (this) {
        case OF_REDEMPTION: return "of Redemption";
        case REDEEMER_S: return "Redeemer's";
        }
        return null;
    }

    @JsonCreator
    public static RedeemerName forValue(String value) throws IOException {
        if (value.equals("of Redemption")) return OF_REDEMPTION;
        if (value.equals("Redeemer's")) return REDEEMER_S;
        throw new IOException("Cannot deserialize RedeemerName");
    }
}
