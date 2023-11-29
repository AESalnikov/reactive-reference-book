package com.salnikov.reactivereferencebook.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Исключение для обработки ситуации, когда работник не найден.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@ResponseStatus(NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
