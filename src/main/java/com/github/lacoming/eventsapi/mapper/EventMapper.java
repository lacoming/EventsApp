package com.github.lacoming.eventsapi.mapper;

import com.github.lacoming.eventsapi.entity.Category;
import com.github.lacoming.eventsapi.entity.Event;
import com.github.lacoming.eventsapi.web.dto.CreateEventRequest;
import com.github.lacoming.eventsapi.web.dto.EventDto;
import com.github.lacoming.eventsapi.web.dto.UpdateEventRequest;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventMapper {

    @Mapping(target = "schedule.description", source = "schedule")
    @Mapping(target = "location.city", source = "cityLocation")
    @Mapping(target = "location.street", source = "streetLocation")
    @Mapping(target = "organization.id", source = "organizationId")
    @Mapping(target = "organization.owner.id", source = "creatorId")
    Event toEntity(CreateEventRequest request);

    @Mapping(target = "schedule.description", source = "schedule")
    Event toEntity(UpdateEventRequest request);

    EventDto toDto(Event event);

    List<EventDto> toDtoList(List<Event> events);

    @IterableMapping(qualifiedByName = "mapToCategory")
    Set<Category> mapToCategories(Set<String> categories);

    @Named("mapToCategory")
    default Category mapToCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        return category;
    }

}
