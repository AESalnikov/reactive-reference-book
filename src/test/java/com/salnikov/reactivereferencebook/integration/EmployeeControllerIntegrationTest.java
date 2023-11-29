package com.salnikov.reactivereferencebook.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.salnikov.reactivereferencebook.abstracts.AbstractIntegrationTest;
import com.salnikov.reactivereferencebook.controller.EmployeeController;
import com.salnikov.reactivereferencebook.domain.dto.request.EmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.request.FindEmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.response.EmployeeResponseDto;
import com.salnikov.reactivereferencebook.domain.entity.Employee;
import com.salnikov.reactivereferencebook.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Интеграционный тест для контроллера {@link EmployeeController}.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
class EmployeeControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findEmployees_shouldReturnExpectedResponse() {
        final List<EmployeeResponseDto> expected = mapToObject("/expected/find.json", new TypeReference<>() {});
        final FindEmployeeRequestDto request = mapToObject("/request/find.json", new TypeReference<>() {});
        final Employee employee = mapToObject("/entity/find.json", new TypeReference<>() {});

        employeeRepository.deleteAll().block();
        employeeRepository.save(employee).block();
        assertThat(employeeRepository.count().block())
                .isEqualTo(1);

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/employees")
                        .queryParam("firstName", request.getFirstName())
                        .build()
                )
                .headers(header -> header.setContentType(APPLICATION_JSON))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<List<EmployeeResponseDto>>() {})
                .value(body -> assertThat(body)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expected));
    }


    @Test
    void getEmployee_shouldReturnExpectedResponse() {
        final EmployeeResponseDto expected = mapToObject("/expected/get.json", new TypeReference<>() {});
        final Employee employee = mapToObject("/entity/get.json", new TypeReference<>() {});

        employeeRepository.deleteAll().block();
        final var employeeId = requireNonNull(employeeRepository.save(employee).block()).getId();
        assertThat(employeeRepository.count().block())
                .isEqualTo(1);

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/employees/{employeeId}").build(employeeId))
                .headers(header -> header.setContentType(APPLICATION_JSON))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeResponseDto.class)
                .value(body -> assertThat(body)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expected));
    }

    @Test
    void createEmployee_shouldReturnExpectedResponse() {
        final EmployeeResponseDto expected = mapToObject("/expected/post.json", new TypeReference<>() {});
        final EmployeeRequestDto request = mapToObject("/request/post.json", new TypeReference<>() {});

        employeeRepository.deleteAll().block();

        webClient.post()
                .uri("/api/employees")
                .headers(header -> header.setContentType(APPLICATION_JSON))
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeResponseDto.class)
                .value(body -> assertThat(body)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expected));

        assertThat(employeeRepository.count().block())
                .isEqualTo(1);
    }

    @Test
    void updateEmployee_shouldReturnExpectedResponse() {
        final EmployeeResponseDto expected = mapToObject("/expected/patch.json", new TypeReference<>() {});
        final EmployeeRequestDto request = mapToObject("/request/patch.json", new TypeReference<>() {});
        final Employee employee = mapToObject("/entity/patch.json", new TypeReference<>() {});

        employeeRepository.deleteAll().block();
        final var employeeId = requireNonNull(employeeRepository.save(employee).block()).getId();
        assertThat(employeeRepository.count().block())
                .isEqualTo(1);

        webClient.patch()
                .uri(uriBuilder -> uriBuilder.path("/api/employees/{employeeId}").build(employeeId))
                .headers(header -> header.setContentType(APPLICATION_JSON))
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeResponseDto.class)
                .value(body -> assertThat(body)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(expected));
    }

    @Test
    void deleteEmployee_shouldReturnExpectedResponse() {
        final Employee employee = mapToObject("/entity/delete.json", new TypeReference<>() {});

        employeeRepository.deleteAll().block();
        final var employeeId = requireNonNull(employeeRepository.save(employee).block()).getId();
        assertThat(employeeRepository.count().block())
                .isEqualTo(1);

        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/employees/{employeeId}").build(employeeId))
                .headers(header -> header.setContentType(APPLICATION_JSON))
                .exchange()
                .expectStatus()
                .isOk();

        assertThat(employeeRepository.findById(employeeId).block())
                .isNull();
    }

}
