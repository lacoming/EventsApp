package com.github.lacoming.eventsapi.web;


import com.github.lacoming.eventsapi.mapper.EventMapper;
import com.github.lacoming.eventsapi.model.EventFilterModel;
import com.github.lacoming.eventsapi.service.EventService;
import com.github.lacoming.eventsapi.web.dto.EventDto;
import com.github.lacoming.eventsapi.web.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/event")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;

    private final EventMapper eventMapper;

    @GetMapping("/filter")
    public ResponseEntity<PageResponse<EventDto>> getEvents(EventFilterModel filterModel) {
        var page = eventService.filter(filterModel);
        return ResponseEntity.ok(new PageResponse<>(
                page.getTotalElements(),
                page.getTotalPages(),
                eventMapper.toDtoList(page.getContent())
        ));
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getEvent() {
        return ResponseEntity.ok(eventMapper.toDtoList(eventService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventMapper.toDto(eventService.getById(id)));
    }

}
