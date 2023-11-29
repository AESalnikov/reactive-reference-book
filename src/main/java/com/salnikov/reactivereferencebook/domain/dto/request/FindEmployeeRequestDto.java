package com.salnikov.reactivereferencebook.domain.dto.request;

import lombok.Value;

/**
 * Параметры поиска работника.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Value
public class FindEmployeeRequestDto {

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
