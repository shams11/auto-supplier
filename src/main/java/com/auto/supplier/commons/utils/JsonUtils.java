package com.auto.supplier.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public JsonUtils() {
  }

  public static <T> String getJsonFromObject(T object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T getObjectFromJson(String json, Class<? extends T> type) throws IOException {
    return objectMapper.readValue(json, type);
  }

  public static <T> T getObjectFromJson(String json, TypeReference<T> type) throws IOException {
    return objectMapper.readValue(json, type);
  }

  public static <T> List<T> getListOfObjectFromJson(String json,
                                                    Class<? extends T> listType) throws IOException {
    return (List)objectMapper.readValue(json,
            objectMapper.getTypeFactory().constructCollectionType(List.class, listType));
  }

  public static <T> T getObjectFromStream(InputStream stream, TypeReference<T> type) throws IOException {
    return objectMapper.readValue(stream, type);
  }

  public static <T> boolean isValidJsonOfListType(String json, Class<? extends T> listType) {
    try {
      getListOfObjectFromJson(json, listType);
      return true;
    } catch (IOException var3) {
      return false;
    }
  }

  public static JsonNode getJsonNodeFromJsonString(String json) throws IOException {
    return objectMapper.readTree(json);
  }
}

