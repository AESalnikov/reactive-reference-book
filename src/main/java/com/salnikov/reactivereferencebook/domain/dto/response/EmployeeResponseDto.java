package com.salnikov.reactivereferencebook.domain.dto.response;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Дто ответа с информацией о работнике.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Value
public class EmployeeResponseDto {

    /**
     * Идентификатор.
     */
    @Id
    UUID id;

    /**
     * Имя.
     */
    String firstName;

    /**
     * Фамилия.
     */
    String lastName;

    /**
     * Отчество.
     */
    String middleName;

    /**
     * Должность.
     */
    String positionHeld;

    /**
     * Адрес электронной почты.
     */
    String email;

    /**
     * Номер телефона.
     */
    String phoneNumber;

}
