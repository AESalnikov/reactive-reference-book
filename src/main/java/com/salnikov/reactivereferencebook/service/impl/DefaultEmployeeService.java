package com.salnikov.reactivereferencebook.service.impl;

import com.salnikov.reactivereferencebook.domain.dto.request.EmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.request.FindEmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.response.EmployeeResponseDto;
import com.salnikov.reactivereferencebook.domain.entity.Employee;
import com.salnikov.reactivereferencebook.domain.mapper.EmployeeMapper;
import com.salnikov.reactivereferencebook.exceptions.EmployeeAlreadyExistsException;
import com.salnikov.reactivereferencebook.exceptions.EmployeeNotFoundException;
import com.salnikov.reactivereferencebook.repository.EmployeeRepository;
import com.salnikov.reactivereferencebook.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Objects.nonNull;

/**
 * Реализация по умолчанию {@link EmployeeService}.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    private static final String LIKE_PATTERN = "%c%s%c";

    @Override
    public Flux<EmployeeResponseDto> findEmployees(FindEmployeeRequestDto request) {
        return employeeRepository.findEmployeeByParameters(
                        nonNull(request.getFirstName())
                                ? LIKE_PATTERN.formatted('%', request.getFirstName(), '%')
                                : "%",
                        nonNull(request.getLastName())
                                ? LIKE_PATTERN.formatted('%', request.getLastName(), '%')
                                : "%",
                        nonNull(request.getMiddleName())
                                ? LIKE_PATTERN.formatted('%', request.getMiddleName(), '%')
                                : "%",
                        nonNull(request.getPositionHeld())
                                ? LIKE_PATTERN.formatted('%', request.getPositionHeld(), '%')
                                : "%",
                        request.getEmail(),
                        request.getPhoneNumber()
                )
                .map(employeeMapper::toDto);
    }

    @Override
    public Mono<EmployeeResponseDto> getEmployee(UUID employeeId) {
        return findEmployeeById(employeeId)
                .map(employeeMapper::toDto);
    }

    @Override
    public Mono<EmployeeResponseDto> createEmployee(EmployeeRequestDto request) {
        final String email = request.getEmail();
        return employeeRepository.findEmployeeByEmailIgnoreCase(email)
                .doOnSuccess(employee -> {
                    throw new EmployeeAlreadyExistsException(
                            "Работник с адресом электронной почты = %s уже существует".formatted(email)
                    );
                })
                .onErrorResume(e -> employeeRepository.save(employeeMapper.fromDto(request)))
                .map(employeeMapper::toDto);
    }

    @Override
    public Mono<EmployeeResponseDto> updateEmployee(UUID employeeId, EmployeeRequestDto request) {
        return findEmployeeById(employeeId)
                .map(employeeToUpdate -> {
                    employeeMapper.merge(employeeToUpdate, employeeMapper.fromDto(request));
                    return employeeToUpdate;
                })
                .flatMap(employeeRepository::save)
                .map(employeeMapper::toDto);
    }

    @Override
    public Mono<Void> deleteEmployee(UUID employeeId) {
        return employeeRepository.deleteById(employeeId);
    }

    private Mono<Employee> findEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .switchIfEmpty(
                        Mono.error(
                                new EmployeeNotFoundException(
                                        "Работник с идентификатором = %s не найден".formatted(employeeId)
                                )
                        )
                );
    }

}
