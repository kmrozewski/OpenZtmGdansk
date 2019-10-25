package com.opengdansk.search.reader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonReader {

    private static final String ENCODING = "UTF-8";

    public static <T> T parseFixture(Class className, Class<T> outputClass, String fileName) {
        try {
            return new ObjectMapper().readValue(readFixtureToString(className, fileName), outputClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, U extends Collection> List<T> parseFixtureToCollection(Class className,
                                                                             Class<T> outputClass,
                                                                             Class<U> collectionClass,
                                                                             String fileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return new ObjectMapper().readValue(
                    readFixtureToString(className, fileName),
                    objectMapper.getTypeFactory().constructCollectionType(collectionClass, outputClass));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, T> parseFixtureToMap(Class className, Class<T> outputClass, String fileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JavaType stringType = objectMapper.getTypeFactory().constructType(String.class);
            JavaType customType = objectMapper.getTypeFactory().constructType(outputClass);
            JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, stringType, customType);

            return new ObjectMapper().readValue(readFixtureToString(className, fileName), mapType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFixtureToString(Class className, String fileName) {
        try (InputStream inputStream = className.getClassLoader().getResourceAsStream(fileName)) {
            return IOUtils.toString(inputStream, ENCODING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
