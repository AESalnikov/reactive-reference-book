package com.salnikov.reactivereferencebook.domain.mapper;

import com.salnikov.reactivereferencebook.domain.dto.request.EmployeeRequestDto;
import com.salnikov.reactivereferencebook.domain.dto.response.EmployeeResponseDto;
import com.salnikov.reactivereferencebook.domain.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Маппер информации о работнике.
 * @author Anton Salnikov
 * @since 29.11.2023
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Преобразует сущность {@link Employee>} к {@link EmployeeResponseDto}.
     * @param from источник
     * @return результат
     */
    EmployeeResponseDto toDto(Employee from);

    /**
     * Преобразует {@link EmployeeRequestDto} к сущности {@link Employee}.
     * @param from источник
     * @return результат
     */
    Employee fromDto(EmployeeRequestDto from);

    /**
     * Обновляет данные {@link Employee}.
     * @param toUpdate запись для обновления
     * @param updated  данные обновления
     */
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void merge(@MappingTarget Employee toUpdate, Employee updated);

}
