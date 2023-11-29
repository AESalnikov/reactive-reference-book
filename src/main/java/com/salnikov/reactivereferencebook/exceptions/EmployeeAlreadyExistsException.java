package com.salnikov.reactivereferencebook.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Исключение для обработки ситуации, когда работник уже существует.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@ResponseStatus(CONFLICT)
public class EmployeeAlreadyExistsException extends RuntimeException {

    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }

}
