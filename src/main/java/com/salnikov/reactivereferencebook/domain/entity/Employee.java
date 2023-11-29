package com.salnikov.reactivereferencebook.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Информация о работнике.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Getter
@Setter
@ToString
@Table("employee_reference_book")
public class Employee {

    /**
     * Идентификатор.
     */
    @Id
    private UUID id;

    /**
     * Имя.
     */
    private String firstName;

    /**
     * Фамилия.
     */
    private String lastName;

    /**
     * Отчество.
     */
    private String middleName;

    /**
     * Должность.
     */
    private String positionHeld;

    /**
     * Адрес электронной почты.
     */
    private String email;

    /**
     * Номер телефона.
     */
    private String phoneNumber;

}
