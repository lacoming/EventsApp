package com.github.lacoming.eventsapi.web;

import com.github.lacoming.eventsapi.mapper.OrganizationMapper;
import com.github.lacoming.eventsapi.service.OrganizationService;
import com.github.lacoming.eventsapi.utils.AuthUtils;
import com.github.lacoming.eventsapi.web.dto.CreateOrganizationRequest;
import com.github.lacoming.eventsapi.web.dto.OrganizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    private final OrganizationMapper organizationMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ORGANIZATION_OWNER')")
    public ResponseEntity<OrganizationDto> createOrganization(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateOrganizationRequest request
    ) {
        var createdOrganization = organizationService.save(
                organizationMapper.toEntity(request),
                AuthUtils.getCurrentUserId(userDetails)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(organizationMapper.toDto(createdOrganization));
    }

}
