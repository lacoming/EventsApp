package com.github.lacoming.eventsapi.mapper;

import com.github.lacoming.eventsapi.entity.Category;
import com.github.lacoming.eventsapi.web.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categories);
}
