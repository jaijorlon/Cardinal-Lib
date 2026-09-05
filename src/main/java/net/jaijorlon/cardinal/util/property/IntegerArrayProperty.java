package net.jaijorlon.cardinal.util.property;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.threetag.palladium.util.property.PalladiumProperty;

import java.util.Arrays;

public class IntegerArrayProperty extends PalladiumProperty<Integer[]> {
    public IntegerArrayProperty(String key) {
        super(key);
    }

    @Override
    public Integer[] fromJSON(JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            return new Integer[]{jsonElement.getAsInt()};
        } else {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Integer[] ints = new Integer[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                ints[i] = jsonArray.get(i).getAsInt();
            }
            return ints;
        }
    }

    @Override
    public JsonElement toJSON(Integer[] value) {
        JsonArray jsonArray = new JsonArray();
        for (Integer i : value) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    @Override
    public Integer[] fromNBT(Tag tag, Integer[] defaultValue) {
        if (tag instanceof ListTag listTag) {
            Integer[] ints = new Integer[listTag.size()];
            for (int i = 0; i < listTag.size(); i++) {
                ints[i] = listTag.getInt(i);
            }
            return ints;
        }
        return defaultValue;
    }

    @Override
    public Tag toNBT(Integer[] value) {
        ListTag listTag = new ListTag();
        for (Integer i : value) {
            listTag.add(IntTag.valueOf(i));
        }
        return listTag;
    }

    @Override
    public Integer[] fromBuffer(FriendlyByteBuf buf) {
        Integer[] ints = new Integer[buf.readInt()];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = buf.readInt();
        }
        return ints;
    }

    @Override
    public void toBuffer(FriendlyByteBuf buf, Object value) {
        Integer[] ints = (Integer[]) value;
        buf.writeInt(ints.length);
        for (Integer i : ints) {
            buf.writeInt(i);
        }
    }

    @Override
    public String getString(Integer[] value) {
        return value == null ? null : Arrays.toString(value);
    }

    @Override
    public String getPropertyType() {
        return "integer_array";
    }
}
