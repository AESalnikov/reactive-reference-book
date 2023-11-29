package com.salnikov.reactivereferencebook.controller;

import com.salnikov.reactivereferencebook.domain.dto.request.EmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.request.FindEmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.response.EmployeeResponseDto;
import com.salnikov.reactivereferencebook.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Контроллер для управления работниками.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Validated
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Поиск работников по параметрам.
     * @param request  параметры поиска
     * @return страница с найденными работниками
     */
    @GetMapping
    public ResponseEntity<Flux<EmployeeResponseDto>> findEmployees(final FindEmployeeRequestDto request) {
        return ResponseEntity.ok(
                employeeService.findEmployees(request)
        );
    }

    /**
     * Получение информации о работника.
     * @param employeeId идентификатор работника
     * @return найденная информация о работнике
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<Mono<EmployeeResponseDto>> getEmployee(
            @NotNull
            @PathVariable
            final UUID employeeId
    ) {
        return ResponseEntity.ok(
                employeeService.getEmployee(employeeId)
        );
    }

    /**
     * Создание работника.
     * @param request информация о работнике
     * @return созданный работник
     */
    @PostMapping
    public ResponseEntity<Mono<EmployeeResponseDto>> createEmployee(
            @Valid
            @RequestBody
            final EmployeeRequestDto request
    ) {
        return ResponseEntity.ok(
                employeeService.createEmployee(request)
        );
    }

    /**
     * Обновление информации о работнике.
     * @param employeeId идентификатор работника
     * @param request    информация о работнике
     * @return обновленная информация о работнике
     */
    @PatchMapping("/{employeeId}")
    public ResponseEntity<Mono<EmployeeResponseDto>> updateEmployee(
            @NotNull
            @PathVariable
            final UUID employeeId,

            @Valid
            @RequestBody
            final EmployeeRequestDto request
    ) {
        return ResponseEntity.ok(
                employeeService.updateEmployee(employeeId, request)
        );
    }

    /**
     * Удаление работника.
     * @param employeeId идентификатор работника
     * @return статус удаления информации о работнике
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Mono<Void>> deleteEmployee(
            @NotNull
            @PathVariable
            final UUID employeeId
    ) {
        return ResponseEntity.ok(
                employeeService.deleteEmployee(employeeId)
        );
    }

}
