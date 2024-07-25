package com.github.lacoming.eventsapi.repository;

import com.github.lacoming.eventsapi.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
