package com.salnikov.reactivereferencebook.abstracts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Абстрактный класс для тестов.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
public abstract class AbstractTest {

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> T mapToObject(final String path, final TypeReference<T> reference) {
        try (InputStream resource = new ClassPathResource(path).getInputStream()) {
            return objectMapper.readValue(resource, reference);
        } catch (IOException e) {
            throw new IllegalStateException("Can't read resource from the classpath", e);
        }
    }

}
