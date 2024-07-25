package com.github.lacoming.eventsapi.service;

import com.github.lacoming.eventsapi.entity.Organization;
import com.github.lacoming.eventsapi.entity.Role;
import com.github.lacoming.eventsapi.exception.AccessDeniedException;
import com.github.lacoming.eventsapi.exception.EntityNotFoundException;
import com.github.lacoming.eventsapi.repository.OrganizationRepository;
import com.github.lacoming.eventsapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    @Transactional
    public Organization save(Organization organization, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    if(!user.hasRole(Role.ROLE_ORGANIZATION_OWNER)) {
                        throw new AccessDeniedException("You don't has rights for create organization");
                    }
                    organization.setOwner(user);
                    return organizationRepository.save(organization);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("User with id {0} not found!", userId)
                ));
    }

    public Organization findById (Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Organization with id {0} not found!", id)
                ));
    }
}
