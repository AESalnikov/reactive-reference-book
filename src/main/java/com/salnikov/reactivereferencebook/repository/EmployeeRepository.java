package com.salnikov.reactivereferencebook.repository;

import com.salnikov.reactivereferencebook.domain.entity.Employee;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Репозиторий с работниками.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, UUID> {

    /**
     * Поиск работников по параметрам.
     * @param firstName    имя
     * @param lastName     фамилия
     * @param middleName   отчество
     * @param positionHeld занимаемая должность
     * @param email        адрес электронной почты
     * @param phoneNumber  номер телефона
     * @return найденные пользователи
     */
    @Query(value = """
            SELECT e.id, e.first_name, e.last_name, e.middle_name, e.position_held, e.phone_number, e.email
            FROM employee_reference_book e
            WHERE e.first_name LIKE :firstName
                AND e.last_name LIKE :lastName
                AND e.middle_name LIKE :middleName
                AND e.position_held LIKE :positionHeld
                AND (:email IS NULL OR e.email = :email)
                AND (:phoneNumber IS NULL OR e.phone_number = :phoneNumber)
            """)
    Flux<Employee> findEmployeeByParameters(
            String firstName,
            String lastName,
            String middleName,
            String positionHeld,
            String email,
            String phoneNumber
    );

    /**
     * Поиск работника по адресу электронной почты.
     * @param email адрес электронной почты
     * @return найденный работник
     */
    Mono<Employee> findEmployeeByEmailIgnoreCase(String email);

}
