package craftingbot.modifiers;

import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum No {
    ATTACK, AURA, BLEED, CASTER, CHAOS, COLD, DEFENCES, ELEMENTAL, FIRE, FLAT_LIFE_REGEN, GEM_LEVEL, JEWELLERY_ATTACK, JEWELLERY_ATTRIBUTE, JEWELLERY_CASTER, JEWELLERY_DEFENSE, JEWELLERY_ELEMENTAL, JEWELLERY_QUALITY_IGNORE, JEWELLERY_RESISTANCE, JEWELLERY_RESOURCE, LIFE, LIGHTNING, MANA, MINION, PHYSICAL, POISON, SPEED;

    @JsonValue
    public String toValue() {
        switch (this) {
        case ATTACK: return "attack";
        case AURA: return "aura";
        case BLEED: return "bleed";
        case CASTER: return "caster";
        case CHAOS: return "chaos";
        case COLD: return "cold";
        case DEFENCES: return "defences";
        case ELEMENTAL: return "elemental";
        case FIRE: return "fire";
        case FLAT_LIFE_REGEN: return "flat_life_regen";
        case GEM_LEVEL: return "gem_level";
        case JEWELLERY_ATTACK: return "jewellery_attack";
        case JEWELLERY_ATTRIBUTE: return "jewellery_attribute";
        case JEWELLERY_CASTER: return "jewellery_caster";
        case JEWELLERY_DEFENSE: return "jewellery_defense";
        case JEWELLERY_ELEMENTAL: return "jewellery_elemental";
        case JEWELLERY_QUALITY_IGNORE: return "jewellery_quality_ignore";
        case JEWELLERY_RESISTANCE: return "jewellery_resistance";
        case JEWELLERY_RESOURCE: return "jewellery_resource";
        case LIFE: return "life";
        case LIGHTNING: return "lightning";
        case MANA: return "mana";
        case MINION: return "minion";
        case PHYSICAL: return "physical";
        case POISON: return "poison";
        case SPEED: return "speed";
        }
        return null;
    }

    @JsonCreator
    public static No forValue(String value) throws IOException {
        if (value.equals("attack")) return ATTACK;
        if (value.equals("aura")) return AURA;
        if (value.equals("bleed")) return BLEED;
        if (value.equals("caster")) return CASTER;
        if (value.equals("chaos")) return CHAOS;
        if (value.equals("cold")) return COLD;
        if (value.equals("defences")) return DEFENCES;
        if (value.equals("elemental")) return ELEMENTAL;
        if (value.equals("fire")) return FIRE;
        if (value.equals("flat_life_regen")) return FLAT_LIFE_REGEN;
        if (value.equals("gem_level")) return GEM_LEVEL;
        if (value.equals("jewellery_attack")) return JEWELLERY_ATTACK;
        if (value.equals("jewellery_attribute")) return JEWELLERY_ATTRIBUTE;
        if (value.equals("jewellery_caster")) return JEWELLERY_CASTER;
        if (value.equals("jewellery_defense")) return JEWELLERY_DEFENSE;
        if (value.equals("jewellery_elemental")) return JEWELLERY_ELEMENTAL;
        if (value.equals("jewellery_quality_ignore")) return JEWELLERY_QUALITY_IGNORE;
        if (value.equals("jewellery_resistance")) return JEWELLERY_RESISTANCE;
        if (value.equals("jewellery_resource")) return JEWELLERY_RESOURCE;
        if (value.equals("life")) return LIFE;
        if (value.equals("lightning")) return LIGHTNING;
        if (value.equals("mana")) return MANA;
        if (value.equals("minion")) return MINION;
        if (value.equals("physical")) return PHYSICAL;
        if (value.equals("poison")) return POISON;
        if (value.equals("speed")) return SPEED;
        throw new IOException("Cannot deserialize No");
    }
}
