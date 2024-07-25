package com.github.lacoming.eventsapi.web;

import com.github.lacoming.eventsapi.mapper.OrganizationMapper;
import com.github.lacoming.eventsapi.service.OrganizationService;
import com.github.lacoming.eventsapi.web.dto.OrganizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/organization")
@RequiredArgsConstructor
public class PublicOrganizationController {

    private final OrganizationService organizationService;

    private final OrganizationMapper organizationMapper;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable Long id) {
        return ResponseEntity.ok(organizationMapper.toDto(organizationService.findById(id)));
    }

}
