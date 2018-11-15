package com.example.xxx.tata.Utils;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public abstract class JsonUtils {

    public static Gson getGson(Type type) {
        return new GsonBuilder()
                .registerTypeAdapter(type, new GsonRootSerializer())
                .create();
    }

    static class GsonRootSerializer implements JsonSerializer<Object> {
        @Override
        public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
            Gson gson = new Gson();
            JsonElement je = gson.toJsonTree(src);
            JsonObject jo = new JsonObject();
            jo.add(src.getClass().getAnnotation(JsonRootName.class).value(), je);
            return jo;
        }
    }
}
