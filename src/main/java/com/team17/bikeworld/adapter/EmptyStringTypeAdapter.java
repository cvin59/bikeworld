package com.team17.bikeworld.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

public final class EmptyStringTypeAdapter<T>
        implements JsonDeserializer<T> {

    // Let Gson instantiate it itself
    private EmptyStringTypeAdapter() {
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
            throws JsonParseException {
        if ( jsonElement.isJsonPrimitive() ) {
            final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if ( jsonPrimitive.isString() && jsonPrimitive.getAsString().isEmpty() ) {
                return null;
            }
        }
        return context.deserialize(jsonElement, type);
    }

}
