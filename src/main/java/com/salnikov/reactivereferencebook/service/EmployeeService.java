package com.salnikov.reactivereferencebook.service;

import com.salnikov.reactivereferencebook.domain.dto.request.EmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.request.FindEmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.response.EmployeeResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Сервис управления работниками.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
public interface EmployeeService {

    /**
     * Поиск работников по параметрам.
     * @param request параметры поиска
     * @return найденные работники
     */
    Flux<EmployeeResponseDto> findEmployees(FindEmployeeRequestDto request);

    /**
     * Получить работника.
     * @param employeeId идентификатор работника
     * @return найденный работник
     */
    Mono<EmployeeResponseDto> getEmployee(UUID employeeId);

    /**
     * Создать работника.
     * @param request дто запроса
     * @return созданный работник
     */
    Mono<EmployeeResponseDto> createEmployee(EmployeeRequestDto request);

    /**
     * Обновить работника.
     * @param employeeId идентификатор работника
     * @param request    дто запроса
     * @return обновленный работник
     */
    Mono<EmployeeResponseDto> updateEmployee(UUID employeeId, EmployeeRequestDto request);

    /**
     * Удалить работника.
     * @param employeeId идентификатор работника
     * @return {@link Mono<Void>}
     */
    Mono<Void> deleteEmployee(UUID employeeId);

}
